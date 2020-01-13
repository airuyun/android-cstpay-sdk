package com.cst.cstpaysdk.bean

class SyncIssuedStateBean {

    //设备ID
    var equipmentId: String? = null

    //设备唯一性标示
    var mac: String? = null

    var dataList: List<DataBean>? = null

    inner class DataBean {

        //记录ID
        var recordId: String? = null

        //状态，S-成功 F-失败
        var state: String? = null

        //同步描述
        var synMsg: String? = null
    }
}