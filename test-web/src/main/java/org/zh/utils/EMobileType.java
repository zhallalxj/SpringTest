package org.zh.utils;

/**
 * Created by sundy on 2015/5/28.
 * 短信验证码类型枚举
 */
public enum EMobileType {

    /**
     * 注册
     */
    REGISTER_CODE("REGISTER_CODE", 1),
    /**
     * 取消注册
     */
    UNREGISTER("UNREGISTER", 2),
    /**
     * 用户登录
     */
    LOGIN_CODE("LOGIN_CODE", 3),
    /**
     * 找回密码
     */
    PASSWORD_CODE("PASSWORD_CODE", 4),
    /**
     * 绑定支付宝
     */
    ALIPAY_CODE("ALIPAY_CODE", 5),

    /**
     * 解除绑定支付宝
     */
    ALIPAY_UNBIND("ALIPAY_UNBIND", 6),

    /**
     * 绑定微信
     */
    WECHAT_CODE("WECHAT_CODE", 7),
    /**
     * 解除绑定微信
     */
    WECHAT_UNBIND("WECHAT_CODE", 8),

    /**
     * 更新账户
     */
    UPDATE_ACCOUNT("UPDATE_ACCOUNT", 9),

    /**
     * SDK注册
     */
    SDK_REGISTER("SDK_REGISTER", 10),

    /**
     * 设置支付密码
     */
    SET_PAY_PWD("SET_PAY_PWD", 11),

    /**
     * 找回支付密码
     */
    FORGET_PAY_PWD("FORGET_PAY_PWD", 12),

    /**
     * 修改支付密码
     */
    UPDATE_PAT_PWD("UPDATE_PAT_PWD", 13),

    /**
     * 绑定手机号
     */
    BIND_PHONE("BIND_PHONE", 14);

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    EMobileType(String name, int type) {
        this.type = type;
        this.name = name;
    }


    public static EMobileType getEMobileCode(int type) {
        for (EMobileType mobileCode : EMobileType.values()) {
            if (mobileCode.getType() == type) return mobileCode;
        }
        return null;
    }


}
