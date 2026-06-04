package br.com.dendesofthouse.dendeeventos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    @GetMapping("/ping")
    public String ping() { return "IngressoController funcionando!"; }
}