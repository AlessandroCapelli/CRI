# CRI
## Alessandro Capelli

### Esecuzione:

#### Packaging:

Il packaging dell'applicazione viene effettuato tramite il seguente comando: `./mvnw clean package spring-boot:repackage`

#### Esecuzione:

L'esecuzione dell'applicazione è effettuata tramite il seguente comando: `java -Djava.security.egd=file:/dev/./urandom -jar target/CRI.jar`

Quando l'applicazione è in esecuzione, essa è disponibile all'indirizzo locale: http://localhost:8080