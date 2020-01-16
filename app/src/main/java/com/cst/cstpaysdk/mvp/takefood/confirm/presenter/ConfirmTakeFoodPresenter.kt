package com.cst.cstpaysdk.mvp.takefood.confirm.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.confirm.model.IConfirmTakeFoodModel
import com.cst.cstpaysdk.mvp.takefood.confirm.model.impl.ConfirmTakeFoodModelImpl
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：确认取餐的P层（调度层）
 * 影响范围：确认取餐
 * 备注：
 * @cst_end
 */
class ConfirmTakeFoodPresenter(val context: Context) : BasePresenter<IConfirmTakeFoodModel>() {

    private val confirmTakeFoodModel: IConfirmTakeFoodModel = ConfirmTakeFoodModelImpl()

    fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?) {
        confirmTakeFoodModel.confirmTakeFood(context, takeFoodInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<Boolean>(iConfirmTakeFoodView) {

                override fun onNext(@NonNull confirmSuccess: Boolean) {
                    iConfirmTakeFoodView?.confirmTakeFoodSuccess(confirmSuccess)
                }

                override fun onError(@NonNull e: Throwable) {
                    iConfirmTakeFoodView?.confirmTakeFoodFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}