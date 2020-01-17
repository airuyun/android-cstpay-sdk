package com.cst.cstpaysdk.manager

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

interface ICstOpenApiManager {

    /**
     * 数据初始化，需要在调用本SDK的APP的application的onCreate中进行数据的初始化
     */
    fun init(reqInitBean: ReqInitBean?, initView: IInitView?)

    /**
     * 通过用户ID查询本地数据库获取人脸信息
     */
    fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?)

    /**
     * 康索特三代平台支付，先调用在线支付，在线支付失败则自动转为离线支付
     */
    fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 离线查询支付记录（查询设备本地数据库的支付记录）
     */
    fun offlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?)

    /**
     * 获取店铺的精品菜（推荐菜品）信息
     */
    fun getRecFoodInfo(iRecFoodInfoView: IRecFoodInfoView?)

    /**
     * 获取用户在该店铺的取餐信息
     */
    fun getTakeFoodInfo(userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?)

    /**
     * 确认取餐
     */
    fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?)
}