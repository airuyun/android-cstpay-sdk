package com.cst.cstpaysdk.manager

import android.content.Context
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.mvp.cstpay.presenter.CstPayPresenter
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.traderecord.presenter.TradeRecordPresenter
import com.cst.cstpaysdk.mvp.traderecord.view.IPayRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadPayRecordView
import com.cst.cstpaysdk.net.NetworkUtils

/**
 * @author TJS
 * @date 2020/01/17 14:49
 * @cst_do 支付接口调度层，为了实现取餐接口内部有修改时，不影响接口调用层
 * 影响范围：支付模块所有接口，本SDK已经实现在线支付异常自动转离线支付，在线支付异常无需应用层维护
 * 备注：
 * @cst_end
 */
internal class CstPayManager(private val context: Context) : ICstPayManager {

    private val tradeRecordPre: TradeRecordPresenter = TradeRecordPresenter(context)
    private val cstPayPre: CstPayPresenter = CstPayPresenter(context)

    override fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        if(NetworkUtils.isConnected(context)) {
            this.cstOnlinePay(payInfoBean, cstPayView)
        } else {
            this.cstOfflinePay(payInfoBean, cstPayView)
        }
    }

    override fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayPre.cstOnlinePay(payInfoBean, cstPayView)
    }

    override fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayPre.cstOfflinePay(payInfoBean, cstPayView)
    }

    override fun offlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        tradeRecordPre.offlineQueryTradeRecord(startTime, endTime, payRecordView)
    }

    override fun onlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        tradeRecordPre.onlineQueryTradeRecord(startTime, endTime, payRecordView)
    }

    override fun uploadPayRecord(uploadPayRecordView: IUploadPayRecordView?) {
        if(NetworkUtils.isConnected(context)) {
            tradeRecordPre.uploadTradeRecord(uploadPayRecordView)
        }
    }
}