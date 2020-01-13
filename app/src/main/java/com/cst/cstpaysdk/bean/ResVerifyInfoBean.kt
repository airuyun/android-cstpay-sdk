package com.cst.cstpaysdk.bean

class ResVerifyInfoBean {

    //响应码
    var code: Int = 0

    var msg: String? = null

    var data: DataBean? = null

    inner class DataBean {

        //设备号
        var equipmentNo: String? = null

        //设备ID
        var equipmentId: String? = null

        //秘钥
        var secretKey: String? = null
    }
}