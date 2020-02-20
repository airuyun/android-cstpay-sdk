package com.cst.cstpaysdk.bean

class ResBeatConnectBean {

    /**
     * 响应码
     */
    var code: Int = 0

    /**
     * 响应信息
     */
    var msg: String? = null

    /**
     * 响应数据
     */
    var data: DataBean? = null

    inner class DataBean {

        /**
         * 业务指令
         */
        var command: String? = null

        /**
         * 设备编号
         */
        var equipmentNo: String? = null
    }
}