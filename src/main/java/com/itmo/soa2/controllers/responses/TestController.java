package com.itmo.soa2.controllers.responses;

import com.itmo.soa2.controllers.requests.TestRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TestController {
    @PostMapping(value = "/api/v1/test")
    public ResponseEntity test(@RequestBody TestRequest testRequest){
        return ResponseEntity.ok("Hello " + testRequest.t);
    }
}
