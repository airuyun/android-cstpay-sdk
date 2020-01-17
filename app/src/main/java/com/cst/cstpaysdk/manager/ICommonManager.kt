package com.cst.cstpaysdk.manager

import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import com.cst.cstpaysdk.mvp.init.view.IInitView
import com.cst.cstpaysdk.mvp.verifyInfo.view.IVerifyInfoView
import com.cst.cstpaysdk.mvp.wallpaper.view.IWallpaperView

internal interface ICommonManager {

    /**
     * 数据初始化，需要在调用本SDK的APP的application的onCreate中进行数据的初始化
     */
    fun init(reqInitBean: ReqInitBean?, initView: IInitView?)

    /**
     * 入网申请，向康索特三代平台注册
     */
    fun register(reqInitBean: ReqInitBean?, initView: IInitView?)

    /**
     * 获取店铺信息
     */
    fun getShopInfo(initView: IInitView?)

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
     * 获取通讯密钥，支付相关数据加签（加密），加签应该放到本SDK中封装好
     */
    fun getCommSecretKey(verifyInfoView: IVerifyInfoView?)

    /**
     * 获取屏保图片
     */
    fun getWallpaper(wallpaperView: IWallpaperView?)

    /**
     * 通过用户ID查询本地数据库获取人脸信息
     */
    fun getFaceInfoByUserId(userId: String?, faceInfoView: IFaceInfoView?)
}