package com.cst.cstpaysdk.mvp.credit.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.ResCstPayBean
import com.cst.cstpaysdk.db.DBManager
import com.cst.cstpaysdk.db.EquipmentCreditEntityDao
import com.cst.cstpaysdk.db.entity.EquipmentCreditEntity
import com.cst.cstpaysdk.mvp.credit.model.IEquipmentCreditModel
import com.cst.cstpaysdk.util.DateUtils
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable
import org.greenrobot.greendao.query.WhereCondition

class EquipmentCreditImpl : IEquipmentCreditModel {

    override fun reqEquipmentCreditInfo(context: Context, data: String?): Observable<ResCstPayBean> {
        return Observable.create {
            LogUtil.customLog(context.applicationContext, "Android端WebSocket向后端请求设备信用参数 = $data")
        }
    }

    override fun resEquipmentCreditInfo(context: Context, data: String?): Observable<ResCstPayBean> {
        return Observable.create {
            LogUtil.customLog(context.applicationContext, "后端WebSocket向Android端推送来的设备信用数据 = $data")
            if (data != null && data != "null" && data != "" && data.startsWith("{") && data.endsWith("}")) {
                val equipmentCreditEntity: EquipmentCreditEntity? = JSON.parseObject(data, EquipmentCreditEntity::class.java)
                val equipmentCreditEntityDao: EquipmentCreditEntityDao = DBManager.getInstance(context).equipmentCreditEntityDao
                equipmentCreditEntity?.let {
                    val condition1: WhereCondition = EquipmentCreditEntityDao.Properties.Valid.eq("0")
                    val list1: MutableList<EquipmentCreditEntity> = equipmentCreditEntityDao.queryBuilder().where(condition1).build().list()
                    if(list1.size >= 10000) {
                        //已经无效的设备信用规则记录大于1万时，清空所有无效数据，不能让数据无限累加
                        for(credit: EquipmentCreditEntity in list1) {
                            equipmentCreditEntityDao.delete(credit)
                        }
                    }
                    val condition2: WhereCondition = EquipmentCreditEntityDao.Properties.Valid.eq("1")
                    val list2: MutableList<EquipmentCreditEntity> = equipmentCreditEntityDao.queryBuilder().where(condition2).build().list()
                    //将当前生效的设备信用规则设置为无效
                    for(credit: EquipmentCreditEntity in list2) {
                        credit.valid = "0"
                        credit.invalidTime = DateUtils.getCurrentTime14()
                        equipmentCreditEntityDao.update(credit)
                    }
                    //插入新的设备信用规则
                    equipmentCreditEntity.valid = "1"
                    equipmentCreditEntity.validTime = DateUtils.getCurrentTime14()
                    equipmentCreditEntityDao.insert(equipmentCreditEntity)
                }
            }
        }
    }
}