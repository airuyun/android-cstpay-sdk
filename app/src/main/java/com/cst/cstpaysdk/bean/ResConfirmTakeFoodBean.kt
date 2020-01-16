package com.cst.cstpaysdk.bean

class ResConfirmTakeFoodBean {

    /**
     * 返回状态码
     */
    var code: String? = null

    /**
     * 返回信息
     */
    var msg: String? = null

    /**
     * 就餐信息列表
     */
    var menuInfoList: List<FoodInfo>? = null

    inner class FoodInfo {

        /**
         * 菜品订单编号
         */
        var fbOrderId: String? = null

        /**
         * 菜品名
         */
        var fbMenuName: String? = null

        /**
         * 菜品数量
         */
        var fbMenuNum: Int? = null

    }
}