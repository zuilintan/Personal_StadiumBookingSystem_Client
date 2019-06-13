package com.lt.personal_stadiumbookingsystem.entity;

import java.util.Objects;

/**
 * @作者: LinTan
 * @日期: 2019/4/27 23:06
 * @版本: 1.0
 * @描述: //场馆实体类
 * 1.0: Initial Commit
 */

public class Gym {
    private Integer gym_id;
    private String gym_name;//场馆名
    private Float gym_price;//场馆价格
    private Integer gym_managestate;//场馆维护状态(0:维护中，1:营业中)
    private String gym_opentime;//场馆开馆时间
    private String gym_closetime;//场馆闭馆时间

    @Override
    public String toString() {
        return "Gym{" +
                "gym_id=" + gym_id +
                ", gym_name='" + gym_name + '\'' +
                ", gym_price=" + gym_price +
                ", gym_managestate=" + gym_managestate +
                ", gym_opentime='" + gym_opentime + '\'' +
                ", gym_closetime='" + gym_closetime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return Objects.equals(gym_id, gym.gym_id) &&
                Objects.equals(gym_name, gym.gym_name) &&
                Objects.equals(gym_price, gym.gym_price) &&
                Objects.equals(gym_managestate, gym.gym_managestate) &&
                Objects.equals(gym_opentime, gym.gym_opentime) &&
                Objects.equals(gym_closetime, gym.gym_closetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gym_id, gym_name, gym_price, gym_managestate, gym_opentime, gym_closetime);
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

    public Integer getGym_managestate() {
        return gym_managestate;
    }

    public void setGym_managestate(Integer gym_managestate) {
        this.gym_managestate = gym_managestate;
    }

    public String getGym_opentime() {
        return gym_opentime;
    }

    public void setGym_opentime(String gym_opentime) {
        this.gym_opentime = gym_opentime;
    }

    public String getGym_closetime() {
        return gym_closetime;
    }

    public void setGym_closetime(String gym_closetime) {
        this.gym_closetime = gym_closetime;
    }
}
