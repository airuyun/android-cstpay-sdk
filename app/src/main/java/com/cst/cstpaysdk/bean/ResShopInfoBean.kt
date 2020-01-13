package com.cst.cstpaysdk.bean

class ResShopInfoBean {

    //响应码
    var code: Int = 0

    var msg: String? = null

    var data: DataBean? = null

    inner class DataBean {

        //商铺ID
        var shopId: String? = null

        //商铺名称
        var shopName: String? = null
    }
}