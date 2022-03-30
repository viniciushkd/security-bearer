# Security Bearer

## Docker
```
docker pull viniciushkd/bearer:dev
```
## Database
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
curl --location --request POST 'http://127.0.0.1:8080/api/v1/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=43DB8BC7765879805DAE48D9F127FEFB' \
--data-raw '{
    "username": "admin",
    "password": "admin"
}'
```
### POST /api/v1/user
Add a new user. (*Inform the Bearer Token in the Key Authorization.*)
```
curl --location --request POST 'http://127.0.0.1:8080/api/v1/user' \
--header 'Content-Type: application/json' \
--header 'Authorization: {Bearer Token}' \
--header 'Cookie: JSESSIONID=43DB8BC7765879805DAE48D9F127FEFB' \
--data-raw '{
    "dto": {
        "username": "user1",
        "password": "user1"
    }
}'
```
### GET /api/v1/user
List users. (*Inform the Bearer Token in the Key Authorization.*)
```
curl --location --request GET 'http://127.0.0.1:8080/api/v1/user' \
--header 'Content-Type: application/json' \
--header 'Authorization: {Bearer Token}' \
--header 'Cookie: JSESSIONID=640B4F20A4AC75966DF2A0324A759017'
```
### GET /api/v1/user/{uid}
Get user. (*Inform the Bearer Token in the Key Authorization.*)
```
curl --location --request GET 'http://127.0.0.1:8080/api/v1/user/{uid}' \
--header 'Content-Type: application/json' \
--header 'Authorization: {Bearer Token}' \
--header 'Cookie: JSESSIONID=43DB8BC7765879805DAE48D9F127FEFB'
```
