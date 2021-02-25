package com.frozendo.learn.springsecurity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Aplicação de teste Basic Auth");
    }

    @RequestMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Admin route");
    }

    @RequestMapping("/admin/path")
    public ResponseEntity<String> adminPath() {
        return ResponseEntity.ok("Admin subroute");
    }

    @RequestMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("User route");
    }

    @RequestMapping("/user/path")
    public ResponseEntity<String> userPath() {
        return ResponseEntity.ok("User subroute");
    }

}
