package com.cst.cstpaysdk.mvp.websocket.model

import android.content.Context
import com.cst.cstpaysdk.bean.SyncIssuedStateBean
import com.cst.cstpaysdk.manager.CstApiManager
import com.cst.cstpaysdk.net.CstWebSocketListener
import io.reactivex.Observable

interface IWebSocketModel {

    fun webSocketConnect(cstApiManager: CstApiManager): Observable<CstWebSocketListener>

    fun beatConnect(context: Context, listener: CstWebSocketListener): Observable<String>

    fun getFaceInfo(context: Context, listener: CstWebSocketListener)

    fun getCardInfo(context: Context, listener: CstWebSocketListener)

    fun getFingerprintInfo(context: Context, listener: CstWebSocketListener)

    fun getPasswordCardInfo(context: Context, listener: CstWebSocketListener)

    fun setFaceInfo(context: Context, data: String?): Observable<String>

    fun setCardInfo(context: Context, data: String?): Observable<String>

    fun setFingerprintInfo(context: Context, data: String?): Observable<String>

    fun setPasswordInfo(context: Context, data: String?): Observable<String>

    fun syncIssuedState(context: Context, syncIssuedStateBean: SyncIssuedStateBean)
}