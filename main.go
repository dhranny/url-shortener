package main

import (
	"log"
	"net/http"
)

func main() {
	log.Print("Application Started")
	mux := http.DefaultServeMux

	server := http.Server{
		Addr:    "127.0.0.1:8080",
		Handler: mux,
	}

	log.Print("Server started on port 8080")
	server.ListenAndServe()

}
