package com.cst.cstpaysdk.mvp.wallpaper.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResWallpaperBean
import io.reactivex.Observable

interface IWallpaperModel {

    /**
     * 获取屏保壁纸
     *
     * @param context 上下文
     */
    fun getWallpaper(context: Context): Observable<ResWallpaperBean>
}