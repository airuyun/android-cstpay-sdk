package com.cst.cstpaysdk.mvp.traderecord.model.impl

import android.content.Context
import android.database.Cursor
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.ReqTradeRecordBean
import com.cst.cstpaysdk.bean.ResTradeRecordBean
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.TradeRecordEntityDao
import com.cst.cstpaysdk.db.UserCreditEntityDao
import com.cst.cstpaysdk.db.entity.TradeRecordEntity
import com.cst.cstpaysdk.db.entity.UserCreditEntity
import com.cst.cstpaysdk.mvp.traderecord.model.ITradeRecordModel
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.*
import com.dj.hrfacelib.util.ImageUtil
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import org.greenrobot.greendao.query.WhereCondition
import java.io.IOException
import java.math.BigDecimal

class TradeRecordModelImpl : ITradeRecordModel {

    override fun offlineQueryTradeRecord(context: Context, startTime: String, endTime: String): Observable<ResTradeRecordBean> {
        return Observable.create {
            val startTime1: String = startTime.replace(Regex("[^0-9]"), "")
            val endTime1: String = endTime.replace(Regex("[^0-9]"), "")
            LogUtil.customLog(context, "离线线查询交易记录请求参数，startTime = $startTime1，endTime = $endTime1")
            val entityDao: TradeRecordEntityDao = DBManager.getInstance(context).tradeRecordEntityDao
            val condition1: WhereCondition = TradeRecordEntityDao.Properties.TradeTime.ge(startTime1)
            val condition2: WhereCondition = TradeRecordEntityDao.Properties.TradeTime.le(endTime1)
            val list = entityDao.queryBuilder().where(condition1, condition2).build().list()

            //查询记录保存到设备excel表中，一次查询的数据量太大时，excel表方便对账
            val excelContentList: MutableList<List<*>> = mutableListOf()
            for(entity: TradeRecordEntity in list) {
                val rowContentList: MutableList<Any> = mutableListOf()
                rowContentList.add(if(entity.id != null) entity.id.toString() else "")
                rowContentList.add(if(entity.userId != null) entity.userId.toString() else "")
                rowContentList.add(if(entity.userName != null) entity.userName.toString() else "")
                rowContentList.add(if(entity.userCode != null) entity.userCode.toString() else "")
                rowContentList.add(if(entity.shopId != null) entity.shopId.toString() else "")
                rowContentList.add(if(entity.shopName != null) entity.shopName.toString() else "")
                rowContentList.add(if(entity.tradeTime != null) entity.tradeTime.toString() else "")
                rowContentList.add(if(entity.dealserial != null) entity.dealserial.toString() else "")
                rowContentList.add(if(entity.amount != null) entity.amount.toString() else "")
                rowContentList.add(if(entity.balance != null) entity.balance.toString() else "")
                rowContentList.add(if(entity.tradeType != null) entity.tradeType.toString() else "")
                rowContentList.add(if(entity.tradeMethod != null) entity.tradeMethod.toString() else "")
                rowContentList.add(if(entity.cardNo != null) entity.cardNo.toString() else "")
                rowContentList.add(if(entity.photoMsg != null) entity.photoMsg.toString() else "")
                rowContentList.add(if(entity.physicsNo != null) entity.physicsNo.toString() else "")
                rowContentList.add(if(entity.syncState != null) entity.syncState.toString() else "")
                rowContentList.add(if(entity.mac != null) entity.mac.toString() else "")
                rowContentList.add(if(entity.oldMac != null) entity.oldMac.toString() else "")
                rowContentList.add(if(entity.syncCount != null) entity.syncCount.toString() else "")
                excelContentList.add(rowContentList)
            }
            ExcelUtils.initExcel("${FileUtil.getPATH()}/${context.packageName}/data/trade_record.xls", "$startTime1-$endTime1", entityDao.allColumns)
            ExcelUtils.writeObjListToExcel(context, excelContentList, "${FileUtil.getPATH()}/${context.packageName}/data/trade_record.xls")

            //金额精确到分
            val sql = "SELECT SUM(cast(AMOUNT AS decimal(18,2))) FROM TRADE_RECORD_ENTITY WHERE TRADE_TIME >= $startTime1 AND TRADE_TIME <= $endTime1"
            val cursor: Cursor = entityDao.session.database.rawQuery(sql, null)
            var amount = 0.0
            if (cursor.moveToFirst()) {
                //不能使用cursor.getString，否则结果精度会丢失
                amount = BigDecimal(cursor.getDouble(cursor.getColumnIndex("SUM(cast(AMOUNT AS decimal(18,2)))"))).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            }
            val resTradeRecordBean = ResTradeRecordBean()
            resTradeRecordBean.code = 200
            resTradeRecordBean.msg = "查询成功"
            resTradeRecordBean.data?.totalNum = list.size
            resTradeRecordBean.data?.totalPrice = amount.toString()
            resTradeRecordBean.data?.tradeLogs = list
            val result: String = JSON.toJSONString(resTradeRecordBean)
            LogUtil.customLog(context, "离线查询交易记录响应参数，result = $result")
            it.onNext(resTradeRecordBean)
        }
    }

