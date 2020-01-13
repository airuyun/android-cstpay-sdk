package com.cst.cstpaysdk.bean

class ReqVerifyInfoBean {

    //设备号（入网时由平台返回），是否必填-Y
    var equipmentId: String? = null

    //设备MAC，是否必输-Y
    var mac: String? = null

    //密钥类型，01-卡秘钥 02-通信秘钥，是否必输-Y
    var type: String? = null
}