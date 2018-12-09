package model

import (
	"log"
	"net/url"

	_ "github.com/denisenkom/go-mssqldb" // mssql
	"github.com/jmoiron/sqlx"
)

var database *sqlx.DB

func init() {
	query := url.Values{}
	query.Add("database", "reservation")

	u := url.URL{
		Scheme:   "sqlserver",
		User:     url.UserPassword("user", "1111"),
		Host:     "localhost:1433",
		RawQuery: query.Encode(),
	}

	var err error
	database, err = sqlx.Open("sqlserver", u.String())

	if err != nil {
		log.Fatal("db connect fail")
	}
}

//GetDB db 인스턴스를 들고옵니다
func GetDB() *sqlx.DB {
	return database
}
