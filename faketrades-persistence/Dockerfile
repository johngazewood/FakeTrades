FROM adoptopenjdk/openjdk11:latest
COPY build/libs/faketrades-persistence*.jar faketrades-persistence.jar
ENTRYPOINT ["java","-jar","./faketrades-persistence.jar", "production"]