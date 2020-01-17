package com.cst.cstpaysdk.mvp.traderecord.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResPayRecordBean
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean
import io.reactivex.Observable

interface ITradeRecordModel {

    /**
     * 离线查询支付记录（查询设备本地数据库的支付记录）
     *
     * @param context 上下文
     */
    fun offlineQueryPayRecord(context: Context, startTime: String, endTime: String): Observable<ResPayRecordBean>

    /**
     * 在线查询支付记录（查询服务端的支付记录）
     *
     * @param context 上下文
     */
    fun onlineQueryPayRecord(context: Context, startTime: String, endTime: String): Observable<ResPayRecordBean>

    /**
     * 上传脱机（离线）支付记录（将还未成功上传到平台的支付记录上传）
     *
     * @param context 上下文
     */
    fun uploadPayRecord(context: Context): Observable<ResUploadTradeRecordBean>
}