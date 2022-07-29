VERSION ?= v0.0.1

REGISTRY ?= ghcr.io/team-dom-toren/loopsnelheid-app-api


# Commands
docker: docker-build docker-push

docker-build:
	docker build . --target api -t ${REGISTRY}/api:${VERSION}

docker-push:
	docker push ${REGISTRY}/api:${VERSION}