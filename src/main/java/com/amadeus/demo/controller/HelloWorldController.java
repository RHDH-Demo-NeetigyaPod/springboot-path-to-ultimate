package com.amadeus.demo.controller;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
  
@RestController
public class HelloWorldController { 
    @RequestMapping("/") public String helloworld()
    { 
        return "Hello World"; 
    } 
}