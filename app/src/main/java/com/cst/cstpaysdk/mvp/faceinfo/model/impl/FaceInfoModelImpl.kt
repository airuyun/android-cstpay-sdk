package com.cst.cstpaysdk.mvp.faceinfo.model.impl

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.FaceInfoEntityDao
import com.cst.cstpaysdk.db.entity.FaceInfoEntity
import com.cst.cstpaysdk.mvp.faceinfo.model.IFaceInfoModel
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable

class FaceInfoModelImpl : IFaceInfoModel {

    override fun getFaceInfo(context: Context, userId: String?): Observable<FaceInfoEntity> {
        return Observable.create {
            LogUtil.customLog(context, "本地查询人脸请求参数，userId = $userId")
            val faceInfoEntityDao: FaceInfoEntityDao = DBManager.getInstance(context).faceInfoEntityDao
            val list = faceInfoEntityDao.queryBuilder().where(FaceInfoEntityDao.Properties.UserId.eq(userId)).build().list()
            if(list.isNotEmpty()){
                val faceInfo: FaceInfoEntity = list[0]
                LogUtil.customLog(context, "本地查询人脸响应参数，faceInfo = ${JSON.toJSONString(faceInfo)}")
                it.onNext(faceInfo)
                it.onComplete()
            } else {
                it.onComplete()
            }
        }
    }
}