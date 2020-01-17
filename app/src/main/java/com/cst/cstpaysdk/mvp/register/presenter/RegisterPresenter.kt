package com.cst.cstpaysdk.mvp.register.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.bean.ResRegisterBean
import com.cst.cstpaysdk.manager.CstApiManager
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.register.model.IRegisterModel
import com.cst.cstpaysdk.mvp.register.model.impl.RegisterModelImpl
import com.cst.cstpaysdk.util.ConstantUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterPresenter(private val context: Context) : BasePresenter<IRegisterModel>() {

    private val registerModel: IRegisterModel = RegisterModelImpl()

    fun register(reqInitBean: ReqInitBean?, initView: IInitView?) {
        registerModel.register(context, reqInitBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResRegisterBean>(initView) {

                override fun onNext(@NonNull resRegisterBean: ResRegisterBean) {
                    ConstantUtils.equipmentId = resRegisterBean.data?.equipmentId
                    ConstantUtils.equipmentNo = resRegisterBean.data?.equipmentNo
                    CstApiManager(context).getShopInfo(initView)
                }

                override fun onError(@NonNull e: Throwable) {
                    initView?.initFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}