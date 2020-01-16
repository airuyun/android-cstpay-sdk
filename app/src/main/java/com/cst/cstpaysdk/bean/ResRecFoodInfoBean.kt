package com.cst.cstpaysdk.bean

class ResRecFoodInfoBean {

    /**
     * 商铺ID
     */
    var shopId: String? = null

    /**
     * 商铺名称
     */
    var shopName: String? = null

    /**
     * 图片url集合
     */
    var photoUrlList: List<PhotoUrl>? = null

    inner class PhotoUrl {

        /**
         * 菜品名称
         */
        var menuName: String? = null

        /**
         * 图片url
         */
        var photoUrl: String? = null
    }
}