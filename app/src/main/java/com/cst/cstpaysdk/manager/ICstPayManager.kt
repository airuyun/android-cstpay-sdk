package com.cst.cstpaysdk.manager

import com.cst.cstpaysdk.bean.InitInfoBean
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.credit.view.IResEquipmentCreditView
import com.cst.cstpaysdk.mvp.cstpay.view.ICstPayView
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.shopinfo.view.IShopInfoView
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView
import com.cst.cstpaysdk.mvp.traderecord.view.ITradeRecordView
import com.cst.cstpaysdk.mvp.traderecord.view.IUploadTradeRecordView
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView
import com.cst.cstpaysdk.mvp.websocket.view.IWebSocketView
import com.cst.cstpaysdk.net.CstWebSocketListener

interface ICstPayManager {

    /**
     * 数据初始化，需要在调用本SDK的APP的application的onCreate中进行数据的初始化
     */
    fun init(initInfoBean: InitInfoBean?, initView: IInitView?)

    /**
     * 入网申请，向康索特三代平台注册
     */
    fun register(initInfoBean: InitInfoBean?, initView: IInitView?)

    /**
     * 启动心跳服务，需要在应用首页onCreate中启动
     */
    fun startBeatService()

    /**
     * 停止动心跳服务，需要在应用首页onDestroy中停止
     */
    fun stopBeatService()

    /**
     * 获取卡密钥
     */
    fun getCardSecretKey(verifyInfoView: IVerifyInfoView?)

    /**
     * 获取通讯密钥，支付相关数据加密使用
     */
    fun getCommSecretKey(verifyInfoView: IVerifyInfoView?)

    /**
     * 获取店铺信息
     */
    fun getShopInfo(shopInfoView: IShopInfoView?)

    /**
     * 获取屏保图片
     */
    fun getWallpaper(wallpaperView: IWallpaperView?)

    /**
     * 离线线查询消费记录（查询设备本地数据库的消费记录）
     */
    fun offlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?)

    /**
     * 在线查询消费记录（查询服务端交易记录）
     */
    fun onlineQueryTradeRecord(startTime: String, endTime: String, tradeRecordView: ITradeRecordView?)

    /**
     * 上传脱机（离线）消费记录（将还未成功上传到平台的交易记录上传）
     */
    fun uploadTradeRecord(uploadTradeRecordView: IUploadTradeRecordView?)

    /**
     * 康索特三代平台消费，先调用在线消费，在线消费失败则自动转为离线消费
     */
    fun cstPay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 康索特三代平台在线消费
     */
    fun cstOnlinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 康索特三代平台脱机（离线）消费
     */
    fun cstOfflinePay(payInfoBean: PayInfoBean, cstPayView: ICstPayView)

    /**
     * 主动向后端WebSocket发起连接请求
     */
    fun webSocketConnect(webSocketView: IWebSocketView?)

    /**
     * 主动向后端WebSocket发起心跳请求
     */
    fun beatConnect(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取人脸信息请求
     */
    fun getFaceInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取卡信息请求
     */
    fun getCardInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取指纹（门禁指纹等）信息请求
     */
    fun getFingerprintInfo(listener: CstWebSocketListener)

    /**
     * 主动向后端WebSocket发起获取密码（门禁密码等）信息请求
     */
    fun getPasswordCardInfo(listener: CstWebSocketListener)

    /**
     * 后端WebSocket向Android端推送人脸信息时，调用此接口处理数据
     */
    fun setFaceInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送卡信息时，调用此接口处理数据
     */
    fun setCardInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送指纹信息时，调用此接口处理数据
     */
    fun setFingerprintInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送密码信息时，调用此接口处理数据
     */
    fun setPasswordInfo(data: String?)

    /**
     * 后端WebSocket向Android端推送设备信用信息时，调用此接口处理数据
     */
    fun setEquipmentCreditInfo(data: String?, resEquipmentCreditView: IResEquipmentCreditView?)

    /**
     * 同步WebSocket下发状态（当后端WebSocket向Android端推送数据，Android端需要上报是否处理成功状态）
     */
    fun syncIssuedState(listener: CstWebSocketListener)

    /**
     * 通过用户ID查询本地数据库获取人脸信息
     */
    fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?)

    /**
     * 获取店铺的精品菜（推荐菜品）信息
     */
    fun getRecFoodInfo(shopId: String?, iRecFoodInfoView: IRecFoodInfoView?)

    /**
     * 获取用户在该店铺的取餐信息
     */
    fun getTakeFoodInfo(shopId: String?, userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?)

    /**
     * 确认取餐
     */
    fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?)
}