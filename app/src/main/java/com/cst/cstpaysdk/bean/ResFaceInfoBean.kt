package com.cst.cstpaysdk.bean

import com.cst.cstpaysdk.db.entity.FaceInfoEntity

class ResFaceInfoBean {

    /**
     * 设备ID
     */
    var equipmentId: String? = null

    /**
     * 黑白类型，B-黑名单 W-白名单
     */
    var type: String? = null

    /**
     * 人脸信息列表
     */
    var dataList: List<FaceInfoEntity>? = null
}