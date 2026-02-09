## Event Manager

#### Prequisities

* Java 21
* PostgreSQL 14
* Vue 3
* npm

#### Running backend

* Create new database with name defined in `application.yaml`: `spring.datasource.url`
* Make necessary username & password changes in `application.yaml` for DB connection: `spring.datasource`
* Move to project root directory
* Execute `./gradlew bootRun`
* Login credentials can be found and modified in `application.yaml`: `spring.security.user`

#### Running frontend

* Move to `/src/main/webapp` directory
* Execute `npm install`
* Execute `npm run dev`
