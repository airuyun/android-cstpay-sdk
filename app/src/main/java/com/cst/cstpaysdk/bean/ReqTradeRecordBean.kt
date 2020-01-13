package com.cst.cstpaysdk.bean

import com.cst.cstpaysdk.db.entity.TradeRecordEntity

class ReqTradeRecordBean {

    /**
     * 设备的系统类型
     */
    var system: String? = null

    /**
     * 系统版本号
     */
    var systemVersion: String? = null

    /**
     * 操作用户ID
     */
    var userId: String? = null

    /**
     * 操作机构ID
     */
    var clientId: String? = null

    /**
     * data + 通信秘钥加密
     */
    var checkcode: String? = null

    /**
     * 传递给后端的参数封装类
     */
    var data: BeatConnectBean? = BeatConnectBean()

    inner class BeatConnectBean {

        //设备MAC，是否必填-Y
        var mac: String? = null

        //记录条数，是否必填-Y
        var tradeCount: String? = null

        //参数封装类，是否必填-Y
        var tradeList: MutableList<TradeRecordEntity>? = null
    }
}