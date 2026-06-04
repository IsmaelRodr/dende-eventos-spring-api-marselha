package br.com.dendesofthouse.dendeeventos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @GetMapping("/ping")
    public String ping() { return "EventoController funcionando!"; }
}