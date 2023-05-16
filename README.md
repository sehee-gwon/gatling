# Gatling
Spring Boot WebSocket + Gatling + Java + Gradle 을 이용한 웹 애플리케이션 부하 테스트  
프론트 개발 없이 웹 애플리케이션 XML 전송 테스트를 위해 Gatling 을 이용한다. (3.7 버전부터 Java 지원)  

Gatling 은 Stomp Frame 을 만들어주는 기능이 없어서,  
직접 Stomp Frame 을 만들어줘야 한다.  

Debug: SubProtocolWebSocketHandler.handleMessage

Gatling Docs: https://gatling.io/docs/gatling  
Gatling Community: https://community.gatling.io  
Gatling-Gradle-Java Demo Git: https://github.com/gatling/gatling-gradle-plugin-demo-java  
Stomp Protocol Specification: https://stomp.github.io
