# Gatling
Spring Boot WebSocket + Gatling 을 이용한 웹 애플리케이션 부하 테스트  
프론트 개발 없이 웹 애플리케이션 XML 전송 테스트를 위해 Gatling 을 이용한다.  
3.7 버전부터 Java 를 지원하므로, 시나리오는 Java 로 작성하였다.  

Gatling 은 Stomp Frame 을 만들어주는 기능이 없어서,  
직접 Stomp Frame 을 만들어 웹 소켓 서버에 전송해야 정상 동작한다.  

Debug: SubProtocolWebSocketHandler.handleMessage

gatling docs: https://gatling.io/docs/gatling  
gatling community: https://community.gatling.io  
gradle demo git: https://github.com/gatling/gatling-gradle-plugin-demo-java  
Stomp Protocol Specification: https://stomp.github.io