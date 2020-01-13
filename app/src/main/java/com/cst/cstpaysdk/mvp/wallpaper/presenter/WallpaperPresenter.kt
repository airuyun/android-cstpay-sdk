package com.cst.cstpaysdk.mvp.wallpaper.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResWallpaperBean
import com.cst.cstpaysdk.mvp.wallpaper.model.IWallpaperModel
import com.cst.cstpaysdk.mvp.wallpaper.model.impl.WallpaperModelImpl
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WallpaperPresenter(private val context: Context) : BasePresenter<IWallpaperModel>() {

    private val wallpaperModel: IWallpaperModel = WallpaperModelImpl()

    fun getWallpaper(wallpaperView: IWallpaperView?) {
        wallpaperModel.getWallpaper(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResWallpaperBean>(wallpaperView) {

                override fun onNext(@NonNull resWallpaperBean: ResWallpaperBean) {
                    wallpaperView?.getWallpaperSuccess(resWallpaperBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    wallpaperView?.getWallpaperFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

}