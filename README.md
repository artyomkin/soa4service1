### Как запустить код
1. Открыть проект в Intellij Idea, дождаться, пока скачаются все зависимости.
2. Запустить postgresql.
3. Указать данные доступа к своему инстансу postgresql тут https://github.com/artyomkin/soa4service1/blob/main/src/main/resources/application.properties
4. Запустить плагин jaxb2. https://i.imgur.com/ALLhR9u.png. Должны появиться вот такие классы по пути target/generated-sources/jaxb/https/localhost/_8080/api/v1/space_marines - https://i.imgur.com/BZopyZ8.png
5. Запустить код в Idea.

WSDL будет доступен по ссылке https://localhost:8080/ws/soa4.wsdl

### Примеры
Примеры запросов есть в папке soa4-reqs
Как сделать запрос на получение всех SpaceMarine

Заходим в консоль и выполняем команду:
```bash
cd path/to/soa4service1/soa4-reqs
curl --header "content-type: text/xml" -d @getAllReq.xml https://localhost:8080/ws --insecure
```

Получаем
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <ns2:getSpaceMarineResponse xmlns:ns2="https://localhost:8080/api/v1/space-marines">
      <ns2:spaceMarine>
        <ns2:id>9</ns2:id>
        <ns2:name>dimon</ns2:name>
        <ns2:coordinates>
          <ns2:id>12</ns2:id>
          <ns2:x>10.0</ns2:x>
        ...
        <ns2:starshipId>1</ns2:starshipId>
      </ns2:spaceMarine>
    </ns2:getSpaceMarineResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

Рекомендую установить утилиту для xml beautify, т.к. вывод будет в сжатом виде.
