package br.com.dendesofthouse.dendeeventos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/ping")
    public String ping() {
        return "Controller conectado no padrão da Equipe Marselha!";
    }
}