package com.cst.cstpaysdk.mvp.httpbeat.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResBeatConnectBean
import io.reactivex.Observable

interface IHttpBeatModel {

    /**
     * HTTP心跳请求（在build.gradle中配置选择使用HTTP还是WebSocket协议发起心跳请求）
     *
     * @param context 上下文
     */
    fun httpBeatConnect(context: Context): Observable<ResBeatConnectBean>
}