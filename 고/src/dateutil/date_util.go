package dateutil

import (
	"errors"
	"fmt"
	"time"
)

const dateFormat = "2006-01-02 15:04:05"

//Parse yyyy-MM-dd HH:mm:ss 로 파싱
func Parse(maybeDateString string) (time.Time, error) {
	return time.ParseInLocation(dateFormat, maybeDateString, time.Local)
}

//MustParse yyyy-MM-dd HH:mm:ss 로 파싱
//이쪽은 에러없이 예외를 던짐
func MustParse(maybeDateString string) time.Time {
	time, err := Parse(maybeDateString)
	if err != nil {
		panic(err)
	}
	return time
}

//CheckBetweenDates 날짜의 시작과 끝 값이 적절한지 확인
func CheckBetweenDates(begin, end time.Time, maxDiffDay int) error {
	if begin.After(end) {
		return errors.New("begin > end")
	}

	if begin.Equal(end) {
		return errors.New("begin == end")
	}

	subTime := end.Sub(begin)
	subMinute := int64(subTime.Minutes())

	if maxDiffDay > 0 && subMinute > int64(maxDiffDay)*int64(24)*int64(60) {
		//0일 이상의 예약에 대해서는 일이 다르면 불가
		return fmt.Errorf("can not view more than a %d day", maxDiffDay)
	}

	if maxDiffDay == 0 {
		if subMinute > int64(24)*int64(60) {
			// 0일의 예약에 대해서는 24시간 * 60분이 넘는 예약은 불가능
			// 00시부터 다음날 00시까지로하면 날짜는 다르지만 예약은 가능
			return errors.New("can not view more than 24h")
		}
	}

	return nil
}

//CheckReservationMMss 예약하기에 적절한 0분, 혹은 30분인지 확인합니다.
func CheckReservationMMss(t time.Time) bool {
	minute := t.Minute()

	if minute == 0 || minute == 30 {
		return t.Second() == 0
	}

	return false
}
