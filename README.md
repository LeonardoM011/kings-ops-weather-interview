# Read Me First

Run the project using spring and maven
jdk 21 is required

Run using:
````
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Dspring.mail.username=<REPLACE-WITH-YOUR-EMAIL> -Dspring.mail.password=<REPLACE-WITH-YOUR-APP-PASSWORD> -Dmail.recipient=<REPLACE-WITH-RECIPIENT-EMAIL>"
````