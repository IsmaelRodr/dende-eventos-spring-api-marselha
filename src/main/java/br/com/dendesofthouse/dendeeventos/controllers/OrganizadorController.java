package br.com.dendesofthouse.dendeeventos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {
    @GetMapping("/ping")
    public String ping() { return "OrganizadorController funcionando!"; }
}