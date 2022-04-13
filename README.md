# Security Bearer

## Docker
### Upload with Circle CI
```
docker pull viniciushkd/security_bearer:dev_circleci
```
### Upload with GitHub Action
```
docker pull viniciushkd/security_bearer:dev_ghaction
```
## Database <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/viniciushkd/security-bearer?style=flat-square">
Access h2DB: http://127.0.0.1:8080/h2
```
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:sec
User Name: root
Password: toor
```
Run the following queries
```
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO usr (psswd ,uid ,usr) VALUES ('21232F297A57A5A743894A0E4A801FC3', '8d7aa23c-c3ff-49b8-bdab-f1bf35f97d33', 'admin');
INSERT INTO user_roles (usr_id ,role_id) VALUES (1, 2);
```
## API
### POST /api/v1/login
Bearer Token returns in response Header in key Authorization.
```
curl -v -X POST \
  http://127.0.0.1:8080/api/v1/login \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 876e687a-25e0-833a-934f-9342db4f23e3' \
  -d '{
    "username": "admin",
    "password": "admin"
}'
```
### POST /api/v1/user
Add a new user. (*Inform the Bearer Token in the Key Authorization.*)
```
curl -v -X POST \
  http://127.0.0.1:8080/api/v1/user \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'Authorization: {Bearer Token}' \
  -H 'postman-token: 876e687a-25e0-833a-934f-9342db4f23e3' \
  -d '{
    "dto": {
        "username": "user1",
        "password": "user1"
    }
}'
```
### GET /api/v1/user
List users. (*Inform the Bearer Token in the Key Authorization.*)
```
curl -v -X GET \
  http://127.0.0.1:8080/api/v1/user \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'Authorization: {Bearer Token}' \
  -H 'postman-token: 876e687a-25e0-833a-934f-9342db4f23e3'
```
### GET /api/v1/user/{uid}
Get user. (*Inform the Bearer Token in the Key Authorization.*)
```
curl -v -X GET \
  http://127.0.0.1:8080/api/v1/user/{uid} \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'Authorization: {Bearer Token}' \
  -H 'postman-token: 876e687a-25e0-833a-934f-9342db4f23e3'
```
