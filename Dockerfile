FROM openjdk
COPY "./build/libs/Course_work_spring-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]