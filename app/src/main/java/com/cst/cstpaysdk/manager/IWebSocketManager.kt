package com.cst.cstpaysdk.manager

import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener

internal interface IWebSocketManager {

    /**
     * 主动向后端WebSocket发起连接请求
     */
    fun webSocketConnect(cstApiManager: CstApiManager, webSocketView: IWebSocketView?)

    /**
     * 主动向后端WebSocket发起心跳请求
     */
    fun beatConnect(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取人脸信息请求
     */
    fun getFaceInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取卡信息请求
     */
    fun getCardInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取指纹（门禁指纹等）信息请求
     */
    fun getFingerprintInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取密码（门禁密码等）信息请求
     */
    fun getPasswordCardInfo(listener: CstWebSocketListener)

    /**
     * 后端WebSocket向Android端推送人脸信息时，调用此接口处理数据
     */
    fun setFaceInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送卡信息时，调用此接口处理数据
     */
    fun setCardInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送指纹信息时，调用此接口处理数据
     */
    fun setFingerprintInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送密码信息时，调用此接口处理数据
     */
    fun setPasswordInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送设备信用信息时，调用此接口处理数据
     */
    fun setEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?)

    /**
     * 同步WebSocket下发状态（当后端WebSocket向Android端推送数据，Android端需要上报是否处理成功状态）
     */
    fun syncIssuedState(listener: CstWebSocketListener)
}