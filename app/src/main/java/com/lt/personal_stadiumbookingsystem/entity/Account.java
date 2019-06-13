package com.lt.personal_stadiumbookingsystem.entity;

import java.util.Objects;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:45
 * @版本: 1.0
 * @描述: //账户实体类
 * 1.0: Initial Commit
 */

public class Account {
    private Integer account_id;
    private String account_name;//账户名
    private String account_passwd;//账户密码
    private String account_nickname;//账户昵称
    private String account_realname;//账户实名
    private Float account_coin;//账户余额
    private String account_sex;//账户性别
    private String account_phone;//账户电话
    private String account_email;//账户邮箱
    private String account_qq;//账户QQ
    private String account_role;//账户角色(user:普通用户，admin:管理员)
    private String account_verifycode;//账户验证码(四位数)
    private Integer account_verifystate;//账户验证状态(0:未验证，1:已验证)
    private Integer account_loginstate;//账户登录状态(0:未登录，1:已登录)
    private String account_createtime;//账户注册时间

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", account_name='" + account_name + '\'' +
                ", account_passwd='" + account_passwd + '\'' +
                ", account_nickname='" + account_nickname + '\'' +
                ", account_realname='" + account_realname + '\'' +
                ", account_coin=" + account_coin +
                ", account_sex='" + account_sex + '\'' +
                ", account_phone='" + account_phone + '\'' +
                ", account_email='" + account_email + '\'' +
                ", account_qq='" + account_qq + '\'' +
                ", account_role='" + account_role + '\'' +
                ", account_verifycode='" + account_verifycode + '\'' +
                ", account_verifystate=" + account_verifystate +
                ", account_loginstate=" + account_loginstate +
                ", account_createtime='" + account_createtime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(account_id, account.account_id) &&
                Objects.equals(account_name, account.account_name) &&
                Objects.equals(account_passwd, account.account_passwd) &&
                Objects.equals(account_nickname, account.account_nickname) &&
                Objects.equals(account_realname, account.account_realname) &&
                Objects.equals(account_coin, account.account_coin) &&
                Objects.equals(account_sex, account.account_sex) &&
                Objects.equals(account_phone, account.account_phone) &&
                Objects.equals(account_email, account.account_email) &&
                Objects.equals(account_qq, account.account_qq) &&
                Objects.equals(account_role, account.account_role) &&
                Objects.equals(account_verifycode, account.account_verifycode) &&
                Objects.equals(account_verifystate, account.account_verifystate) &&
                Objects.equals(account_loginstate, account.account_loginstate) &&
                Objects.equals(account_createtime, account.account_createtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, account_name, account_passwd, account_nickname, account_realname, account_coin, account_sex, account_phone, account_email, account_qq, account_role, account_verifycode, account_verifystate, account_loginstate, account_createtime);
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_passwd() {
        return account_passwd;
    }

    public void setAccount_passwd(String account_passwd) {
        this.account_passwd = account_passwd;
    }

    public String getAccount_nickname() {
        return account_nickname;
    }

    public void setAccount_nickname(String account_nickname) {
        this.account_nickname = account_nickname;
    }

    public String getAccount_realname() {
        return account_realname;
    }

    public void setAccount_realname(String account_realname) {
        this.account_realname = account_realname;
    }

    public Float getAccount_coin() {
        return account_coin;
    }

    public void setAccount_coin(Float account_coin) {
        this.account_coin = account_coin;
    }

    public String getAccount_sex() {
        return account_sex;
    }

    public void setAccount_sex(String account_sex) {
        this.account_sex = account_sex;
    }

    public String getAccount_phone() {
        return account_phone;
    }

    public void setAccount_phone(String account_phone) {
        this.account_phone = account_phone;
    }

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public String getAccount_qq() {
        return account_qq;
    }

    public void setAccount_qq(String account_qq) {
        this.account_qq = account_qq;
    }

    public String getAccount_role() {
        return account_role;
    }

    public void setAccount_role(String account_role) {
        this.account_role = account_role;
    }

    public String getAccount_verifycode() {
        return account_verifycode;
    }

    public void setAccount_verifycode(String account_verifycode) {
        this.account_verifycode = account_verifycode;
    }

    public Integer getAccount_verifystate() {
        return account_verifystate;
    }

    public void setAccount_verifystate(Integer account_verifystate) {
        this.account_verifystate = account_verifystate;
    }

    public Integer getAccount_loginstate() {
        return account_loginstate;
    }

    public void setAccount_loginstate(Integer account_loginstate) {
        this.account_loginstate = account_loginstate;
    }

    public String getAccount_createtime() {
        return account_createtime;
    }

    public void setAccount_createtime(String account_createtime) {
        this.account_createtime = account_createtime;
    }
}
