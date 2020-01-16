package com.cst.cstpaysdk.bean

class ReqTakeFoodInfoBean {

    //参数封装类，是否必填-Y
    var data: DataBean = DataBean()

    inner class DataBean {

        /**
         * mac号，获取店铺信息需要传递
         */
        var mac: String? = null

        /**
         * 店铺ID，根据店铺获取菜品图片
         */
        var shopId: String? = null

        /**
         * 用户ID，根据用户编号获取就餐菜品信息接口
         */
        var userCode: String? = null

        /**
         * 菜品订单编号列表，确认就餐接口需要
         */
        var fbOrderIds: List<String>? = null
    }
}