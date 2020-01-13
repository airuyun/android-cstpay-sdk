package com.cst.cstpaysdk.manager

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do RxJava管理类，主要用于Activity被销毁时取消观察者的订阅，避免RxJava造成内存泄漏
 * 影响范围：RxJava
 * 备注：
 * @cst_end
 */
object RxJavaManager {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.dispose()
    }

}