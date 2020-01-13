package com.cst.cstpaysdk.mvp.faceinfo.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.db.entity.FaceInfoEntity
import com.cst.cstpaysdk.mvp.faceinfo.model.IFaceInfoModel
import com.cst.cstpaysdk.mvp.faceinfo.model.impl.FaceInfoModelImpl
import com.cst.cstpaysdk.mvp.faceinfo.view.IFaceInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：通过用户ID查询人脸信息
 * 影响范围：查询人脸信息
 * 备注：
 * @cst_end
 */
class FaceInfoPresenter(private val context: Context) : BasePresenter<IFaceInfoModel>() {

    private val faceInfoModel: IFaceInfoModel = FaceInfoModelImpl()

    fun getFaceInfo(userId: String?, faceInfoView: IFaceInfoView?) {
        faceInfoModel.getFaceInfo(context, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<FaceInfoEntity>(faceInfoView) {

                override fun onNext(@NonNull faceInfoEntity: FaceInfoEntity) {
                    faceInfoView?.getFaceInfoSuccess(faceInfoEntity)
                }

                override fun onError(@NonNull e: Throwable) {
                    faceInfoView?.getFaceInfoFailure(e)
                }

                override fun onComplete() {
                }
            })
    }
}