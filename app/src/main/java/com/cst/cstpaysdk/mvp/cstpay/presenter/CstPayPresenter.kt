package com.cst.cstpaysdk.mvp.cstpay.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ResCstPayBean
import com.cst.cstpaysdk.mvp.cstpay.model.ICstPayModel
import com.cst.cstpaysdk.mvp.cstpay.model.impl.CstPayModelImpl
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：支付功能，向后台发起支付的P层（调度层）
 * 影响范围：支付
 * 备注：
 * @cst_end
 */
class CstPayPresenter(private val context: Context) : BasePresenter<ICstPayModel>() {

    private val cstPayModel: ICstPayModel = CstPayModelImpl(context)

    fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView?) {
        cstPayModel.cstOnlinePay(context, payInfoBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResCstPayBean>(cstPayView) {

                override fun onNext(@NonNull resCstPayBean: ResCstPayBean) {
                    cstPayView?.cstPaySuccess(resCstPayBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    if(e.message == "000000") {
                        cstOfflinePay(payInfoBean, cstPayView)
                    } else {
                        cstPayView?.cstPayFailure(e)
                    }
                }

                override fun onComplete() {
                }
            })
    }

    fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView?) {
        cstPayModel.cstOfflinePay(context, payInfoBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResCstPayBean>(cstPayView) {

                override fun onNext(@NonNull resCstPayBean: ResCstPayBean) {
                    cstPayView?.cstPaySuccess(resCstPayBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    cstPayView?.cstPayFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}