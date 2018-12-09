package router

import (
	"fmt"
	"model"
	"net/http"

	"github.com/labstack/echo"
)

// http://localhost:8080/status
func status(c echo.Context) error {

	var err error
	beginDate, err := GetDateFormValue(c, "beginDate")
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	endDate, err := GetDateFormValue(c, "endDate")
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	reservationStatus, err := model.GetReservationStatus(*beginDate, *endDate)
	if err != nil {
		return c.String(http.StatusBadRequest, fmt.Sprintf("%v", err))
	}

	return c.JSON(http.StatusOK, reservationStatus)
}
