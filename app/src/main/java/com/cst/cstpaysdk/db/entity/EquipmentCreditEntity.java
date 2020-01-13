package com.cst.cstpaysdk.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EquipmentCreditEntity {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 当前设备ID，如果ID不是属于当前设备，则禁止写入该表中
     */
    private String equipmentId;

    /**
     * 每个用户最大脱机(离线)支付次数
     */
    private int offlineNum;

    /**
     * 每个用户最大脱机(离线)支付额度
     */
    private String offlineQuota;

    /**
     * 设备信用规则生效时间
     */
    private String validTime;

    /**
     * 设备信用规则失效时间
     */
    private String invalidTime;

    /**
     * 当前设备信用规则是否有效，0-无效(已经不使用) 1-有效（正在使用）
     */
    private String valid;

    @Generated(hash = 401880356)
    public EquipmentCreditEntity(Long id, String equipmentId, int offlineNum,
            String offlineQuota, String validTime, String invalidTime,
            String valid) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.offlineNum = offlineNum;
        this.offlineQuota = offlineQuota;
        this.validTime = validTime;
        this.invalidTime = invalidTime;
        this.valid = valid;
    }

    @Generated(hash = 1889593334)
    public EquipmentCreditEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return this.equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getOfflineNum() {
        return this.offlineNum;
    }

    public void setOfflineNum(int offlineNum) {
        this.offlineNum = offlineNum;
    }

    public String getOfflineQuota() {
        return this.offlineQuota;
    }

    public void setOfflineQuota(String offlineQuota) {
        this.offlineQuota = offlineQuota;
    }

    public String getValid() {
        return this.valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getValidTime() {
        return this.validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }
}
