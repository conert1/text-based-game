all: compile server run_default_docker

compile:
	mvn compile
	mvn package -DskipTests

server:
	java -jar libs/toyrobot.jar &
	mvn test
	./runserver.sh stop

run_default_docker:
	docker run --name test-docker -p 5000:5050 -p 5001:5050 robot-worlds-server:1.0.0 -p 5050 -h 5051 &
	sleep 5
	docker test
	docker stop test-docker

build_docker:
	docker build -t robot-worlds-server:1.0.0 .

push_docker:
	make build_docker
	docker push gitlab.wethinkco.de:5000
