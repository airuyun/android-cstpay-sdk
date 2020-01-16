package com.cst.cstpaysdk.mvp.cstpay.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ReqCstOnlinePayBean
import com.cst.cstpaysdk.bean.ResCstPayBean
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.EquipmentCreditEntityDao
import com.cst.cstpaysdk.db.TradeRecordEntityDao
import com.cst.cstpaysdk.db.UserCreditEntityDao
import com.cst.cstpaysdk.db.entity.EquipmentCreditEntity
import com.cst.cstpaysdk.db.entity.TradeRecordEntity
import com.cst.cstpaysdk.db.entity.UserCreditEntity
import com.cst.cstpaysdk.mvp.cstpay.model.ICstPayModel
import com.cst.cstpaysdk.net.OkHttp3Utils1
import com.cst.cstpaysdk.util.*
import com.dj.hrfacelib.util.ImageUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import org.greenrobot.greendao.query.WhereCondition
import java.io.File
import java.io.IOException

class CstPayModelImpl(val context: Context) : ICstPayModel {

    /**
     * 人脸识别成功后抓拍到的照片临时保存文件夹路径（人脸识别成功后抓拍的照片，下次人脸识别成功时删除）
     */
    private val mSnapPhotoCacheDir:String = "${FileUtil.getPATH()}/${context.packageName}/face_picture_cache/"

    /**
     * 支付成功后，将抓拍的图片保存到该文件夹，最多保存10万张人脸抓拍图片，超过10万张则将旧图片删除
     */
    private val mSnapPhotoSaveDir:String = "${FileUtil.getPATH()}/${context.packageName}/face_picture/"

    /**
     * 本地保存的最多交易记录数量为10万条
     */
    private val mMaxTradeRecordCount: Int = 100000

