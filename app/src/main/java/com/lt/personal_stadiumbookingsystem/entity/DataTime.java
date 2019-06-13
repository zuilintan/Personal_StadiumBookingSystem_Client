package com.lt.personal_stadiumbookingsystem.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class DataTime {

    /**
     * gym : 台球馆
     * site : 5
     * date : 2019-05-04
     * time : [{"time_start":"08:20:00","time_end":"10:00:00","time_orderstate":0},{"time_start":"10:05:00","time_end":"11:45:00","time_orderstate":0},{"time_start":"11:50:00","time_end":"13:30:00","time_orderstate":0},{"time_start":"13:35:00","time_end":"15:15:00","time_orderstate":0},{"time_start":"15:20:00","time_end":"17:00:00","time_orderstate":0},{"time_start":"17:05:00","time_end":"18:45:00","time_orderstate":0},{"time_start":"18:50:00","time_end":"20:30:00","time_orderstate":0}]
     */

    @SerializedName("gym")
    private String gym;
    @SerializedName("site")
    private String site;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private List<Time> time;

    @Override
    public String toString() {
        return "DataTime{" +
                "gym='" + gym + '\'' +
                ", site='" + site + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTime dataTime = (DataTime) o;
        return Objects.equals(gym, dataTime.gym) &&
                Objects.equals(site, dataTime.site) &&
                Objects.equals(date, dataTime.date) &&
                Objects.equals(time, dataTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gym, site, date, time);
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Time> getTime() {
        return time;
    }

    public void setTime(List<Time> time) {
        this.time = time;
    }
}
