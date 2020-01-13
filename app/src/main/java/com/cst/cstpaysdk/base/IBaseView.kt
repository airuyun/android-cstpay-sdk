package com.cst.cstpaysdk.base

import io.reactivex.disposables.Disposable

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：MVP架构的V层回调接口的基类
 * 影响范围：MVP架构的V层回调接口
 * 备注：
 * @cst_end
 */
interface IBaseView {
    fun addDispose(d: Disposable)
}