package com.cst.cstpaysdk.util

import android.content.Context
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

    fun getInitInfo(context: Context): InitInfoBean? {
        val filePath = "${FileUtil.getPATH()}/${context.packageName}/data/initinfo.txt"
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

    fun getRegisterUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/equipment/apiout/netApplyFor"
    }

    fun getSecretKeyUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/equipment/apiout/getsecretKey"
    }

    fun getShopInfoUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/repast/fbTakeFood/getShopInfoByMac"
    }

    fun getWallpaperUrl(context: Context): String {
        return "${rootHttpURL(context)}/"
    }

    fun getTradeRecordUrl(context: Context): String {
        return "${rootHttpURL(context)}/"
    }

    /**
     * 在线支付url
     */
    fun getCstPayUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/consume/onlineConsume"
    }

    fun dealRecordUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/consume/supermarket/transLog"
    }

    /**
     * 离线支付记录上报url
     */
    fun getUploadTradeRecordUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/consume/offlineTradeLogUp"
    }

    fun getWebSocketUrl(context: Context): String {
        return "${rootWebSocketURL(context)}/websocket"
    }

    fun getSyncIssuedStateUrl(context: Context): String {
        return "${rootHttpURL(context)}/api/equipment/apiout/synIssuedState"
    }

    private fun rootHttpURL(context: Context): String {
        return "http://${getIP(context)}:${getPort(context)}"
    }

    private fun rootWebSocketURL(context: Context): String {
        return "ws://${getWebSocketIP(context)}:${getWebSocketPort(context)}"
    }

    private fun getIP(context: Context): String? {
        return getInitInfo(context)?.serverIp ?: "0.0.0.0"
    }

    private fun getPort(context: Context): String {
        return getInitInfo(context)?.serverPort ?: "8080"
    }

    private fun getWebSocketIP(context: Context): String {
        return getInitInfo(context)?.webSocketIp ?: "0.0.0.0"
    }

    private fun getWebSocketPort(context: Context): String {
        return getInitInfo(context)?.webSocketPort ?: "8080"
    }
}