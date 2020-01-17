package com.cst.cstpaysdk.bean

class ReqInitBean {

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
     * 设备类型，0201-人脸挂式消费机 0202-人脸双屏消费机 0203-人脸面板消费机
     * 0204-普通二维码消费机 205-普通消费机
     */
    var equipmentType: String? = null

    /**
     * 是否覆盖，当 SharedPreferences 有变化时，是否更新 initinfo.txt 中的内容
     */
    var isUpdate: Boolean? = null
}