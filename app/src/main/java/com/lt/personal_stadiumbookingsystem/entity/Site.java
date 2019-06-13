package com.lt.personal_stadiumbookingsystem.entity;

import java.util.Objects;

/**
 * @作者: LinTan
 * @日期: 2019/4/25 15:30
 * @版本: 1.0
 * @描述: //场地实体类
 * 1.0: Initial Commit
 */

public class Site {
    private Integer site_id;
    private String site_number;//场地号
    private Integer site_managestate;//场地维护状态(0:维护中，1:营业中)
    private Integer gym_id;//场地的场馆ID
    private String gym_name;//场馆ID对应的场馆名。非该Table字段

    @Override
    public String toString() {
        return "Site{" +
                "site_id=" + site_id +
                ", site_number=" + site_number +
                ", site_managestate=" + site_managestate +
                ", gym_id=" + gym_id +
                ", gym_name='" + gym_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return Objects.equals(site_id, site.site_id) &&
                Objects.equals(site_number, site.site_number) &&
                Objects.equals(site_managestate, site.site_managestate) &&
                Objects.equals(gym_id, site.gym_id) &&
                Objects.equals(gym_name, site.gym_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site_id, site_number, site_managestate, gym_id, gym_name);
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

    public Integer getSite_managestate() {
        return site_managestate;
    }

    public void setSite_managestate(Integer site_managestate) {
        this.site_managestate = site_managestate;
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
}
