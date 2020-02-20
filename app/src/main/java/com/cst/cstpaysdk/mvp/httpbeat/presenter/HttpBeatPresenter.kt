package com.cst.cstpaysdk.mvp.httpbeat.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResBeatConnectBean
import com.cst.cstpaysdk.mvp.httpbeat.model.IHttpBeatModel
import com.cst.cstpaysdk.mvp.httpbeat.model.impl.HttpBeatModelImpl
import com.cst.cstpaysdk.mvp.httpbeat.view.IHttpBeatView
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：
 * 影响范围：
 * 备注：
 * @cst_end
 */
class HttpBeatPresenter(private val context: Context) : BasePresenter<IHttpBeatView>() {

    private val httpBeatModel: IHttpBeatModel = HttpBeatModelImpl()

    fun httpBeatConnect(httpBeatView: IHttpBeatView?) {
        httpBeatModel.httpBeatConnect(context)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : BaseObserver<ResBeatConnectBean>(httpBeatView) {

                override fun onNext(@NonNull resBeatConnectBean: ResBeatConnectBean) {

                }

                override fun onError(@NonNull e: Throwable) {

                }

                override fun onComplete() {
                }
            })
    }
}