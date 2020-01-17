package com.cst.cstpaysdk.manager

import android.content.Context
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.confirm.presenter.ConfirmTakeFoodPresenter
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import com.cst.cstpaysdk.mvp.takefood.info.presenter.TakeFoodInfoPresenter
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.presenter.RecFoodInfoPresenter
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView

/**
 * @author TJS
 * @date 2020/01/17 14:48
 * @cst_do 取餐接口调度层，为了实现取餐接口内部有修改时，不影响接口调用层
 * 影响范围：取餐模块所有接口
 * 备注：
 * @cst_end
 */
internal class TakeFoodManager(context: Context) : ITakeFoodManager {

    private val mRecFoodInfoPre: RecFoodInfoPresenter = RecFoodInfoPresenter(context)
    private val mTakeFoodInfoPre: TakeFoodInfoPresenter = TakeFoodInfoPresenter(context)
    private val mConfirmTakeFoodPre: ConfirmTakeFoodPresenter = ConfirmTakeFoodPresenter(context)

    override fun getRecFoodInfo(iRecFoodInfoView: IRecFoodInfoView?) {
        mRecFoodInfoPre.getFoodPhoto(iRecFoodInfoView)
    }

    override fun getTakeFoodInfo(userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?) {
        mTakeFoodInfoPre.getTakeFoodInfo(userCode, iTakeFoodInfoView)
    }

    override fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?) {
        mConfirmTakeFoodPre.confirmTakeFood(takeFoodInfo, iConfirmTakeFoodView)
    }
}