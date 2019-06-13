package com.lt.personal_stadiumbookingsystem.entity;

import java.util.Objects;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 20:06
 * @版本: 1.0
 * @描述: //订单实体类
 * 1.0: Initial Commit
 */


public class Order {
    private Integer order_id;
    private String order_number;//订单号
    private String order_date;//订单日期
    private String order_timestart;//订单开始时间
    private String order_timeend;//订单结束时间
    private Integer order_state;//订单状态(0:待付款，1:已付款，2:已执行(或过期)，3:已取消)
    private String order_createtime;//订单创建时间
    private Integer account_id;//订单的用户ID
    private String account_name;//用户ID对应的账号。非该Table字段
    private String account_nickname;//用户ID对应的昵称。非该Table字段
    private Integer site_id;//订单的场地ID
    private String site_number;//场地ID对应的场地号。非该Table字段
    private Integer gym_id;//场地ID对应的场馆ID。非该Table字段
    private String gym_name;//场馆ID对应的场馆名。非该Table字段
    private Float gym_price;//场馆ID对应的场馆价格。非该Table字段

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_number='" + order_number + '\'' +
                ", order_date='" + order_date + '\'' +
                ", order_timestart='" + order_timestart + '\'' +
                ", order_timeend='" + order_timeend + '\'' +
                ", order_state=" + order_state +
                ", order_createtime='" + order_createtime + '\'' +
                ", account_id=" + account_id +
                ", account_name='" + account_name + '\'' +
                ", account_nickname='" + account_nickname + '\'' +
                ", site_id=" + site_id +
                ", site_number='" + site_number + '\'' +
                ", gym_id=" + gym_id +
                ", gym_name='" + gym_name + '\'' +
                ", gym_price=" + gym_price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(order_id, order.order_id) &&
                Objects.equals(order_number, order.order_number) &&
                Objects.equals(order_date, order.order_date) &&
                Objects.equals(order_timestart, order.order_timestart) &&
                Objects.equals(order_timeend, order.order_timeend) &&
                Objects.equals(order_state, order.order_state) &&
                Objects.equals(order_createtime, order.order_createtime) &&
                Objects.equals(account_id, order.account_id) &&
                Objects.equals(account_name, order.account_name) &&
                Objects.equals(account_nickname, order.account_nickname) &&
                Objects.equals(site_id, order.site_id) &&
                Objects.equals(site_number, order.site_number) &&
                Objects.equals(gym_id, order.gym_id) &&
                Objects.equals(gym_name, order.gym_name) &&
                Objects.equals(gym_price, order.gym_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, order_number, order_date, order_timestart, order_timeend, order_state, order_createtime, account_id, account_name, account_nickname, site_id, site_number, gym_id, gym_name, gym_price);
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_timestart() {
        return order_timestart;
    }

    public void setOrder_timestart(String order_timestart) {
        this.order_timestart = order_timestart;
    }

    public String getOrder_timeend() {
        return order_timeend;
    }

    public void setOrder_timeend(String order_timeend) {
        this.order_timeend = order_timeend;
    }

    public Integer getOrder_state() {
        return order_state;
    }

    public void setOrder_state(Integer order_state) {
        this.order_state = order_state;
    }

    public String getOrder_createtime() {
        return order_createtime;
    }

    public void setOrder_createtime(String order_createtime) {
        this.order_createtime = order_createtime;
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

    public String getAccount_nickname() {
        return account_nickname;
    }

    public void setAccount_nickname(String account_nickname) {
        this.account_nickname = account_nickname;
    }

    public Integer getSite_id() {
        return site_id;
    }

    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    public String getSite_number() {
        return site_number;
    }

    public void setSite_number(String site_number) {
        this.site_number = site_number;
    }

    public Integer getGym_id() {
        return gym_id;
    }

    public void setGym_id(Integer gym_id) {
        this.gym_id = gym_id;
    }

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }

    public Float getGym_price() {
        return gym_price;
    }

    public void setGym_price(Float gym_price) {
        this.gym_price = gym_price;
    }
}
