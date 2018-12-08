package org.rudty.reservation.status.repository;



import javax.persistence.*;

@Entity
@Table(name="reservation")
public class RoomStatus {
    @Id
    @Column(name = "idx")
    long idx;

    @Column(name = "beginTime")
    String beginTime;

    @Column(name = "endTime")
    String endTime;

    @Column(name = "userName")
    String userName;

    @Column(name = "repeat")
    int repeat;


    public RoomStatus() {
    }

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return "RoomStatus{" +
                "idx=" + idx +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userName='" + userName + '\'' +
                ", repeat=" + repeat +
                '}';
    }
}

