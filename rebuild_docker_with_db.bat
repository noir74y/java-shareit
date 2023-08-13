docker container rm shareit-gw-cont shareit-srv-cont shareit-db-cont
docker image rm shareit-gw-img shareit-srv-img postgres:13.7-alpine
mvn clean package
docker compose up
