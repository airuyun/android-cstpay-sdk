package com.cst.cstpaysdk.bean

class ReqRegisterBean {

    /**
     * 设备类型，0201-人脸挂式消费机 0202-人脸双屏消费机 0203-人脸面板消费机 0204-普通二维码消费机
     * 0205-普通消费机，是否必填-Y
     */
    var equipmentType: String? = null

    /**
     * 设备MAC，是否必填-Y
     */
    var mac: String? = null

    /**
     * 设备ip，是否必填-Y
     */
    var ip: String? = null
}