package com.cst.cstpaysdk.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import com.cst.cstpaysdk.bean.InitInfoBean
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.mvp.credit.presenter.EquipmentCreditPresenter
import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import com.cst.cstpaysdk.mvp.cstpay.presenter.CstPayPresenter
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.faceinfo.presenter.FaceInfoPresenter
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.presenter.InitPresenter
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.register.presenter.RegisterPresenter
import com.cst.cstpaysdk.mvp.shopinfo.presenter.ShopInfoPresenter
import com.cst.cstpaysdk.mvp.shopinfo.view.IShopInfoView
import com.cst.cstpaysdk.mvp.traderecord.presenter.TradeRecordPresenter
import com.cst.cstpaysdk.mvp.traderecord.view.ITradeRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadTradeRecordView
import com.cst.cstpaysdk.mvp.verifyInfo.presenter.VerifyInfoPresenter
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import com.cst.cstpaysdk.mvp.wallpaper.presenter.WallpaperPresenter
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView
import com.cst.cstpaysdk.mvp.websocket.presenter.WebSocketPresenter
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener
import com.cst.cstpaysdk.net.NetworkUtils
import com.cst.cstpaysdk.service.WebSocketService
import com.cst.cstpaysdk.util.LocalUtils
import com.cst.cstpaysdk.util.LogUtil

class CstPayManager(private val context: Context) : ICstPayManager {

    private val initPre: InitPresenter = InitPresenter(context)
    private val registerPre: RegisterPresenter = RegisterPresenter(context)
    private val verifyInfoPre: VerifyInfoPresenter = VerifyInfoPresenter(context)
    private val shopInfoPre: ShopInfoPresenter = ShopInfoPresenter(context)
    private val wallpaperPre: WallpaperPresenter = WallpaperPresenter(context)
    private val tradeRecordPre: TradeRecordPresenter = TradeRecordPresenter(context)
    private val cstPayPre: CstPayPresenter = CstPayPresenter(context)
    private val mWebSocketPre: WebSocketPresenter = WebSocketPresenter(context)
    private val mFaceInfoPre: FaceInfoPresenter = FaceInfoPresenter(context)
    private val mEquipmentCreditPre: EquipmentCreditPresenter = EquipmentCreditPresenter(context)

    override fun init(initInfoBean: InitInfoBean?, initView: IInitView?) {
        initPre.init(initInfoBean, initView)
    }

    override fun register(initInfoBean: InitInfoBean?, initView: IInitView?) {
        registerPre.register(initInfoBean, initView)
    }

    override fun startBeatService() {
        val serviceHasStart = LocalUtils.hasServiceStart(context, WebSocketService::class.java.name)
        LogUtil.customLog(context, "心跳服务是否已经启动？ = $serviceHasStart")
        if(!serviceHasStart) {
            val intent = Intent()
            intent.component = ComponentName(context.applicationContext, WebSocketService::class.java.name)
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
            intent.component = ComponentName(context.applicationContext, WebSocketService::class.java.name)
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

    override fun getShopInfo(shopInfoView: IShopInfoView?) {
        shopInfoPre.getShopInfo(shopInfoView)
    }

    override fun getWallpaper(wallpaperView: IWallpaperView?) {
        wallpaperPre.getWallpaper(wallpaperView)
    }

    override fun offlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?) {
        tradeRecordPre.offlineQueryTradeRecord(startTime, endTime, tradeRecordView)
    }

    override fun onlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?) {
        tradeRecordPre.onlineQueryTradeRecord(startTime, endTime, tradeRecordView)
    }

    override fun uploadTradeRecord(uploadTradeRecordView: IUploadTradeRecordView?) {
        if(NetworkUtils.isConnected(context.applicationContext)) {
            tradeRecordPre.uploadTradeRecord(uploadTradeRecordView)
        }
    }

    override fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        if(NetworkUtils.isConnected(context.applicationContext)) {
            this.cstOnlinePay(payInfoBean, cstPayView)
        } else {
            this.cstOfflinePay(payInfoBean, cstPayView)
        }
    }

    override fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayPre.cstOnlinePay(payInfoBean, cstPayView)
    }

    override fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayPre.cstOfflinePay(payInfoBean, cstPayView)
    }

    override fun webSocketConnect(webSocketView: IWebSocketView?) {
        mWebSocketPre.webSocketConnect(this, webSocketView)
    }

    override fun beatConnect(listener: CstWebSocketListener) {
        mWebSocketPre.beatConnect(listener)
    }

    override fun getFaceInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getFaceInfo(listener)
    }

    override fun getCardInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getCardInfo(listener)
    }

    override fun getFingerprintInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getFingerprintInfo(listener)
    }

    override fun getPasswordCardInfo(listener: CstWebSocketListener) {
        mWebSocketPre.getPasswordCardInfo(listener)
    }

    override fun setFaceInfo(data: String?) {
        mWebSocketPre.setFaceInfo(data)
    }

    override fun setCardInfo(data: String?) {
        mWebSocketPre.setCardInfo(data)
    }

    override fun setFingerprintInfo(data: String?) {
        mWebSocketPre.setFingerprintInfo(data)
    }

    override fun setPasswordInfo(data: String?) {
        mWebSocketPre.setPasswordInfo(data)
    }

    override fun setEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?) {
        mEquipmentCreditPre.resEquipmentCreditInfo(data, resEquipmentCreditView)
    }

    override fun syncIssuedState(listener: CstWebSocketListener) {
        mWebSocketPre.syncIssuedState(listener)
    }

    override fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?) {
        mFaceInfoPre.getFaceInfo(userId, faceInfoView)
    }
}