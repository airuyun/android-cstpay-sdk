package com.cst.cstpaysdk.manager

import android.content.Context
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView
import com.cst.cstpaysdk.mvp.traderecord.view.IPayRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadPayRecordView
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener

/**
 * @author TJS
 * @date 2020/01/17 14：38
 * @cst_do 接口统一调度层，为了实现接口内部有修改时，不影响接口调用层
 * 影响范围：本SDK所有接口
 * 备注：暂时将支付、取餐、公共、WebSocket模块放在同一个SDK，后期可根据需要进行分离
 * @cst_end
 */
class CstApiManager(context: Context) : ICstApiManager {

    private val commonManager: ICommonManager = CommonManager(context.applicationContext)
    private val webSocketManager: IWebSocketManager = WebSocketManager(context.applicationContext)
    private val cstPayManager: ICstPayManager = CstPayManager(context.applicationContext)
    private val takeFoodManager: ITakeFoodManager = TakeFoodManager(context.applicationContext)

    override fun init(reqInitBean: ReqInitBean?, initView: IInitView?) {
        commonManager.init(reqInitBean, initView)
    }

    override fun register(reqInitBean: ReqInitBean?, initView: IInitView?) {
        commonManager.register(reqInitBean, initView)
    }

    override fun getShopInfo(initView: IInitView?) {
        commonManager.getShopInfo(initView)
    }

    override fun startBeatService() {
        commonManager.startBeatService()
    }

    override fun stopBeatService() {
        commonManager.stopBeatService()
    }

    override fun getCardSecretKey(verifyInfoView: IVerifyInfoView?) {
        commonManager.getCardSecretKey(verifyInfoView)
    }

    override fun getCommSecretKey(verifyInfoView: IVerifyInfoView?) {
        commonManager.getCommSecretKey(verifyInfoView)
    }

    override fun getWallpaper(wallpaperView: IWallpaperView?) {
        commonManager.getWallpaper(wallpaperView)
    }

    override fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?) {
        commonManager.getFaceInfoByUserId(userId, faceInfoView)
    }

    override fun webSocketConnect(webSocketView: IWebSocketView?) {
        webSocketManager.webSocketConnect(this, webSocketView)
    }

    override fun beatConnect(listener: CstWebSocketListener) {
        webSocketManager.beatConnect(listener)
    }

    override fun getFaceInfo(listener: CstWebSocketListener) {
        webSocketManager.getFaceInfo(listener)
    }

    override fun getCardInfo(listener: CstWebSocketListener) {
        webSocketManager.getCardInfo(listener)
    }

    override fun getFingerprintInfo(listener: CstWebSocketListener) {
        webSocketManager.getFingerprintInfo(listener)
    }

    override fun getPasswordCardInfo(listener: CstWebSocketListener) {
        webSocketManager.getPasswordCardInfo(listener)
    }

    override fun setFaceInfo(data: String?) {
        webSocketManager.setFaceInfo(data)
    }

    override fun setCardInfo(data: String?) {
        webSocketManager.setCardInfo(data)
    }

    override fun setFingerprintInfo(data: String?) {
        webSocketManager.setFingerprintInfo(data)
    }

    override fun setPasswordInfo(data: String?) {
        webSocketManager.setPasswordInfo(data)
    }

    override fun setEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?) {
        webSocketManager.setEquipmentCreditInfo(data, resEquipmentCreditView)
    }

    override fun syncIssuedState(listener: CstWebSocketListener) {
        webSocketManager.syncIssuedState(listener)
    }

    override fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayManager.cstPay(payInfoBean, cstPayView)
    }

    override fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayManager.cstOnlinePay(payInfoBean, cstPayView)
    }

    override fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView) {
        cstPayManager.cstOfflinePay(payInfoBean, cstPayView)
    }

    override fun offlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        cstPayManager.offlineQueryPayRecord(startTime, endTime, payRecordView)
    }

    override fun onlineQueryPayRecord(startTime: String, endTime: String, payRecordView: IPayRecordView?) {
        cstPayManager.onlineQueryPayRecord(startTime, endTime, payRecordView)
    }

    override fun uploadPayRecord(uploadPayRecordView: IUploadPayRecordView?) {
        cstPayManager.uploadPayRecord(uploadPayRecordView)
    }

    override fun getRecFoodInfo(iRecFoodInfoView: IRecFoodInfoView?) {
        takeFoodManager.getRecFoodInfo(iRecFoodInfoView)
    }

    override fun getTakeFoodInfo(userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?) {
        takeFoodManager.getTakeFoodInfo(userCode, iTakeFoodInfoView)
    }

    override fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?) {
        takeFoodManager.confirmTakeFood(takeFoodInfo, iConfirmTakeFoodView)
    }
}