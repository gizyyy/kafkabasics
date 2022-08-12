# Kafka Basics
This is a web application implemented to play with basic Kafka Features such as

- basic environment configuration
- publish/listen
- transactional consumer (One of the listeners are throwing exception intentionally to see transaction is rollbacking for both topics.)


### Installing
1. Clone this repository anywhere on your machine:
```
git clone git@github.com:gizyyy/kafkabasics.git
```

2. Run docker compose build
```
docker-compose up -d --build
```

## Installing dependencies
```bash
./gradlew build
```

## Tests and checks
To run all tests:
```bash
./gradlew test
```
