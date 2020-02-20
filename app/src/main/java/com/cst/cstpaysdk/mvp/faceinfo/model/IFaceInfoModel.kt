package com.cst.cstpaysdk.mvp.faceinfo.model

import android.content.Context
import com.cst.cstpaysdk.db.entity.FaceInfoEntity
import io.reactivex.Observable

interface IFaceInfoModel {

    /**
     * 通过用户ID查询人脸信息
     *
     * @param context 上下文
     */
    fun getFaceInfoByUserId(context: Context, userId: String?): Observable<FaceInfoEntity>
}