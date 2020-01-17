package com.cst.cstpaysdk.manager

import android.content.Context
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView
import com.cst.cstpaysdk.mvp.traderecord.view.IPayRecordView

/**
 * @author TJS
 * @date 2020/01/17 14：38
 * @cst_do 开放给应用层使用的接口，应用层不关心的代码请不要放到本类中
 * 影响范围：开放给应用层使用的所有接口
 * 备注：
 * @cst_end
 */
class CstOpenApiManager(context: Context) : ICstOpenApiManager {

    private val cstApiManager: ICstApiManager = CstApiManager(context.applicationContext)

    override fun init(reqInitBean: ReqInitBean?, initView: IInitView?) {
        cstApiManager.init(reqInitBean, initView)
    }

    override fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?) {
        cstApiManager.getFaceInfoByUserId(userId, faceInfoView)
    }

    override fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstApiManager.cstPay(payInfoBean, cstPayView)
    }

    override fun offlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        cstApiManager.offlineQueryPayRecord(startTime, endTime, payRecordView)
    }

    override fun getRecFoodInfo(iRecFoodInfoView: IRecFoodInfoView?) {
        cstApiManager.getRecFoodInfo(iRecFoodInfoView)
    }

    override fun getTakeFoodInfo(userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?) {
        cstApiManager.getTakeFoodInfo(userCode, iTakeFoodInfoView)
    }

    override fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?) {
        cstApiManager.confirmTakeFood(takeFoodInfo, iConfirmTakeFoodView)
    }
}