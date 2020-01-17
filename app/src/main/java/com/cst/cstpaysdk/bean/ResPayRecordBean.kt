package com.cst.cstpaysdk.bean

import com.cst.cstpaysdk.db.entity.TradeRecordEntity

class ResPayRecordBean {

    /**
     * 返回码
     */
    var code: Int? = 0

    /**
     * 返回信息说明
     */
    var msg: String? = null

    /**
     * 数据封装类
     */
    var data: DataBean? = DataBean()

    inner class DataBean {

        /**
         * 交易记录数量
         */
        var totalNum: Int? = 0

        /**
         * 交易总金额
         */
        var totalPrice: String? = null

        /**
         * 交易记录封装类
         */
        var tradeLogs: List<TradeRecordEntity>? = null
    }
}