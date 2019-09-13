FROM java:8
EXPOSE 8080
ADD remicroarticle.jar remicroarticle.jar
ENTRYPOINT ["java","-jar","remicroarticle.jar"]