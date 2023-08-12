docker container rm shareit-gw-cont shareit-srv-cont
docker image rm shareit-gw-img shareit-srv-img
mvn clean package
docker compose up
