package com.cst.cstpaysdk.net

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.manager.CstPayManager
import com.cst.cstpaysdk.util.LogUtil
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class CstWebSocketListener(val context: Context, val cstPayManager: CstPayManager) : WebSocketListener(){

    //心跳
    private val BEAT_COMMAND = "20001"

    //指纹信息
    private val FINGERPRINT_INFO_COMMAND = "20002"

    //密码信息
    private val PASSWORD_INFO_COMMAND = "20003"

    //人脸信息
    private val FACE_INFO_COMMAND = "20004"

    //卡信息指令
    private val CARD_INFO_COMMAND = "20005"

    //设备信用指令
    private val EQUIPMENT_CREDIT_COMMAND = "20006"

    var mWebSocket: WebSocket? = null

    fun getWebSocket(): WebSocket? {
        return mWebSocket
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        LogUtil.customLog(context, "webSocket onOpen，response = ${JSON.toJSONString(response)}")
        mWebSocket = webSocket
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        LogUtil.customLog(context, "websoket返回数据 = $text")
        if (text == "null" || text.isEmpty()) {
            return
        }

        val jsonObject: JSONObject = JSON.parseObject(text)
        val command: String? = jsonObject.getString("command")
        val data: String? = jsonObject.getString("data")
        when (command) {
            //获取人脸信息
            FACE_INFO_COMMAND -> {
                cstPayManager.setFaceInfo(data)
            }

            //获取卡信息
            CARD_INFO_COMMAND -> {
                cstPayManager.setCardInfo(data)
            }

            //获取指纹信息，
            FINGERPRINT_INFO_COMMAND -> {
                //cstPayManager.setFingerprintInfo()
            }

            //获取密码信息
            PASSWORD_INFO_COMMAND -> {
                //cstPayManager.setPasswordInfo()
            }

            //获取设备信用信息
            EQUIPMENT_CREDIT_COMMAND -> {
                cstPayManager.setEquipmentCreditInfo(data, null)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        LogUtil.customLog(context, "webSocket onClosing，code = $code")
        LogUtil.customLog(context, "webSocket onClosing，reason = $reason")
        mWebSocket?.cancel()
        mWebSocket?.close(code, reason)
        mWebSocket = null
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        LogUtil.customLog(context, "webSocket onClosed，code = $code")
        LogUtil.customLog(context, "webSocket onClosed，reason = $reason")
        mWebSocket?.cancel()
        mWebSocket = null
    }

    override fun onFailure(webSocket: WebSocket, e: Throwable, response: Response?) {
        super.onFailure(webSocket, e, response)
        LogUtil.customLog(context, "webSocket onFailure，e = $e")
        LogUtil.customLog(context, "webSocket onFailure，response = ${JSON.toJSONString(response)}")
        mWebSocket?.cancel()
        mWebSocket = null
    }
}