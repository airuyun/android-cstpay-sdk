package com.cst.cstpaysdk.manager

import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.traderecord.view.IPayRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadPayRecordView

internal interface ICstPayManager {

    /**
     * 康索特三代平台支付，先调用在线支付，在线支付失败则自动转为离线支付
     */
    fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 康索特三代平台在线支付
     */
    fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 康索特三代平台脱机（离线）支付
     */
    fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 离线线查询支付记录（查询设备本地数据库的支付记录）
     */
    fun offlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?)

    /**
     * 在线查询支付记录（查询服务端支付记录）
     */
    fun onlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?)

    /**
     * 上传脱机（离线）支付记录（将还未成功上传到平台的支付记录上传）
     */
    fun uploadPayRecord(uploadPayRecordView: IUploadPayRecordView?)
}