package model

import (
	"context"
	"errors"
	"time"
)

//db 조회 결과로 하나만 가져옵니다.
func selectOne(dest interface{}, query string, arg ...interface{}) error {
	db := GetDB()
	row := db.QueryRowx(query, arg...)
	if row.Err() != nil {
		return row.Err()
	}
	if err := row.Scan(dest); err != nil {
		return err
	}
	return nil
}

//DoReservation 예약이 가능한지 확인하고 예약을 실행합니다.
func DoReservation(beginTime, endTime time.Time,
	roomName, userName string,
	repeat int) error {

	db := GetDB()

	var roomSN int
	if err := selectOne(&roomSN, "exec get_room_sn @p1", roomName); err != nil {
		return err
	}

	var userSN int
	if err := selectOne(&userSN, "exec get_user_sn @p1", userName); err != nil {
		return err
	}

	// 이 작업은 2초 안에 실행되어야 합니다.
	// 중복 검사 후 예약하는 부분.
	ctx, cancel := context.WithTimeout(context.Background(), 2*time.Second)
	defer cancel()

	transaction, err := db.Beginx()
	defer transaction.Commit()

	if err != nil {
		return err
	}

	//예약이 존재하는지 확인
	//중복검사
	row := transaction.QueryRowxContext(ctx,
		"exec confirm_availability_reservation @p1,@p2,@p3,@p4",
		beginTime, endTime, roomSN, repeat)

	if row.Err() != nil {
		return row.Err()
	}

	var ok bool
	row.Scan(&ok)

	if !ok {
		return errors.New("reservation exists")
	}

	//예약
	transaction.ExecContext(ctx,
		"exec request_reservation @p1,@p2,@p3,@p4,@p5",
		beginTime,
		endTime,
		roomSN,
		userSN,
		repeat)

	return nil
}
