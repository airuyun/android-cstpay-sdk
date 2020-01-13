package com.cst.cstpaysdk.mvp.websocket.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.manager.CstPayManager
import com.cst.cstpaysdk.mvp.websocket.model.IWebSocketModel
import com.cst.cstpaysdk.mvp.websocket.model.impl.WebSocketModelIml
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WebSocketPresenter(private val context: Context) : BasePresenter<IWebSocketModel>() {

    private val webSocketModel: IWebSocketModel = WebSocketModelIml(context)

    fun webSocketConnect(cstPayManager: CstPayManager, webSocketView: IWebSocketView?) {
        webSocketModel.webSocketConnect(cstPayManager)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<CstWebSocketListener>(webSocketView) {

                override fun onNext(@NonNull listener: CstWebSocketListener) {
                    webSocketView?.webSocketConnect(listener)
                }

                override fun onError(@NonNull e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    fun beatConnect(listener: CstWebSocketListener) {
        webSocketModel.beatConnect(context, listener)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getFaceInfo(listener: CstWebSocketListener) {

    }

    fun getCardInfo(listener: CstWebSocketListener) {

    }

    fun getFingerprintInfo(listener: CstWebSocketListener) {

    }

    fun getPasswordCardInfo(listener: CstWebSocketListener) {

    }

    fun setFaceInfo(data: String?) {
        webSocketModel.setFaceInfo(context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun setCardInfo(data: String?) {
        webSocketModel.setCardInfo(context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun setFingerprintInfo(data: String?) {
        webSocketModel.setFingerprintInfo(context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun setPasswordInfo(data: String?) {
        webSocketModel.setPasswordInfo(context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun syncIssuedState(listener: CstWebSocketListener) {

    }
}