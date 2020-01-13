package com.cst.cstpaysdk.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TradeRecordEntity {

    @Id(autoincrement = true)
    private Long id;

    //用户ID
    private String userId;

    //用户姓名
    private String userName;

    //用户编号
    private String userCode;

    //店铺ID
    private String shopId;

    //店铺名称
    private String shopName;

    //14位交易时间
    private String tradeTime;

    //流水号，12位Mac+14位交易时间+随机2位
    @Index(unique = true)
    private String dealserial;

    //交易金额，单位为元
    private String amount;

    //账户余额
    private String balance;

    //消费类型，CARD-卡片 QRCODE-二维码 FACE-人脸，脱机没有二维码消费
    private String tradeType;

    //消费方式，online-在线消费 offline-离线消费
    private String tradeMethod;

    //卡片消费时为卡号，人脸消费时为下发标识，二维码消费时为二维码内容，指纹为指纹标识，是否必填-N
    private String cardNo;

    //Base64抓拍到的人脸图片本地路径，人脸消费时必填，其他类型消费省略
    private String photoMsg;

    //卡物理号，刷卡消费必填，其他类型消费省略
    private String physicsNo;

    //向平台同步交易状态，0-未同步 1-已同步
    private String syncState;

    //当前设备MAC地址
    private String mac;

    //原始mac地址
    private String oldMac;

    //累计上传次数
    private Integer syncCount;

    

    @Generated(hash = 1993400370)
    public TradeRecordEntity() {
    }

    @Generated(hash = 1870234021)
    public TradeRecordEntity(Long id, String userId, String userName,
            String userCode, String shopId, String shopName, String tradeTime,
            String dealserial, String amount, String balance, String tradeType,
            String tradeMethod, String cardNo, String photoMsg, String physicsNo,
            String syncState, String mac, String oldMac, Integer syncCount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userCode = userCode;
        this.shopId = shopId;
        this.shopName = shopName;
        this.tradeTime = tradeTime;
        this.dealserial = dealserial;
        this.amount = amount;
        this.balance = balance;
        this.tradeType = tradeType;
        this.tradeMethod = tradeMethod;
        this.cardNo = cardNo;
        this.photoMsg = photoMsg;
        this.physicsNo = physicsNo;
        this.syncState = syncState;
        this.mac = mac;
        this.oldMac = oldMac;
        this.syncCount = syncCount;
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

    public String getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getDealserial() {
        return this.dealserial;
    }

    public void setDealserial(String dealserial) {
        this.dealserial = dealserial;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhotoMsg() {
        return this.photoMsg;
    }

    public void setPhotoMsg(String photoMsg) {
        this.photoMsg = photoMsg;
    }

    public String getPhysicsNo() {
        return this.physicsNo;
    }

    public void setPhysicsNo(String physicsNo) {
        this.physicsNo = physicsNo;
    }

    public String getSyncState() {
        return this.syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getShopId() {
        return this.shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOldMac() {
        return this.oldMac;
    }

    public void setOldMac(String oldMac) {
        this.oldMac = oldMac;
    }

    public String getTradeMethod() {
        return this.tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public Integer getSyncCount() {
        return this.syncCount;
    }

    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }
}
