package com.cst.cstpaysdk.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.mvp.httpbeat.presenter.HttpBeatPresenter
import com.cst.cstpaysdk.mvp.httpbeat.view.IHttpBeatView
import com.cst.cstpaysdk.mvp.faceinfo.presenter.FaceInfoPresenter
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.presenter.InitPresenter
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.register.presenter.RegisterPresenter
import com.cst.cstpaysdk.mvp.shopinfo.presenter.ShopInfoPresenter
import com.cst.cstpaysdk.mvp.verifyInfo.presenter.VerifyInfoPresenter
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import com.cst.cstpaysdk.mvp.wallpaper.presenter.WallpaperPresenter
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView
import com.cst.cstpaysdk.service.WebSocketService
import com.cst.cstpaysdk.util.LocalUtils
import com.cst.cstpaysdk.util.LogUtil

/**
 * @author TJS
 * @date 2020/01/17 14:48
 * @cst_do 公共接口调度层，为了实现接口内部有修改时，不影响接口调用层
 * 影响范围：公共模块所有接口
 * 备注：康索特所有三代平台Android设备都需要调用这些接口，因此将公共部分接口单独分一个独立模块
 * @cst_end
 */
internal class CommonManager(private val context: Context) : ICommonManager {

    private val initPre: InitPresenter = InitPresenter(context)
    private val registerPre: RegisterPresenter = RegisterPresenter(context)
    private val httpBeatPre:HttpBeatPresenter = HttpBeatPresenter(context)
    private val verifyInfoPre: VerifyInfoPresenter = VerifyInfoPresenter(context)
    private val shopInfoPre: ShopInfoPresenter = ShopInfoPresenter(context)
    private val wallpaperPre: WallpaperPresenter = WallpaperPresenter(context)
    private val mFaceInfoPre: FaceInfoPresenter = FaceInfoPresenter(context)

    override fun init(reqInitBean: ReqInitBean?, initView: IInitView?) {
        initPre.init(reqInitBean, initView)
    }

    override fun register(reqInitBean: ReqInitBean?, initView: IInitView?) {
        registerPre.register(reqInitBean, initView)
    }

    override fun httpBeatConnect(httpBeatView: IHttpBeatView?) {
        httpBeatPre.httpBeatConnect(httpBeatView)
    }

    override fun getShopInfo(initView: IInitView?) {
        shopInfoPre.getShopInfo(initView)
    }

    override fun startBeatService() {
        val serviceHasStart = LocalUtils.hasServiceStart(context, WebSocketService::class.java.name)
        LogUtil.customLog(context, "心跳服务是否已经启动？ = $serviceHasStart")
        if(!serviceHasStart) {
            val intent = Intent()
            intent.component = ComponentName(context, WebSocketService::class.java.name)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            LogUtil.customLog(context, "启动心跳服务")
        }
    }

    override fun stopBeatService() {
        val serviceHasStart = LocalUtils.hasServiceStart(context, WebSocketService::class.java.name)
        LogUtil.customLog(context, "心跳服务是否已经启动？ = $serviceHasStart")
        if(serviceHasStart) {
            val intent = Intent()
            intent.component = ComponentName(context, WebSocketService::class.java.name)
            context.stopService(intent)
            LogUtil.customLog(context, "停止心跳服务")
        }
    }

    override fun getCardSecretKey(verifyInfoView: IVerifyInfoView?) {
        verifyInfoPre.getVerifyInfo("01", verifyInfoView)
    }

    override fun getCommSecretKey(verifyInfoView: IVerifyInfoView?) {
        verifyInfoPre.getVerifyInfo("02", verifyInfoView)
    }

    override fun getWallpaper(wallpaperView: IWallpaperView?) {
        wallpaperPre.getWallpaper(wallpaperView)
    }

    override fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?) {
        mFaceInfoPre.getFaceInfoByUserId(userId, faceInfoView)
    }
}