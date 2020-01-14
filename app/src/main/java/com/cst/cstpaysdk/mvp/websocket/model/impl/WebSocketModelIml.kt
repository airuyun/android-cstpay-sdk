package com.cst.cstpaysdk.mvp.websocket.model.impl

import android.content.Context
import android.graphics.Bitmap
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqBeatConnectBean
import com.cst.cstpaysdk.bean.SyncIssuedStateBean
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.FaceInfoEntityDao
import com.cst.cstpaysdk.db.entity.FaceInfoEntity
import com.cst.cstpaysdk.manager.CstPayManager
import com.cst.cstpaysdk.mvp.websocket.model.IWebSocketModel
import com.cst.cstpaysdk.net.CstWebSocketListener
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.*
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

class WebSocketModelIml(val context: Context) : IWebSocketModel {

    //心跳指令
    private val BEAT_CONNECT_COMMAND = "20001"

    override fun webSocketConnect(cstPayManager: CstPayManager): Observable<CstWebSocketListener> {
        return Observable.create {
            LogUtil.customLog(context, "开始与服务端建立WebSocket连接")
            val listener = CstWebSocketListener(context, cstPayManager)
            OkHttp3Utils.get().doWebSocket(context, null, listener, ConstantUtils.getWebSocketUrl(), "")
            it.onNext(listener)
        }
    }

    override fun beatConnect(context: Context, listener: CstWebSocketListener): Observable<String> {
        return Observable.create {
            val reqBeatConnectBean = ReqBeatConnectBean()
            reqBeatConnectBean.system = "Android"
            reqBeatConnectBean.systemVersion = android.os.Build.VERSION.RELEASE
            reqBeatConnectBean.command = BEAT_CONNECT_COMMAND
            reqBeatConnectBean.data?.equipmentId = ConstantUtils.getEquipmentId()
            reqBeatConnectBean.data?.equipmentNo = ConstantUtils.getEquipmentNo()
            val param: String = JSON.toJSONString(reqBeatConnectBean)
            LogUtil.customLog(context.applicationContext, "WebSocket心跳推送参数 = $param")
            LogUtil.customLog(context.applicationContext, "queueSize = ${listener.getWebSocket()?.queueSize()}")
            OkHttp3Utils.get().doWebSocket(context.applicationContext, listener.getWebSocket(), listener, ConstantUtils.getWebSocketUrl(), param)
            it.onComplete()
        }
    }

    override fun getFaceInfo(context: Context, listener: CstWebSocketListener) {

    }

    override fun getCardInfo(context: Context, listener: CstWebSocketListener) {

    }

    override fun getFingerprintInfo(context: Context, listener: CstWebSocketListener) {

    }

    override fun getPasswordCardInfo(context: Context, listener: CstWebSocketListener) {

    }

    override fun setFaceInfo(context: Context, data: String?): Observable<String> {
        return Observable.create {
            if (data == null || data == "null" || data.isEmpty() || !data.startsWith("{") || !data.endsWith("}")) {
                return@create
            }
            val jsonObject: JSONObject = JSON.parseObject(data)
            val faceInfoList: List<FaceInfoEntity> = JSON.parseArray(jsonObject.getString("dataList"), FaceInfoEntity::class.java)
            val mFaceInfoEntityDao: FaceInfoEntityDao = DBManager.getInstance(context).faceInfoEntityDao
            val faceInfoSaveState: MutableList<SyncIssuedStateBean.DataBean> = mutableListOf()
            val syncIssuedStateBean = SyncIssuedStateBean()
            syncIssuedStateBean.mac = LocalUtils.getMac().replace(":", "")
            syncIssuedStateBean.equipmentId = jsonObject.getString("equipmentId")

            for (faceInfo: FaceInfoEntity in faceInfoList) {
                val condition1: WhereCondition = FaceInfoEntityDao.Properties.UserId.eq(faceInfo.userId)
                val condition2: WhereCondition = FaceInfoEntityDao.Properties.UserCode.eq(faceInfo.userCode)
                val list = mFaceInfoEntityDao.queryBuilder().where(condition1, condition2).build().list()
                val dataBean = syncIssuedStateBean.DataBean()
                if (list.size > 0 && list[0].recordTime > faceInfo.recordTime) {
                    //数据库中的人脸已经是最新的人脸，就算平台推送了旧的人脸信息下来，也不做任何操作
                    dataBean.recordId = faceInfo.recordId
                    dataBean.state = "F"
                    dataBean.synMsg = "人脸记录已过时"
                    faceInfoSaveState.add(dataBean)
                    return@create
                }

                if ("A" == faceInfo.operateType || "E" == faceInfo.operateType) {
                    val bitmap: Bitmap = Base64Utils.base64ToBitmap(faceInfo.data) ?: return@create
                    //Base64Utils.Base64ToFile(faceInfo.data, "${FileUtil.getPATH()+ File.separator + faceInfo.recordId}-${faceInfo.userCode}-${faceInfo.userId}.jpg")
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
                        dataBean.recordId = faceInfo.recordId
                        dataBean.state = "S"
                        dataBean.synMsg = "人脸下发成功"
                    } else {
                        dataBean.recordId = faceInfo.recordId
                        dataBean.state = "F"
                        dataBean.synMsg = "人脸下发写入设备失败"
                    }
                } else if ("D" == faceInfo.operateType) {
                    if (list.isNotEmpty()) {
                        for (infoEntity: FaceInfoEntity in list) {
                            mFaceInfoEntityDao.delete(infoEntity)
                            val success: Boolean = FaceServer.getInstance().deleteFace(context, faceInfo.userCode)
                            if (success) {
                                dataBean.recordId = faceInfo.recordId
                                dataBean.state = "S"
                            } else {
                                dataBean.recordId = faceInfo.recordId
                                dataBean.state = "F"
                            }
                        }
                    }
                }
                faceInfoSaveState.add(dataBean)
            }
            syncIssuedStateBean.dataList = faceInfoSaveState
            syncIssuedState(context, syncIssuedStateBean)
        }
    }

    override fun setCardInfo(context: Context, data: String?): Observable<String> {
        return Observable.create {
            if (data == null || data == "null" || data.isEmpty()) {
                return@create
            }
        }
    }

    override fun setFingerprintInfo(context: Context, data: String?): Observable<String> {
        return Observable.create {
            if (data == null || data == "null" || data.isEmpty()) {
                return@create
            }
        }
    }

    override fun setPasswordInfo(context: Context, data: String?): Observable<String> {
        return Observable.create {
            if (data == null || data == "null" || data.isEmpty()) {
                return@create
            }
        }
    }

    override fun syncIssuedState(context: Context, syncIssuedStateBean: SyncIssuedStateBean) {
        val param: String = JSON.toJSONString(syncIssuedStateBean)
        LogUtil.customLog(context.applicationContext, "黑白名单下发上报推送参数 = $param")
        OkHttp3Utils.get().doPostJson(context, ConstantUtils.getSyncIssuedStateUrl(), param, object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body: ResponseBody? = response.body()
                val result: String? = body?.string()
                LogUtil.customLog(context, "黑白名单下发上报响应参数，result = $result")
            }

            override fun onFailure(call: Call, e: IOException) {
                LogUtil.customLog(context, "黑白名单下发上报失败，e = $e")
            }
        })
    }
}