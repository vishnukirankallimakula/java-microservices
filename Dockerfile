FROM maven:3.8.1-openjdk-11
WORKDIR /
COPY /target/CustomerProductManagement-1.0.0.war ./app.war
RUN ls -ltra
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]
