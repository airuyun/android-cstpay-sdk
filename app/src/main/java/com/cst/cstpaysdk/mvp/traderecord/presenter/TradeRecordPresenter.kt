package com.cst.cstpaysdk.mvp.traderecord.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResTradeRecordBean
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean
import com.cst.cstpaysdk.mvp.traderecord.model.ITradeRecordModel
import com.cst.cstpaysdk.mvp.traderecord.model.impl.TradeRecordModelImpl
import com.cst.cstpaysdk.mvp.traderecord.view.ITradeRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadTradeRecordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TradeRecordPresenter(private val context: Context) : BasePresenter<ITradeRecordModel>() {

    private val tradeRecordModel: ITradeRecordModel = TradeRecordModelImpl()

    fun offlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?) {
        tradeRecordModel.offlineQueryTradeRecord(context, startTime, endTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResTradeRecordBean>(tradeRecordView) {

                override fun onNext(@NonNull resTradeRecordBean: ResTradeRecordBean) {
                    tradeRecordView?.queryTradeRecordSuccess(resTradeRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    tradeRecordView?.queryTradeRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

    fun onlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?) {
        tradeRecordModel.onlineQueryTradeRecord(context, startTime, endTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResTradeRecordBean>(tradeRecordView) {

                override fun onNext(@NonNull resTradeRecordBean: ResTradeRecordBean) {
                    tradeRecordView?.queryTradeRecordSuccess(resTradeRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    tradeRecordView?.queryTradeRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

    fun uploadTradeRecord(uploadTradeRecordView: IUploadTradeRecordView?) {
        tradeRecordModel.uploadTradeRecord(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResUploadTradeRecordBean>(uploadTradeRecordView) {

                override fun onNext(@NonNull resUploadTradeRecordBean: ResUploadTradeRecordBean) {
                    uploadTradeRecordView?.uploadTradeRecordSuccess(resUploadTradeRecordBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    uploadTradeRecordView?.uploadTradeRecordFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}