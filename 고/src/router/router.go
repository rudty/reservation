package router

import (
	"dateutil"
	"errors"
	"fmt"
	"time"

	"github.com/labstack/echo"
)

//Register 이 디렉터리의 라우팅 함수들을  등록합니다
func Register(r *echo.Group) {
	r.GET("status", status)
	r.GET("status/", status)

	r.POST("reservation", reservation)
	r.POST("reservation/", reservation)
}

//GetDateFormValue formValue에서 date 문자열을 파싱합니다.
func GetDateFormValue(c echo.Context, name string) (*time.Time, error) {
	timeString := c.FormValue(name)

	if len(timeString) == 0 {
		return nil, errors.New("empty form value")
	}

	formTime, err := dateutil.Parse(timeString)
	if err != nil {
		return nil, fmt.Errorf("require %s  yyyy-mm-dd hh:mm:ss", name)
	}

	return &formTime, nil
}
