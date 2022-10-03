# school-registration-system
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
## Coverage
![coverage](https://i.imgur.com/GwEKO3G.png)

## Endpoints and payloads

- Check http://localhost:8080/swagger-ui/index.html

## How to set up project
—-------------------------------------------------------
- download the code : https://github.com/tomas-santucho/school-registration-system.git
- execute the following commands
```sh
./gradlew clean build -x test #for some reason some tests run ok from the ide but fail from the command line
sudo docker-compose build
sudo docker-compose up
```
