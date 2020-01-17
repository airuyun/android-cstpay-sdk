package com.cst.cstpaysdk.mvp.takefood.recfoodinfo.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResRecFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.model.IRecFoodInfoModel
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.model.impl.RecFoodInfoModelImpl
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：获取推荐食品图片的P层（调度层）
 * 影响范围：获取推荐食品图片
 * 备注：
 * @cst_end
 */
class RecFoodInfoPresenter(val context: Context) : BasePresenter<IRecFoodInfoModel>() {

    private val recFoodInfoModel: IRecFoodInfoModel = RecFoodInfoModelImpl()

    fun getFoodPhoto(iRecFoodInfoView: IRecFoodInfoView?) {
        recFoodInfoModel.getRecFoodInfo(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResRecFoodInfoBean>(iRecFoodInfoView) {

                override fun onNext(@NonNull recFoodInfo: ResRecFoodInfoBean) {
                    iRecFoodInfoView?.getRecFoodInfoSuccess(recFoodInfo)
                }

                override fun onError(@NonNull e: Throwable) {
                    iRecFoodInfoView?.getRecFoodInfoFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}