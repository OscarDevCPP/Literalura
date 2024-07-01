#!/bin/bash
DOCKER_COMPOSE_PATH=.docker
DOCKER_COMPOSE_FILE=${DOCKER_COMPOSE_PATH}/docker-compose.yml

ifneq (,$(wildcard ${DOCKER_COMPOSE_PATH}/.env))
    include ${DOCKER_COMPOSE_PATH}/.env
    export
endif

APP_NETWORK = ${APP_NAME}_network

UID = $(shell id -u)

help: ## Show this help message
	@echo 'Welcome to ${APP_NAME} make'
	@echo 'usage: make [target]'
	@echo
	@echo 'targets:'
	@egrep '^(.+)\:\ ##\ (.+)' ${MAKEFILE_LIST} | column -t -c 2 -s ':#'

run: ## Start the containers
	docker network create ${APP_NETWORK} || true
	U_ID=${UID} docker compose --project-directory=${DOCKER_COMPOSE_PATH} --file ${DOCKER_COMPOSE_FILE} up ${params}

stop: ## Stop the containers
	U_ID=${UID} docker compose --project-directory=${DOCKER_COMPOSE_PATH} --file ${DOCKER_COMPOSE_FILE} stop

down: ## Stop the containers
	U_ID=${UID} docker compose --project-directory=${DOCKER_COMPOSE_PATH} --file ${DOCKER_COMPOSE_FILE} down -v

restart: ## Restart the containers
	$(MAKE) stop && $(MAKE) run

rebuild: ## Rebuilds all the containers
	echo ${DOCKER_COMPOSE_PATH}
	U_ID=${UID} docker compose --project-directory=${DOCKER_COMPOSE_PATH} --file ${DOCKER_COMPOSE_FILE} build