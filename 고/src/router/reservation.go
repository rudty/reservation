package router

import (
	"dateutil"
	"fmt"
	"model"
	"net/http"
	"strconv"

	"github.com/labstack/echo"
)

func reservation(c echo.Context) error {
	var err error
	beginDate, err := GetDateFormValue(c, "beginDate")
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	endDate, err := GetDateFormValue(c, "endDate")
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	roomName := c.FormValue("roomName")
	if len(roomName) == 0 {
		return c.String(http.StatusBadRequest, "require roomName")
	}

	userName := c.FormValue("userName")
	if len(userName) == 0 {
		return c.String(http.StatusBadRequest, "require userName")
	}

	repeat, err := strconv.Atoi(c.FormValue("repeat"))
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	//날짜가 사이에 있는지 확인
	if err := dateutil.CheckBetweenDates(*beginDate, *endDate, 0); err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	//00분 혹은 30분
	if !dateutil.CheckReservationMMss(*beginDate) {
		return c.String(http.StatusBadRequest, "beginDate min 00 or 30")
	}

	//00분 혹은 30분
	if !dateutil.CheckReservationMMss(*endDate) {
		return c.String(http.StatusBadRequest, "endDate min 00 or 30")
	}

	if err = model.DoReservation(
		*beginDate, *endDate, roomName, userName, repeat); err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}
	return c.String(http.StatusOK, "OK")
}
