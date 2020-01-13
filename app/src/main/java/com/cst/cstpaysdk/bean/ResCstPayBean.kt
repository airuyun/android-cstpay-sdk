package com.cst.cstpaysdk.bean

class ResCstPayBean {

    /**
     * 响应码
     */
    var code: Int = 0

    /**
     * 响应信息
     */
    var msg: String? = null

    /**
     * 响应数据
     */
    var data: DataBean? = null

    inner class DataBean {

        /**
         * 卡片消费时为卡号，人脸消费时为人脸标识号，二维码消费时为二维码字符串，指纹为指纹标识号
         */
        var cardNo: String? = null

        /**
         * 设备MAC地址
         */
        var mac: String? = null

        /**
         * 不知道是什么鬼
         */
        var tradeList: List<Any>? = null

        /**
         * 不知道是什么鬼
         */
        var amount: String? = null

        /**
         * 不知道是什么鬼
         */
        var money: String? = null

        /**
         * 不知道是什么鬼
         */
        var cardleft: String? = null

        /**
         * 流水号
         */
        var dealserial: String? = null

        /**
         * 交易笔数
         */
        var tradeCount: Int = 0

        /**
         * 实际消费金额
         */
        var actualAmount: String? = null

        /**
         * 余额
         */
        var balance: String? = null
    }
}