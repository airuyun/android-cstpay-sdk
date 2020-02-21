package com.cst.cstpaysdk.mvp.httpbeat.model.impl

import android.content.Context
import android.graphics.Bitmap
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqBeatConnectBean
import com.cst.cstpaysdk.bean.ReqHttpSyncIssuedStateBean
import com.cst.cstpaysdk.bean.ResBeatConnectBean
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.FaceInfoEntityDao
import com.cst.cstpaysdk.db.entity.FaceInfoEntity
import com.cst.cstpaysdk.mvp.httpbeat.model.IHttpBeatModel
import com.cst.cstpaysdk.net.OkHttp3Utils1
import com.cst.cstpaysdk.util.Base64Utils
import com.cst.cstpaysdk.util.ConstantUtils
import com.cst.cstpaysdk.util.LocalUtils
import com.cst.cstpaysdk.util.LogUtil
import com.dj.hrfacelib.faceserver.FaceServer
import com.dj.hrfacelib.util.ImageUtil
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import org.greenrobot.greendao.query.WhereCondition
import java.io.File
import java.io.IOException

class HttpBeatModelImpl : IHttpBeatModel{

    override fun httpBeatConnect(context: Context): Observable<ResBeatConnectBean> {
        return Observable.create {

            val param: String?
            if(ConstantUtils.currentCommandValue == "1003") {
                param = JSON.toJSONString(ConstantUtils.httpSyncIssuedStateBean)
            } else {
                val reqBeatConnectBean = ReqBeatConnectBean()
                reqBeatConnectBean.system = "Android"
                reqBeatConnectBean.systemVersion = android.os.Build.VERSION.RELEASE
                reqBeatConnectBean.data?.equipmentId = ConstantUtils.equipmentId
                reqBeatConnectBean.data?.equipmentNo = ConstantUtils.equipmentNo
                reqBeatConnectBean.data?.command = "1001"
                reqBeatConnectBean.data?.mac = LocalUtils.getMac().replace(":", "")
                reqBeatConnectBean.data?.ip = LocalUtils.getLocalInetAddress().hostAddress
                param = JSON.toJSONString(reqBeatConnectBean)
            }
            LogUtil.customLog(context.applicationContext, "HTTP心跳推送参数 = $param")

            OkHttp3Utils1.get().doPostJson(context, ConstantUtils.getHTTPBeatConnectUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val result: String? = body?.string()
                    LogUtil.customLog(context, "HTTP心跳请求响应参数，result = $result")
                    if (result != null && result.startsWith("{") && result.endsWith("}")) {
                        val resBeatConnectBean: ResBeatConnectBean = JSON.parseObject(result, ResBeatConnectBean::class.java)
                        if (resBeatConnectBean.code == 200) {
                            if(resBeatConnectBean.data != null) {
                                ConstantUtils.currentCommandValue = resBeatConnectBean.data?.command
                                when(resBeatConnectBean.data?.command ?: "1001") {
                                    //心跳
                                    "1001" -> {
                                        ConstantUtils.httpSyncIssuedStateBean = null
                                    }
                                    //获取人脸
                                    "1002" -> {
                                        ConstantUtils.httpSyncIssuedStateBean = setFaceInfo(context, result)
                                    }
                                    //下发（人脸、卡、指纹等）状态上报
                                    "1003" -> {

                                    }
                                }
                                it.onNext(resBeatConnectBean)
                            } else {
                                it.onError(Throwable(resBeatConnectBean.msg))
                            }
                        } else {
                            it.onError(Throwable(resBeatConnectBean.msg ?: "HTTP心跳请求失败"))
                        }
                    } else {
                        it.onError(Throwable("HTTP心跳请求失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context, "HTTP心跳请求失败，e = $e")
                    it.onError(e)

                }
            })
        }
    }

    fun setFaceInfo(context: Context, data: String?): ReqHttpSyncIssuedStateBean {
        val jsonObject: JSONObject = JSON.parseObject(data)
        val faceInfo: FaceInfoEntity = JSON.parseObject(jsonObject.getString("data"), FaceInfoEntity::class.java)
        val mFaceInfoEntityDao: FaceInfoEntityDao = DBManager.getInstance(context).faceInfoEntityDao
        val condition1: WhereCondition = FaceInfoEntityDao.Properties.UserId.eq(faceInfo.userId)
        val condition2: WhereCondition = FaceInfoEntityDao.Properties.UserCode.eq(faceInfo.userCode)
        val list = mFaceInfoEntityDao.queryBuilder().where(condition1, condition2).build().list()
        val dataBean = ReqHttpSyncIssuedStateBean()
        dataBean.system = "Android"
        dataBean.systemVersion = android.os.Build.VERSION.RELEASE
        dataBean.token = ""
        dataBean.clientId = ""
        dataBean.data?.command = "1003"
        dataBean.data?.mac = LocalUtils.getMac().replace(":", "")

        if (list.size > 0 && list[0].recordTime > faceInfo.recordTime) {
            //数据库中的人脸已经是最新的人脸，就算平台推送了旧的人脸信息下来，也不做任何操作
            dataBean.data?.recordId = faceInfo.recordId
            dataBean.data?.state = "F"
            dataBean.data?.synMsg = "人脸记录已过时"
            return dataBean
        }
        if ("A" == faceInfo.operateType || "E" == faceInfo.operateType) {
            val bitmap: Bitmap? = Base64Utils.base64ToBitmap(faceInfo.data)
            if (bitmap == null) {
                dataBean.data?.recordId = faceInfo.recordId
                dataBean.data?.state = "F"
                dataBean.data?.synMsg = "人脸图片格式错误"
                return dataBean
            }
            if (list.isNotEmpty()) {
                for (infoEntity: FaceInfoEntity in list) {
                    //如果数据在本地存在，则先删除旧数据
                    mFaceInfoEntityDao.delete(infoEntity)
                    FaceServer.getInstance().deleteFace(context, faceInfo.userCode)
                }
            }
            //大匠人脸规定，图片的宽度必须是4的倍数
            val width: Int = if (bitmap.width % 4 == 0) bitmap.width else bitmap.width - bitmap.width % 4
            val nv21: ByteArray = ImageUtil.bitmapToNv21(bitmap, width, bitmap.height)
            val success: Boolean = FaceServer.getInstance().register(context, nv21, width, bitmap.height, faceInfo.userName, faceInfo.userCode, faceInfo.userId)
            LogUtil.customLog(context, "人脸写入设备成功？= $success")
            //写入设备成功后再加入数据库，不然会出现数据库已经存在记录，但是这条记录永远都无法写入设备问题
            if (success) {
                //保存人脸图片的文件夹由大匠人脸设定，是固定的：/data/user/0/com.cst.takefood/files/register/imgs/
                faceInfo.data = "${context.filesDir.path + File.separator + FaceServer.SAVE_IMG_DIR + File.separator + faceInfo.userName}-${faceInfo.userCode}-${faceInfo.userId}.jpg"
                mFaceInfoEntityDao.insert(faceInfo)
                dataBean.data?.recordId = faceInfo.recordId
                dataBean.data?.state = "S"
                dataBean.data?.synMsg = "人脸下发成功"
                return dataBean
            } else {
                dataBean.data?.recordId = faceInfo.recordId
                dataBean.data?.state = "F"
                dataBean.data?.synMsg = "人脸下发写入设备失败"
                return dataBean
            }
        } else if ("D" == faceInfo.operateType) {
            if (list.isNotEmpty()) {
                for (infoEntity: FaceInfoEntity in list) {
                    mFaceInfoEntityDao.delete(infoEntity)
                    val success: Boolean = FaceServer.getInstance().deleteFace(context, faceInfo.userCode)
                    if (success) {
                        dataBean.data?.recordId = faceInfo.recordId
                        dataBean.data?.state = "S"
                        dataBean.data?.synMsg = "删除设备人脸成功"
                        return dataBean
                    } else {
                        dataBean.data?.recordId = faceInfo.recordId
                        dataBean.data?.state = "F"
                        dataBean.data?.synMsg = "删除设备人脸失败"
                        return dataBean
                    }
                }
            }
        }
        return dataBean
    }
}