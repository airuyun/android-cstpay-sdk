package com.cst.cstpaysdk.mvp.register.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.InitInfoBean
import com.cst.cstpaysdk.bean.ResRegisterBean
import com.cst.cstpaysdk.manager.CstPayManager
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.register.model.IRegisterModel
import com.cst.cstpaysdk.mvp.register.model.impl.RegisterModelImpl
import com.cst.cstpaysdk.util.ConstantUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterPresenter(private val context: Context) : BasePresenter<IRegisterModel>() {

    private val registerModel: IRegisterModel = RegisterModelImpl()

    fun register(initInfoBean: InitInfoBean?, initView: IInitView?) {
        registerModel.register(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResRegisterBean>(initView) {

                override fun onNext(@NonNull resRegisterBean: ResRegisterBean) {
                    CstPayManager(context).startBeatService()
                    val equipmentId: String? = resRegisterBean.data?.equipmentId
                    val equipmentNo: String? = resRegisterBean.data?.equipmentNo
                    ConstantUtils.setEquipmentId(equipmentId)
                    ConstantUtils.setEquipmentNo(equipmentNo)
                    initView?.initSuccess(equipmentId, equipmentNo)
                }

                override fun onError(@NonNull e: Throwable) {
                    initView?.initFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}