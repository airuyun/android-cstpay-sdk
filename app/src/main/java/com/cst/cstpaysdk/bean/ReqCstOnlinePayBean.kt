package com.cst.cstpaysdk.bean

class ReqCstOnlinePayBean {

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

        /**
         * 卡片消费时为卡号，人脸消费时为人脸标识号，二维码消费时为二维码字符串，指纹为指纹标识号
         */
        var cardNo: String? = null

        /**
         * 支付密码
         */
        var payPwd: String? = null

        /**
         * 交易日期
         */
        var tradeTime: String? = null

        /**
         * 流水号，Mac + 14位时间 + 随机2位
         */
        var dealserial: String? = null

        /**
         * 交易类型，CARD-卡片，QRCODE-二维码，FACE-人脸
         */
        var tradeType: String? = null

        /**
         * 设备唯一号
         */
        var mac: String? = null

        /**
         * 人脸必传，Base64的图片字符
         */
        var photoMsg: String? = null

        /**
         * 原始消费金额，单位为元
         */
        var amount: String? = null

        /**
         * 设备id
         */
        var equipmentId: String? = null
    }
}