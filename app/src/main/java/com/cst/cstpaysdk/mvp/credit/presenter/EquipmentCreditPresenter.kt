package com.cst.cstpaysdk.mvp.credit.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResCstPayBean
import com.cst.cstpaysdk.mvp.credit.model.IEquipmentCreditModel
import com.cst.cstpaysdk.mvp.credit.model.impl.EquipmentCreditImpl
import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：
 * 影响范围：
 * 备注：
 * @cst_end
 */
class EquipmentCreditPresenter(private val context: Context) : BasePresenter<IResEquipmentCreditView>() {

    private val equipmentCreditModel: IEquipmentCreditModel = EquipmentCreditImpl()

    fun resEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?) {
        equipmentCreditModel.resEquipmentCreditInfo(context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : BaseObserver<ResCstPayBean>(resEquipmentCreditView) {

                override fun onNext(@NonNull resCstPayBean: ResCstPayBean) {
                }

                override fun onError(@NonNull e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }
}