    override fun onlineQueryTradeRecord(context: Context, startTime: String, endTime: String): Observable<ResTradeRecordBean> {
        return Observable.create {
            val reqTradeRecordBean = ReqTradeRecordBean()
            val param: String = JSON.toJSONString(reqTradeRecordBean)
            LogUtil.customLog(context, "在线查询交易记录请求参数，param = $param")
            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getTradeRecordUrl(context), param, object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val body: ResponseBody? = response.body()
                        val result: String? = body?.string()
                        LogUtil.customLog(context, "在线查询交易记录响应参数，result = $result")
                        if (result != null && result.startsWith("{") && result.endsWith("}")) {
                            val resTradeRecordBean: ResTradeRecordBean = JSON.parseObject(result, ResTradeRecordBean::class.java)
                            if (resTradeRecordBean.code == 200) {
                                it.onNext(resTradeRecordBean)
                            } else {
                                it.onError(Throwable(resTradeRecordBean.msg ?: "在线查询交易记录失败"))
                            }
                        } else {
                            it.onError(Throwable("在线查询交易记录失败"))
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        LogUtil.customLog(context, "在线查询交易记录失败，e = $e")
                        it.onError(e)
                    }
                })
        }
    }

    override fun uploadTradeRecord(context: Context): Observable<ResUploadTradeRecordBean> {
        return Observable.create {
            val entityDao: TradeRecordEntityDao = DBManager.getInstance(context).tradeRecordEntityDao
            val condition: WhereCondition = TradeRecordEntityDao.Properties.SyncState.eq("0")
            //限制每次最多上传100条记录
            val list = entityDao.queryBuilder().where(condition).limit(100).build().list()
            LogUtil.customLog(context, "待上传的离线支付记录，list.size = ${list.size}")
            if (list.size <= 0) {
                return@create
            }

            val reqTradeRecordBean = ReqTradeRecordBean()
            reqTradeRecordBean.system = "Android"
            reqTradeRecordBean.systemVersion = android.os.Build.VERSION.RELEASE
            reqTradeRecordBean.userId = ""
            reqTradeRecordBean.clientId = ""
            reqTradeRecordBean.checkcode = ""
            reqTradeRecordBean.data?.mac = LocalUtils.getMac().replace(":", "")
            reqTradeRecordBean.data?.tradeCount = list?.size?.toString()
            reqTradeRecordBean.data?.tradeList = list
            val param1: String = JSON.toJSONString(reqTradeRecordBean)
            val tradeRecordBean: ReqTradeRecordBean = JSON.parseObject(param1, ReqTradeRecordBean::class.java)
            val tradeList: MutableList<TradeRecordEntity>? = tradeRecordBean.data?.tradeList
            if(tradeList != null) {
                for ((i, tradeRecord: TradeRecordEntity) in tradeList.withIndex()) {
                    //更新离线支付记录同步状态
                    /*if(tradeRecord.syncCount <= Int.MAX_VALUE) {
                        tradeRecord.syncCount += 1
                        entityDao.update(tradeRecord)
                    }*/
                    val base64 = ImageUtil.getBase64FromImgFile(tradeRecord.photoMsg)
                    tradeList[i].photoMsg = base64
                }
            }
            val param2: String = JSON.toJSONString(tradeRecordBean)
            LogUtil.customLog(context, "上传离线支付记录推送参数 = $param2")
            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getUploadTradeRecordUrl(context), param2, object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val body: ResponseBody? = response.body()
                        val result: String? = body?.string()
                        LogUtil.customLog(context, "上传离线支付记录响应参数，result = $result")
                        if (result != null && result.startsWith("{") && result.endsWith("}")) {
                            val resUploadTradeRecordBean: ResUploadTradeRecordBean = JSON.parseObject(result, ResUploadTradeRecordBean::class.java)
                            if (resUploadTradeRecordBean.code == 200) {
                                val uploadSuccessList: MutableList<TradeRecordEntity> = mutableListOf()
                                val uploadFailDealSerialList: MutableList<String> = mutableListOf()
                                //同步支付记录失败的数据
                                resUploadTradeRecordBean.data?.failList?.let { failList ->
                                    if (failList.isNotEmpty()) {
                                        for (fail: ResUploadTradeRecordBean.DataBean.FailData in failList) {
                                            val failDealSerial: String? = fail.dealserial
                                            if (failDealSerial != null) {
                                                uploadFailDealSerialList.add(failDealSerial)
                                            }
                                        }
                                    }
                                }
                                //同步支付记录成功的数据
                                for (tradeRecord: TradeRecordEntity in list) {
                                    if (!uploadFailDealSerialList.contains(tradeRecord.dealserial)) {
                                        uploadSuccessList.add(tradeRecord)
                                    }
                                }
                                //同步支付记录处理
                                val userCreditEntityDao: UserCreditEntityDao = DBManager.getInstance(context).userCreditEntityDao
                                for (tradeRecord: TradeRecordEntity in uploadSuccessList) {
                                    val condition1: WhereCondition = UserCreditEntityDao.Properties.UserId.eq(tradeRecord.userId)
                                    val list1: MutableList<UserCreditEntity> = userCreditEntityDao.queryBuilder().where(condition1).build().list()
                                    val userCredit: UserCreditEntity = list1[0]
                                    userCredit.offlinePayNum -= 1
                                    userCredit.offlinePayAmount = (userCredit.offlinePayAmount.toFloat() - tradeRecord.amount.toFloat()).toString()
                                    userCreditEntityDao.update(userCredit)
                                    if (userCredit.offlinePayNum == 0 && userCredit.offlinePayAmount.toFloat() <= 0) {
                                        //用户离线消费记录已经全部上传，删除该用户的离线支付信用记录
                                        userCreditEntityDao.delete(userCredit)
                                    }
                                    //更新离线支付记录同步状态
                                    tradeRecord.syncState = "1"
                                    entityDao.update(tradeRecord)
                                }
                                it.onNext(resUploadTradeRecordBean)
                            } else {
                                it.onError(Throwable(resUploadTradeRecordBean.msg ?: "上传离线支付记录失败"))
                            }
                        } else {
                            it.onError(Throwable("上传离线支付记录失败"))
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        it.onError(e)
                    }
                })
        }
    }
}