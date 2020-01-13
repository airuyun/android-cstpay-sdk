package com.cst.cstpaysdk.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：RxJava观察者基类
 * 影响范围：MVP模式的所有观察者
 * 备注：
 * @cst_end
 */
abstract class BaseObserver<T : Any?>(private val iBaseView: IBaseView?) : Observer<T>{
    override fun onSubscribe(d: Disposable) {
        iBaseView?.addDispose(d)
    }
}