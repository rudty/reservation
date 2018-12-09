package model_test

import (
	"dateutil"
	"fmt"
	"model"
	"testing"
)

func TestReservation(t *testing.T) {
	begin := dateutil.MustParse("2015-12-07 12:00:00")
	end := dateutil.MustParse("2015-12-07 23:59:59")
	if err := model.DoReservation(begin, end, "테스트", "테스트 유저", 0); err != nil {
		t.Fatal(fmt.Errorf("%v", err))
	}

	db := model.GetDB()
	db.Exec("delete from reservation where beginTime < '2016-01-01'")
}

func Test예약두번함(t *testing.T) {
	begin := dateutil.MustParse("2015-12-07 12:00:00")
	end := dateutil.MustParse("2015-12-07 23:59:59")
	if err := model.DoReservation(begin, end, "테스트", "테스트 유저", 0); err != nil {
		t.Fatal(fmt.Errorf("%v", err))
	}
	if err := model.DoReservation(begin, end, "테스트", "테스트 유저", 0); err == nil {
		t.Fatal("두번째 예약은 실패해야함")
	} else {
		fmt.Printf("%v\n", err)
	}

	db := model.GetDB()
	db.Exec("delete from reservation where beginTime < '2016-01-01'")
}
func Test이상한_아이디(t *testing.T) {
	begin := dateutil.MustParse("2015-12-07 12:00:00")
	end := dateutil.MustParse("2015-12-07 23:59:59")
	if err := model.DoReservation(begin, end, "테스트", "테스트유저123", 0); err == nil {
		t.Fatal("예약은 실패해야함")
	} else {
		fmt.Printf("%v\n", err)
	}

	// db := model.GetDB()
	// db.Exec("delete from reservation where beginTime < '2016-01-01'")
}

func Test이상한_방제목(t *testing.T) {
	begin := dateutil.MustParse("2015-12-07 12:00:00")
	end := dateutil.MustParse("2015-12-07 23:59:59")
	if err := model.DoReservation(begin, end, "테스트12312", "테스트 유저", 0); err == nil {
		t.Fatal("예약은 실패해야함")
	} else {
		fmt.Printf("%v\n", err)
	}

	// db := model.GetDB()
	// db.Exec("delete from reservation where beginTime < '2016-01-01'")
}
