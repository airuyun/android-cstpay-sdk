package com.cst.cstpaysdk.bean

class ResUploadTradeRecordBean {

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
    var data: DataBean? = null

    inner class DataBean {

        var cardNo: String? = null

        var mac: String? = null

        var amount: String? = null

        var money: String? = null

        var cardleft: String? = null

        var dealserial: String? = null

        var tradeCount: String? = null

        var actualAmount: String? = null

        var balance: String? = null

        var tradeList: List<Any>? = null

        var failList: List<FailData>? = null

        inner class FailData {

            var dealserial: String? = null
        }
    }
}