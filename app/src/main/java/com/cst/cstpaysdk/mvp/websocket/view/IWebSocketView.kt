package com.cst.cstpaysdk.mvp.websocket.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.net.CstWebSocketListener

interface IWebSocketView : IBaseView {

    fun webSocketConnect(listener: CstWebSocketListener)
}