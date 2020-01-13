package com.cst.cstpaysdk.util

import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.InitInfoBean

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 常量类，用于存放一些公共的固定参数
 * 影响范围：部分公共的常量
 * 备注：
 * @cst_end
 */
object ConstantUtils {

    //网络数据请求成功返回响应码
   // const val RESPONSE_SUCCESS_CODE = 200

    //更新时间标志
   // const val UPDATE_TIME_TAG = 0

    //const val SHOP_NAME = "shop_name"

    private var equipmentId: String? = null
    private var equipmentNo: String? = null

    fun getInitInfo(): InitInfoBean? {
        val filePath = "${FileUtil.getPATH()}/CstPay/data/initinfo.txt"
        val initInfo: String? = FileUtil.readFile(filePath)
        return if(initInfo != null) {
            JSON.parseObject(initInfo, InitInfoBean::class.java)
        } else {
            null
        }
    }

    fun getEquipmentId(): String? {
        return equipmentId
    }

    fun setEquipmentId(equipmentId: String?) {
        this.equipmentId = equipmentId
    }

    fun getEquipmentNo(): String? {
        return equipmentNo
    }

    fun setEquipmentNo(equipmentNo: String?) {
        this.equipmentNo = equipmentNo
    }

    fun getRegisterUrl(): String {
        return "${rootHttpURL()}/api/equipment/apiout/netApplyFor"
    }

    fun getSecretKeyUrl(): String {
        return "${rootHttpURL()}/api/equipment/apiout/getsecretKey"
    }

    fun getShopInfoUrl(): String {
        return "${rootHttpURL()}/api/repast/fbTakeFood/getShopInfoByMac"
    }

    fun getWallpaperUrl(): String {
        return "${rootHttpURL()}/"
    }

    fun getTradeRecordUrl(): String {
        return "${rootHttpURL()}/"
    }

    /**
     * 在线支付url
     */
    fun getCstPayUrl(): String {
        return "${rootHttpURL()}/api/consume/onlineConsume"
    }

    fun dealRecordUrl(): String {
        return "${rootHttpURL()}/api/consume/supermarket/transLog"
    }

    /**
     * 离线支付记录上报url
     */
    fun getUploadTradeRecordUrl(): String {
        return "${rootHttpURL()}/api/consume/offlineTradeLogUp"
    }

    fun getWebSocketUrl(): String {
        return "${rootWebSocketURL()}/websocket"
    }

    fun getSyncIssuedStateUrl(): String {
        return "${rootHttpURL()}/api/equipment/apiout/synIssuedState"
    }

    private fun rootHttpURL(): String {
        return "http://${getIP()}:${getPort()}"
    }

    private fun rootWebSocketURL(): String {
        return "ws://${getWebSocketIP()}:${getWebSocketPort()}"
    }

    private fun getIP(): String? {
        return getInitInfo()?.serverIp ?: "0.0.0.0"
    }

    private fun getPort(): String {
        return getInitInfo()?.serverPort ?: "8080"
    }

    private fun getWebSocketIP(): String {
        return getInitInfo()?.webSocketIp ?: "0.0.0.0"
    }

    private fun getWebSocketPort(): String {
        return getInitInfo()?.webSocketPort ?: "8080"
    }
}