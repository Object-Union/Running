FROM openjdk

RUN mkdir /usr/upload/

RUN mkdir /usr/upload/avatar/

RUN mkdir /usr/upload/moment/

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone

ADD ./Running-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
