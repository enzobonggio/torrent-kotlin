FROM findepi/graalvm

EXPOSE 8080 8080

COPY build/libs/poc-kotlin.jar /usr/local/app/lib/
COPY graal-app.json /usr/local/app/lib/

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y build-essential && \
    apt-get install zlib1g-dev && \
    apt-get install unzip && \
    unzip /usr/local/app/lib/poc-kotlin.jar -d /usr/local/app/lib/poc-kotlin && \
    cd /usr/local/app/lib/ && \
    gu install native-image && \
    native-image \
    -H:Name=app \
    -H:ReflectionConfigurationFiles=/usr/local/app/lib/graal-app.json \
    -Dio.netty.noUnsafe=true \
    -H:+ReportUnsupportedElementsAtRuntime \
    -H:+ReportExceptionStackTraces \
    --no-fallback \
    --allow-incomplete-classpath \
    --initialize-at-build-time=org.springframework.util.unit.DataSize,ch.qos.logback.classic.Logger \
    --initialize-at-run-time=io.netty.handler.codec.http.HttpObjectEncoder,org.springframework.core.io.VfsUtils \
    -Dfile.encoding=UTF-8 \
    -cp ".:$(echo /usr/local/app/lib/poc-kotlin/BOOT-INF/lib/*.jar | tr ' ' ':')":/usr/local/app/lib/poc-kotlin/BOOT-INF/classes com.example.pockotlin.PocKotlinApplicationKt

ENTRYPOINT /usr/local/app/lib/app