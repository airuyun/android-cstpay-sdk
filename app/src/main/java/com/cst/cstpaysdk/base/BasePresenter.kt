package com.cst.cstpaysdk.base

import android.view.View
import com.cst.cstpaysdk.manager.RxJavaManager

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：MVP模式的P层（调度层）的基类
 * 影响范围：MVP架构的中间层
 * 备注：
 * @cst_end
 */
open class BasePresenter<Model> {

    private var mView: View? = null
    private var mModel: Model? = null

    /**
     * 持有（绑定）activity
     */
    fun attachView(view: View, model: Model) {
        mView = view
        mModel = model
    }

    /**
     * 释放activity
     */
    fun detachView() {
        RxJavaManager.clear()
        mView = null
        mModel = null
    }
}