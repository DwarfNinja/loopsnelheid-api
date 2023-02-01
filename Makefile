VERSION ?= v0.0.1

REGISTRY ?= ghcr.io/team-dom-toren/loopsnelheid-api

TYPE ?= dev


# Commands
docker: docker-build docker-push

docker-build:
	docker build . --target api -t ${REGISTRY}/${TYPE}:${VERSION}

docker-push:
	docker push ${REGISTRY}/${TYPE}:${VERSION}
