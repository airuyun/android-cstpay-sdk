package com.cst.cstpaysdk.mvp.init.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.InitInfoBean
import com.cst.cstpaysdk.manager.CstPayManager
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

    fun init(initInfo: InitInfoBean?, initView: IInitView?) {
        initModel.init(context, initInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<InitInfoBean>(initView) {

                override fun onNext(@NonNull initInfoBean: InitInfoBean) {
                    CstPayManager(context).register(initInfo, initView)
                }

                override fun onError(@NonNull e: Throwable) {
                    initView?.initFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}