package model

import (
	"dateutil"
	"time"

	"github.com/rudty/go-parallel"
)

//RoomStatus 방의 예약 정보를 나타냅니다.
//예약 정보 단위당 하나
type RoomStatus struct {
	Index     int64  `db:"idx" json:"idx"`
	BeginTime string `db:"beginTime" json:"beginTime"`
	EndTime   string `db:"endTime" json:"endTime"`
	UserName  string `db:"userName" json:"userName"`
	Repeat    int    `db:"repeat" json:"repeat"`
}

//ReservationStatus 예약 상태를 확인
type ReservationStatus struct {
	Name       string `json:"name"`
	RoomStatus []RoomStatus
}

//GetReservationStatus 예약 상황을 로드합니다
//일단 방이름을 조회하고
//그 방 이름으로 예약된것이 있나 조회합니다.
func GetReservationStatus(beginTime, endTime time.Time) ([]ReservationStatus, error) {
	if err := dateutil.CheckBetweenDates(beginTime, endTime, 7); err != nil {
		return nil, err
	}
	db := GetDB()
	roomNames := make([]string, 0, 10)
	if err := db.Select(&roomNames, "exec get_room_names"); err != nil {
		return nil, err
	}

	reservationStatus := make([]ReservationStatus, len(roomNames))

	parallel.ForEach(roomNames, func(i int, roomName string) {
		reservationStatus[i].Name = roomName
		reservationStatus[i].RoomStatus = make([]RoomStatus, 0, 42)

		//igore parallel error
		db.Select(&reservationStatus[i].RoomStatus,
			"exec get_reservation_status @p1,@p2,@p3", beginTime, endTime, roomName)
	})

	return reservationStatus, nil
}
