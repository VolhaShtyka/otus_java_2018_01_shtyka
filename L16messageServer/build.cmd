cd MessageServer
call mvn clean install
cd ..\FrontendService
call mvn clean package
copy target\frontend-service-1.0-SNAPSHOT.jar ..\MessageServer\target
cd ..\DBService
call mvn clean package
copy target\db-service-1.0-SNAPSHOT.jar ..\MessageServer\target
cd ..\MessageServer\target
java -jar message-server-1.0-SNAPSHOT.jar
cd ..\..