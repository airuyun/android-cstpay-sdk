package com.cst.cstpaysdk.manager

import android.content.Context
import com.cst.cstpaysdk.mvp.credit.presenter.EquipmentCreditPresenter
import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import com.cst.cstpaysdk.mvp.websocket.presenter.WebSocketPresenter
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener

/**
 * @author TJS
 * @date 2020/01/17 14:47
 * @cst_do WebSocket接口调度层，为了实现取餐接口内部有修改时，不影响接口调用层
 * 影响范围：WebSocket模块所有接口，后端通过WebSocket将数据推送到设备
 * 备注：一般情况下，接口调用层（应用层）不会直接调用该模块，该模块所有的数据都应该由本SDK统一处理
 * @cst_end
 */
class WebSocketManager(context: Context) : IWebSocketManager {

    private val mWebSocketPre: WebSocketPresenter = WebSocketPresenter(context)
    private val mEquipmentCreditPre: EquipmentCreditPresenter = EquipmentCreditPresenter(context)

    override fun webSocketConnect(cstApiManager: CstApiManager, webSocketView: IWebSocketView?) {
        mWebSocketPre.webSocketConnect(cstApiManager, webSocketView)
    }

    override fun beatConnect(listener: CstWebSocketListener) {
        mWebSocketPre.beatConnect(listener)
    }

    override fun getFaceInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getFaceInfo(listener)
    }

    override fun getCardInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getCardInfo(listener)
    }

    override fun getFingerprintInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getFingerprintInfo(listener)
    }

    override fun getPasswordCardInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getPasswordCardInfo(listener)
    }

    override fun setFaceInfo(data: String?) {
        mWebSocketPre.setFaceInfo(data)
    }

    override fun setCardInfo(data: String?) {
        mWebSocketPre.setCardInfo(data)
    }

    override fun setFingerprintInfo(data: String?) {
        mWebSocketPre.setFingerprintInfo(data)
    }

    override fun setPasswordInfo(data: String?) {
        mWebSocketPre.setPasswordInfo(data)
    }

    override fun setEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?) {
        mEquipmentCreditPre.resEquipmentCreditInfo(data, resEquipmentCreditView)
    }

    override fun syncIssuedState(listener: CstWebSocketListener) {
        mWebSocketPre.syncIssuedState(listener)
    }
}