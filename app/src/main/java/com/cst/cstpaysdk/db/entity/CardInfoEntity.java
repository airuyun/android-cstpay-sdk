package com.cst.cstpaysdk.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CardInfoEntity {

    @Id(autoincrement = true)
    private Long id;

    //记录ID
    @Index(unique = true)
    private String recordId;

    //用户ID
    private String userId;

    //用户编号
    private String userCode;

    //用户姓名
    private String userName;

    //用户类型
    private String userType;

    //用户部门
    private String userDept;

    //卡序号
    private String cardNo;

    //卡物理号
    private String cardPhysics;

    //操作类型 A-新增 E-修改 D-删除
    private String operateType;

    //创建时间
    private String recordTime;

    @Generated(hash = 515035730)
    public CardInfoEntity(Long id, String recordId, String userId, String userCode,
            String userName, String userType, String userDept, String cardNo,
            String cardPhysics, String operateType, String recordTime) {
        this.id = id;
        this.recordId = recordId;
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userType = userType;
        this.userDept = userDept;
        this.cardNo = cardNo;
        this.cardPhysics = cardPhysics;
        this.operateType = operateType;
        this.recordTime = recordTime;
    }

    @Generated(hash = 677034413)
    public CardInfoEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserDept() {
        return this.userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPhysics() {
        return this.cardPhysics;
    }

    public void setCardPhysics(String cardPhysics) {
        this.cardPhysics = cardPhysics;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}