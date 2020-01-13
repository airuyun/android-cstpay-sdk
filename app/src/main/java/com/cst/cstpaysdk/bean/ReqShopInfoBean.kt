package com.cst.cstpaysdk.bean

class ReqShopInfoBean {

    //参数封装类，是否必填-Y
    var data: DataBean = DataBean()

    inner class DataBean {

        //设备号（入网时由平台返回），是否必填-Y
        var equipmentNo: String? = null

        //MAC地址，是否必填-Y
        var mac: String? = null
    }
}