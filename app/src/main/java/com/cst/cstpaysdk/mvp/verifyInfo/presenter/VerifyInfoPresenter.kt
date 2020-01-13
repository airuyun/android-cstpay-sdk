package com.cst.cstpaysdk.mvp.verifyInfo.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResVerifyInfoBean
import com.cst.cstpaysdk.mvp.verifyInfo.model.IVerifyInfoModel
import com.cst.cstpaysdk.mvp.verifyInfo.model.impl.VerifyInfoModelImpl
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VerifyInfoPresenter(private val context: Context) : BasePresenter<IVerifyInfoModel>() {

    private val verifyInfoModel: IVerifyInfoModel = VerifyInfoModelImpl()

    fun getVerifyInfo(secretKeyType: String, verifyInfoView: IVerifyInfoView?) {
        verifyInfoModel.getVerifyInfo(context, secretKeyType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResVerifyInfoBean>(verifyInfoView) {
                override fun onNext(@NonNull resVerifyInfoBean: ResVerifyInfoBean) {
                    verifyInfoView?.getVerifyInfoSuccess(resVerifyInfoBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    verifyInfoView?.getVerifyInfoFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

}