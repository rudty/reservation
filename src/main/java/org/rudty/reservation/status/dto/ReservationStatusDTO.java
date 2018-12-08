package org.rudty.reservation.status.dto;

import org.rudty.reservation.status.repository.RoomStatus;

import java.util.List;

public class ReservationStatusDTO {
    private String roomName;
    private List<RoomStatus> roomStatusList;

    public ReservationStatusDTO() {
    }

    public ReservationStatusDTO(String roomName, List<RoomStatus> roomStatusList) {
        this.roomName = roomName;
        this.roomStatusList = roomStatusList;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<RoomStatus> getRoomStatusList() {
        return roomStatusList;
    }

    public void setRoomStatusList(List<RoomStatus> roomStatusList) {
        this.roomStatusList = roomStatusList;
    }

    @Override
    public String toString() {
        return "ReservationStatusDTO{" +
                "roomName='" + roomName + '\'' +
                ", roomStatusList=" + roomStatusList +
                '}';
    }
}
