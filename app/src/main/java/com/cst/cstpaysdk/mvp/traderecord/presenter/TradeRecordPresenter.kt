package com.cst.cstpaysdk.mvp.traderecord.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResPayRecordBean
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean
import com.cst.cstpaysdk.mvp.traderecord.model.ITradeRecordModel
import com.cst.cstpaysdk.mvp.traderecord.model.impl.TradeRecordModelImpl
import com.cst.cstpaysdk.mvp.traderecord.view.IPayRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadPayRecordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TradeRecordPresenter(private val context: Context) : BasePresenter<ITradeRecordModel>() {

    private val tradeRecordModel: ITradeRecordModel = TradeRecordModelImpl()

    fun offlineQueryTradeRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        tradeRecordModel.offlineQueryPayRecord(context, startTime, endTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResPayRecordBean>(payRecordView) {

                override fun onNext(@NonNull resPayRecordBean: ResPayRecordBean) {
                    payRecordView?.queryPayRecordSuccess(resPayRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    payRecordView?.queryPayRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

    fun onlineQueryTradeRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        tradeRecordModel.onlineQueryPayRecord(context, startTime, endTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResPayRecordBean>(payRecordView) {

                override fun onNext(@NonNull resPayRecordBean: ResPayRecordBean) {
                    payRecordView?.queryPayRecordSuccess(resPayRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    payRecordView?.queryPayRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

    fun uploadTradeRecord(uploadPayRecordView: IUploadPayRecordView?) {
        tradeRecordModel.uploadPayRecord(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResUploadTradeRecordBean>(uploadPayRecordView) {

                override fun onNext(@NonNull resUploadTradeRecordBean: ResUploadTradeRecordBean) {
                    uploadPayRecordView?.uploadPayRecordSuccess(resUploadTradeRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    uploadPayRecordView?.uploadPayRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}