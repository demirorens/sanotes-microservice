#!/bin/sh

VAULT_DEV_TOKEN=c9858719-8008-4564-8d24-5afb6db6ef98

vault login ${VAULT_DEV_TOKEN}

vault secrets disable secret
vault secrets enable -version=1 -path=secret kv

vault kv put secret/application @${CONFIG_DIR}/rabbitmq.json
vault kv put secret/application/docker @${CONFIG_DIR}/rabbitmq.json

vault kv put secret/notebook-service @${CONFIG_DIR}/database.json
vault kv put secret/notebook-service/docker @${CONFIG_DIR}/database.json

vault kv put secret/note-service @${CONFIG_DIR}/database.json
vault kv put secret/note-service/docker @${CONFIG_DIR}/database.json

vault kv put secret/tag-service @${CONFIG_DIR}/database.json
vault kv put secret/tag-service/docker @${CONFIG_DIR}/database.json

vault kv put secret/oauth2-server @${CONFIG_DIR}/keycloak.json
vault kv put secret/oauth2-server/docker @${CONFIG_DIR}/keycloak.json

vault kv put secret/user-service @${CONFIG_DIR}/user-service.json
vault kv put secret/user-service/docker @${CONFIG_DIR}/user-service.json

