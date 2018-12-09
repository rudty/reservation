package model_test

import (
	"dateutil"
	"model"
	"testing"
)

func TestDevel(t *testing.T) {
	begin := dateutil.MustParse("2018-12-07 12:00:00")
	end := dateutil.MustParse("2018-12-07 23:59:59")
	r, err := model.GetReservationStatus(begin, end)
	if err != nil {
		t.Fatal("db 연결 실패")
	}
	if len(r) == 0 {
		t.Fatal("값 추출 실패")
	}
}
