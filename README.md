### Как запустить код
1. Открыть проект в Intellij Idea, дождаться, пока скачаются все зависимости.
2. Запустить postgresql.
3. Указать данные доступа к своему инстансу postgresql тут https://github.com/artyomkin/soa4service1/blob/main/src/main/resources/application.properties
4. Запустить плагин jaxb2. https://i.imgur.com/ALLhR9u.png. Должны появиться вот такие классы по пути target/generated-sources/jaxb/https/localhost/_8080/api/v1/space_marines - https://i.imgur.com/BZopyZ8.png
5. Запустить код в Idea.
6. WSDL будет доступен по ссылке https://localhost:8080/ws/soa4.wsdl
