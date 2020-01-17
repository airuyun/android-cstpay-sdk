package com.cst.cstpaysdk.mvp.init.model

import android.content.Context
import com.cst.cstpaysdk.bean.ReqInitBean
import io.reactivex.Observable

interface IInitModel {

    /**
     * 数据初始化，将IP、端口保存到本地文件中，为了和调用module解耦
     */
    fun init(context: Context, reqInit: ReqInitBean?): Observable<ReqInitBean>
}