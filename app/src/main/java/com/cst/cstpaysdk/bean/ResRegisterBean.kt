package com.cst.cstpaysdk.bean

class ResRegisterBean {

    //响应码
    var code: Int = 0

    var msg: String? = null

    var data: DataBean? = null

    inner class DataBean {
        //设备号
        var equipmentNo: String? = null

        //设备ID
        var equipmentId: String? = null
    }
}