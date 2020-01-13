package com.cst.cstpaysdk.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserCreditEntity {

    /**
     * 记录ID
     */
    @Id(autoincrement = true)
    private Long id;

    /**
     * 用户ID
     */
    @Index(unique = true)
    private String userId;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 累计离线消费次数
     */
    private int offlinePayNum;

    /**
     * 累计离线消费额度
     */
    private String offlinePayAmount;

    @Generated(hash = 862499061)
    public UserCreditEntity(Long id, String userId, String userCode,
            String userName, int offlinePayNum, String offlinePayAmount) {
        this.id = id;
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.offlinePayNum = offlinePayNum;
        this.offlinePayAmount = offlinePayAmount;
    }

    @Generated(hash = 1041573239)
    public UserCreditEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOfflinePayNum() {
        return this.offlinePayNum;
    }

    public void setOfflinePayNum(int offlinePayNum) {
        this.offlinePayNum = offlinePayNum;
    }

    public String getOfflinePayAmount() {
        return this.offlinePayAmount;
    }

    public void setOfflinePayAmount(String offlinePayAmount) {
        this.offlinePayAmount = offlinePayAmount;
    }
}