package main

import (
	"router"

	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
)

func main() {
	server := echo.New()

	server.Use(middleware.Logger())
	server.Use(middleware.Recover())

	router.Register(server.Group("/"))

	server.Start(":8080")
}
