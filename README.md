# Microservices deployment & Performance Testing
A project to deploy and run the [Rock Paper Scissors Microservice](https://github.com/gavarava/rock-paper-scissors-2)

# Run Microservice & Backing Services
This runs a Postgres Database, NGINX reverse proxy & a microservice 
```
cd environment
docker-compose up -d
```
## Run Load Tests using Gatling
```
cd load-tests
mvn gatling:test
```
## Monitoring using Grafana & Prometheus
```
cd load-tests
mvn gatling:test
```
## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details