    override fun cstOnlinePay(context: Context, payInfoBean: PayInfoBean): Observable<ResCstPayBean> {
        return Observable.create {
            val reqCstPayBean = ReqCstOnlinePayBean()
            reqCstPayBean.system = "Android"
            reqCstPayBean.systemVersion = android.os.Build.VERSION.RELEASE
            reqCstPayBean.userId = payInfoBean.userId
            reqCstPayBean.clientId = ""
            reqCstPayBean.checkcode = ""
            reqCstPayBean.data?.cardNo = payInfoBean.userId
            reqCstPayBean.data?.payPwd = ""
            reqCstPayBean.data?.tradeTime = DateUtils.getCurrentTime14()
            reqCstPayBean.data?.dealserial = LocalUtils.getMac().replace(
                ":",
                ""
            ) + DateUtils.getCurrentTime14() + (0..10).random() + (0..10).random()
            reqCstPayBean.data?.tradeType = payInfoBean.tradeType
            reqCstPayBean.data?.mac = LocalUtils.getMac().replace(":", "")
            //人脸识别成功后抓拍到的照片
            val list: List<File>? = FileUtil.listFile(mSnapPhotoCacheDir)
            if (list != null && list.isNotEmpty()) {
                val base64Face = ImageUtil.getBase64FromImgFile(list[0].absolutePath)
                reqCstPayBean.data?.photoMsg = base64Face
            }
            reqCstPayBean.data?.amount = payInfoBean.amount
            reqCstPayBean.data?.equipmentId = payInfoBean.equipmentId
            val param: String = JSON.toJSONString(reqCstPayBean)
            LogUtil.customLog(context.applicationContext, "康索特三代平台在线支付推送参数，param = $param")
            OkHttp3Utils1.get().doPostJson(context, ConstantUtils.getCstPayUrl(context), param, object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val body: ResponseBody? = response.body()
                        val result: String? = body?.string()
                        LogUtil.customLog(context, "康索特三代平台在线支付响应参数，result = $result")
                        if (result != null && result.startsWith("{") && result.endsWith("}")) {
                            val resCstPayBean: ResCstPayBean = JSON.parseObject(result, ResCstPayBean::class.java)
                            if (resCstPayBean.code == 200) {
                                if(resCstPayBean.data != null) {
                                    paySuccess(context, payInfoBean, resCstPayBean, it)
                                } else {
                                    it.onError(Throwable(resCstPayBean.msg))
                                }
                            } else {
                                it.onError(Throwable(resCstPayBean.msg ?: "在线支付失败"))
                            }
                        } else {
                            it.onError(Throwable("在线支付失败"))
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        LogUtil.customLog(context.applicationContext, "康索特三代平台在线支付失败，e = $e")
                        //it.onError(e)
                        it.onError(Throwable("000000"))
                    }
                })
        }
    }

    override fun cstOfflinePay(context: Context, payInfoBean: PayInfoBean): Observable<ResCstPayBean> {
        return Observable.create {
            //获取当前设备的信用规则信息
            val equipmentCreditEntityDao: EquipmentCreditEntityDao = DBManager.getInstance(context).equipmentCreditEntityDao
            val condition: WhereCondition = EquipmentCreditEntityDao.Properties.Valid.eq("1")
            val list: MutableList<EquipmentCreditEntity> = equipmentCreditEntityDao.queryBuilder().where(condition).build().list()
            LogUtil.customLog(context.applicationContext, "离线线支付相关参数，payInfoBean = ${JSON.toJSONString(payInfoBean)}")
            LogUtil.customLog(context.applicationContext, "离线线支付相关参数，list = ${JSON.toJSONString(list)}")
            if(list.size > 0) {
                if(list.size == 1) {
                    //最大离线支付次数
                    val maxOfflineNum: Int = list[0].offlineNum
                    //最大离线支付金额
                    val maxOfflineQuota: Float = list[0].offlineQuota.toFloat()
                    //用户当前需要支付的金额
                    val payAmount: Float = payInfoBean.amount?.toFloat()!!
                    if(maxOfflineQuota > payAmount) {
                        //获取用户离线消费信用信息
                        val userCreditEntityDao: UserCreditEntityDao = DBManager.getInstance(context).userCreditEntityDao
                        val condition2: WhereCondition = UserCreditEntityDao.Properties.UserId.eq(payInfoBean.userId)
                        val list2: MutableList<UserCreditEntity> = userCreditEntityDao.queryBuilder().where(condition2).build().list()
                        if(list2.size > 0) {
                            //用户离线支付信用信息
                            val userCredit: UserCreditEntity = list2[0]
                            //用户已经使用的离线支付次数
                            val offlinePayNum: Int = userCredit.offlinePayNum
                            //用户已经使用的离线支付金额
                            val offlinePayAmount: Float = userCredit.offlinePayAmount.toFloat()
                            if(maxOfflineNum >= offlinePayNum) {
                                if(payAmount <= (maxOfflineQuota - offlinePayAmount)) {
                                    userCredit.offlinePayNum = offlinePayNum + 1
                                    userCredit.offlinePayAmount = (offlinePayAmount + payAmount).toString()
                                    userCreditEntityDao.update(userCredit)
                                    paySuccess(context, payInfoBean, null, it)
                                } else {
                                    LogUtil.customLog(context, "消费余额额度不足，离线支付失败")
                                    //it.onError(Throwable("支付失败"))
                                    it.onError(Throwable("离线支付额度不足"))
                                }
                            } else {
                                LogUtil.customLog(context, "消费次数额度不足，离线支付失败")
                                //it.onError(Throwable("支付失败"))
                                it.onError(Throwable("离线支付次数已满"))
                            }
                        } else {
                            val userCredit = UserCreditEntity()
                            userCredit.userId = payInfoBean.userId
                            userCredit.userCode = payInfoBean.userCode
                            userCredit.userName = payInfoBean.userName
                            userCredit.offlinePayNum = 1
                            userCredit.offlinePayAmount = payAmount.toString()
                            userCreditEntityDao.insert(userCredit)
                            paySuccess(context, payInfoBean, null, it)
                        }
                    } else {
                        LogUtil.customLog(context, "支付金额大于设备信用额度，离线支付失败")
                        //it.onError(Throwable("支付失败"))
                        it.onError(Throwable("离线支付额度不足"))
                    }
                } else {
                    LogUtil.customLog(context, "设备存在多条信誉规则异常，离线支付失败")
                    //it.onError(Throwable("支付失败"))
                    it.onError(Throwable("设备离线支付信誉异常"))
                }
            } else {
                LogUtil.customLog(context, "设备没有信誉规则，离线支付失败")
                //it.onError(Throwable("支付失败"))
                it.onError(Throwable("设备不支持离线支付"))
            }
        }
    }

    private fun paySuccess(context: Context, payInfoBean: PayInfoBean, resCstPayBean: ResCstPayBean?, emitter: ObservableEmitter<ResCstPayBean>) {
        val tradeRecordEntity = TradeRecordEntity()
        tradeRecordEntity.userId = payInfoBean.userId
        tradeRecordEntity.userName = payInfoBean.userName
        tradeRecordEntity.userCode = payInfoBean.userCode
        tradeRecordEntity.shopId = payInfoBean.shopId
        tradeRecordEntity.shopName = payInfoBean.shopName
        tradeRecordEntity.tradeTime = DateUtils.getCurrentTime14()
        tradeRecordEntity.tradeType = payInfoBean.tradeType
        tradeRecordEntity.cardNo = payInfoBean.cardNo
        tradeRecordEntity.physicsNo = payInfoBean.physicsNo
        tradeRecordEntity.mac = LocalUtils.getMac().replace(":", "")
        tradeRecordEntity.oldMac = LocalUtils.getMac().replace(":", "")
        if (resCstPayBean != null) {
            tradeRecordEntity.syncState = "1"//向平台同步交易状态，0-未同步 1-已同步
            tradeRecordEntity.dealserial = resCstPayBean.data?.dealserial
            tradeRecordEntity.amount = resCstPayBean.data?.actualAmount
            tradeRecordEntity.balance = resCstPayBean.data?.balance
            tradeRecordEntity.tradeMethod = "online"
        } else {
            tradeRecordEntity.syncState = "0"//向平台同步交易状态，0-未同步 1-已同步
            tradeRecordEntity.dealserial = LocalUtils.getMac().replace(
                ":",
                ""
            ) + DateUtils.getCurrentTime14() + (0..10).random() + (0..10).random()
            tradeRecordEntity.amount = payInfoBean.amount
            tradeRecordEntity.balance = ""
            tradeRecordEntity.tradeMethod = "offline"
        }
        tradeRecordEntity.photoMsg = "${mSnapPhotoSaveDir}${tradeRecordEntity.dealserial}.jpg"
        val entityDao: TradeRecordEntityDao = DBManager.getInstance(context).tradeRecordEntityDao
        val index: Long = entityDao.insert(tradeRecordEntity)

        //本地保存的交易记录大于10万条时，删除最旧的1千条已经同步到后端的记录
        val allDataList: MutableList<TradeRecordEntity> = entityDao.queryBuilder().build().list()
        if (allDataList.size >= mMaxTradeRecordCount) {
            val condition = TradeRecordEntityDao.Properties.SyncState.eq("1")
            val staleDataList = entityDao.queryBuilder().where(condition).limit(1000)
                .orderAsc(TradeRecordEntityDao.Properties.TradeTime).build().list()
            for (staleData in staleDataList) {
                FileUtil.deleteFile(staleData.photoMsg)
                entityDao.delete(staleData)
            }
        }

        LogUtil.customLog(context.applicationContext, "支付成功，insertCount = $index")
        val list: List<File> = FileUtil.listFile(mSnapPhotoCacheDir)
        if (list.isNotEmpty()) {
            FileUtil.copyFile(list[0].absolutePath, "${mSnapPhotoSaveDir}${tradeRecordEntity.dealserial}.jpg", false)
        }
        emitter.onNext(resCstPayBean!!)
    }
}