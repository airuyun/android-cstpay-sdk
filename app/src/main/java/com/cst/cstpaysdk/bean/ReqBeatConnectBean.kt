package com.cst.cstpaysdk.bean

class ReqBeatConnectBean {

    /**
     * 设备的系统类型
     */
    var system: String? = null

    /**
     * 系统版本号
     */
    var systemVersion: String? = null

    /**
     * 接口指令，设备注册相关，调用同一个url，通过command区分调用不同的接口
     */
    var command: String? = null

    /**
     * 传递给后端的参数封装类
     */
    var data: BeatConnectBean? = BeatConnectBean()

    inner class BeatConnectBean {

        /**
         * 设备ID，是否必输-Y
         */
        var equipmentId: String? = null


        /**
         * 设备号，是否必输-Y
         */
        var equipmentNo: String? = null

    }
}