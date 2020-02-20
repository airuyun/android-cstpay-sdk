package com.cst.cstpaysdk.bean

class ReqHttpSyncIssuedStateBean {

    /**
     * 设备的系统类型
     */
    var system: String? = null

    /**
     * 系统版本号
     */
    var systemVersion: String? = null

    /**
     * token
     */
    var token: String? = null

    /**
     * 客户id
     */
    var clientId: String? = null

    /**
     * 传递给后端的参数封装类
     */
    var data: DataBean? = DataBean()

    inner class DataBean {

        /**
         * 业务指令
         */
        var command: String? = null

        /**
         * 设备唯一标识
         */
        var mac: String? = null

        /**
         * 同步id
         */
        var recordId: String? = null

        /**
         * 同步成功标识，F-失败 S-成功
         */
        var state: String? = null

        /**
         * 失败原因
         */
        var synMsg: String? = null
    }
}