package dateutil_test

import (
	"dateutil"
	"testing"
)

func Test시작시간이_끝시간보다_크다(t *testing.T) {
	begin := dateutil.MustParse("2018-01-01 01:00:00")
	end := dateutil.MustParse("2018-01-01 00:00:00")

	if err := dateutil.CheckBetweenDates(begin, end, 0); err == nil {
		t.Fatal("시작시간이 끝시간보다 큼 ")

	}
}

func Test같은_시간이다(t *testing.T) {
	begin := dateutil.MustParse("2018-01-01 01:00:00")
	end := dateutil.MustParse("2018-01-01 01:00:00")

	if err := dateutil.CheckBetweenDates(begin, end, 0); err == nil {
		t.Error("같은 시간 ")
	}
}

func Test다음날_성공(t *testing.T) {
	begin := dateutil.MustParse("2018-01-01 00:00:00")
	end := dateutil.MustParse("2018-01-02 00:00:00")

	if err := dateutil.CheckBetweenDates(begin, end, 0); err != nil {
		t.Error("00시부터 다음날 00시까지는 성공해야함  ")
	}
}
