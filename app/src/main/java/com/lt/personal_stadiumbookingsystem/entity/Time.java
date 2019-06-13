package com.lt.personal_stadiumbookingsystem.entity;

import java.util.Objects;

/**
 * @作者: LinTan
 * @日期: 2019/5/2 20:48
 * @版本: 1.0
 * @描述: //时段实体类。非DB的Table
 * 1.0: Initial Commit
 */

public class Time {
    private String time_start;//时段开始时间
    private String time_end;//时段结束时间
    private Integer time_orderstate;//该时段的预订状态(-1:可下单，0:待付款，1:已付款，2:已执行(或过期)，3:已取消)
    private Integer time_expirestate;//该时段是否已经过期(0:未过期，1已过期)

    @Override
    public String toString() {
        return "Time{" +
                "time_start='" + time_start + '\'' +
                ", time_end='" + time_end + '\'' +
                ", time_orderstate=" + time_orderstate +
                ", time_expirestate=" + time_expirestate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Objects.equals(time_start, time.time_start) &&
                Objects.equals(time_end, time.time_end) &&
                Objects.equals(time_orderstate, time.time_orderstate) &&
                Objects.equals(time_expirestate, time.time_expirestate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time_start, time_end, time_orderstate, time_expirestate);
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public Integer getTime_orderstate() {
        return time_orderstate;
    }

    public void setTime_orderstate(Integer time_orderstate) {
        this.time_orderstate = time_orderstate;
    }

    public Integer getTime_expirestate() {
        return time_expirestate;
    }

    public void setTime_expirestate(Integer time_expirestate) {
        this.time_expirestate = time_expirestate;
    }
}
