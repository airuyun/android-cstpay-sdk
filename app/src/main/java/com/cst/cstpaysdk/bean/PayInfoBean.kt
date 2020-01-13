package com.cst.cstpaysdk.bean

class PayInfoBean {

    /**
     * 用户ID
     */
    var userId: String? = null

    /**
     * 用户编号
     */
    var userCode: String? = null

    /**
     * 用户姓名
     */
    var userName: String? = null

    /**
     * 店铺ID
     */
    var shopId: String? = null

    /**
     * 店铺名称
     */
    var shopName: String? = null

    /**
     * 设备ID
     */
    var equipmentId: String? = null

    /**
     * 设备号（入网时由平台返回），是否必填-Y
     */
    var equipmentNo: String? = null
    /**
     * 交易金额，单位为元
     */
    var amount: String? = null

    /**
     * 消费类型，CARD-卡片 QRCODE-二维码 FACE-人脸，脱机没有二维码消费，FACE_C-人脸消费
     */
    var tradeType: String? = null

    /**
     * 卡片消费时为卡号，人脸消费时为下发标识，二维码消费时为二维码内容，指纹为指纹标识，是否必填-N
     */
    var cardNo: String? = null

    /**
     * 抓拍到的人脸图片Base64字符串，人脸消费时必填，其他类型消费省略
     */
    var photoMsg: String? = null

    /**
     * 抓拍到的人脸图片路径，人脸消费时必填，其他类型消费省略
     */
    var photoPath: String? = null

    /**
     * 卡物理号，刷卡消费必填，其他类型消费省略
     */
    var physicsNo: String? = null
}