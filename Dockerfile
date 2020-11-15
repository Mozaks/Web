FROM java:8
ADD WebApplication.java .
RUN javac WebApplication.java
CMD ["java", "WebApplication"]