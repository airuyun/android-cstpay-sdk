package com.cst.cstpaysdk.mvp.shopinfo.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResInitBean
import com.cst.cstpaysdk.bean.ResShopInfoBean
import com.cst.cstpaysdk.manager.CstApiManager
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.shopinfo.model.IShopInfoModel
import com.cst.cstpaysdk.mvp.shopinfo.model.impl.ShopInfoModelImpl
import com.cst.cstpaysdk.util.ConstantUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShopInfoPresenter(private val context: Context) : BasePresenter<IShopInfoModel>() {

    private val shopInfoModel: IShopInfoModel = ShopInfoModelImpl()

    fun getShopInfo(initView: IInitView?) {
        shopInfoModel.getShopInfo(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResShopInfoBean>(initView) {

                override fun onNext(@NonNull resShopInfoBean: ResShopInfoBean) {
                    CstApiManager(context).startBeatService()
                    ConstantUtils.shopId = resShopInfoBean.data?.shopId
                    ConstantUtils.shopName = resShopInfoBean.data?.shopName
                    val resInitBean = ResInitBean()
                    resInitBean.equipmentId = ConstantUtils.equipmentId
                    resInitBean.equipmentNo = ConstantUtils.equipmentNo
                    resInitBean.shopId = ConstantUtils.shopId
                    resInitBean.shopName = ConstantUtils.shopName
                    initView?.initSuccess(resInitBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    initView?.initFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

}