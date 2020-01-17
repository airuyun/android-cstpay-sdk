package com.cst.cstpaysdk.mvp.init.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.manager.CstApiManager
import com.cst.cstpaysdk.mvp.init.model.IInitModel
import com.cst.cstpaysdk.mvp.init.model.impl.InitModelImpl
import com.cst.cstpaysdk.mvp.init.view.IInitView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：
 * 影响范围：
 * 备注：
 * @cst_end
 */
class InitPresenter(private val context: Context) : BasePresenter<IInitView>() {

    private val initModel: IInitModel = InitModelImpl()

    fun init(reqInit: ReqInitBean?, initView: IInitView?) {
        initModel.init(context, reqInit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ReqInitBean>(initView) {

                override fun onNext(@NonNull reqInitBean: ReqInitBean) {
                    CstApiManager(context).register(reqInit, initView)
                }

                override fun onError(@NonNull e: Throwable) {
                    initView?.initFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}