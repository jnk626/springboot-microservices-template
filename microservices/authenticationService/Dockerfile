# Usa l'immagine ufficiale di Java come base
FROM openjdk:11

# Copia il file JAR del tuo microservizio nel container
COPY /authenticationService/target/authenticationService.jar app.jar

# Espone la porta su cui il tuo microservizio sarà in ascolto
EXPOSE 8081

# Comando per eseguire l'applicazione
CMD ["java","-jar","/app.jar"]
