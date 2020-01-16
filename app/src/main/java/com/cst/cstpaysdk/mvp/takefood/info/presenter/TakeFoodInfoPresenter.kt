package com.cst.cstpaysdk.mvp.takefood.info.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.info.model.ITakeFoodInfoModel
import com.cst.cstpaysdk.mvp.takefood.info.model.impl.TakeFoodInfoModelImpl
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：获取取餐菜品（所点菜品）的P层（调度层）
 * 影响范围：获取取餐菜品（所点菜品）
 * 备注：
 * @cst_end
 */
class TakeFoodInfoPresenter(val context: Context) : BasePresenter<ITakeFoodInfoModel>() {

    private val takeFoodInfoModel: ITakeFoodInfoModel = TakeFoodInfoModelImpl()

    fun getTakeFoodInfo(shopId: String?, userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?) {
        takeFoodInfoModel.getTakeFoodInfo(context, shopId, userCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResTakeFoodInfoBean>(iTakeFoodInfoView) {

                override fun onNext(@NonNull takeFoodInfo: ResTakeFoodInfoBean) {
                    iTakeFoodInfoView?.getTakeFoodInfoSuccess(takeFoodInfo)
                }

                override fun onError(@NonNull e: Throwable) {
                    iTakeFoodInfoView?.getTakeFoodInfoFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}