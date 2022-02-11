From golang:1.13.4-alpine3.10
WORKDIR /src
COPY ./main.go /src
RUN go build -o /bin/start_appserver main.go
WORKDIR /
CMD ["/bin/start_appserver"]