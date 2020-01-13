package com.cst.cstpaysdk.mvp.wallpaper.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResWallpaperBean

interface IWallpaperView : IBaseView {

    fun getWallpaperSuccess(resWallpaperBean: ResWallpaperBean)

    fun getWallpaperFailure(error: Throwable)
}