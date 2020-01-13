package com.cst.cstpaysdk.bean

class InitInfoBean {

    /**
     * 服务端HTTP请求IP
     */
    var serverIp: String? = null

    /**
     * 服务端HTTP请求端口
     */
    var serverPort: String? = null

    /**
     * 服务端WEB SOCKET IP
     */
    var webSocketIp: String? = null

    /**
     * 服务端WEB SOCKET 端口
     */
    var webSocketPort: String? = null

    /**
     * 是否覆盖，当 SharedPreferences 有变化时，是否更新 initinfo.txt 中的内容
     */
    var isUpdate: Boolean? = null
}