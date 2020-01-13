package com.cst.cstpaysdk.mvp.traderecord.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResTradeRecordBean
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean
import io.reactivex.Observable

interface ITradeRecordModel {

    /**
     * 离线查询消费记录（查询设备本地数据库的消费记录）
     *
     * @param context 上下文
     */
    fun offlineQueryTradeRecord(context: Context, startTime: String, endTime: String): Observable<ResTradeRecordBean>

    /**
     * 在线查询消费记录（查询服务端的消费记录）
     *
     * @param context 上下文
     */
    fun onlineQueryTradeRecord(context: Context, startTime: String, endTime: String): Observable<ResTradeRecordBean>

    /**
     * 上传脱机（离线）消费记录（将还未成功上传到平台的消费记录上传）
     *
     * @param context 上下文
     */
    fun uploadTradeRecord(context: Context): Observable<ResUploadTradeRecordBean>
}