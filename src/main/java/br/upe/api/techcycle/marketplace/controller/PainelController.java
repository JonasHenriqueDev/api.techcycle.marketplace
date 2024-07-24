package br.upe.api.techcycle.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/painel")
public class PainelController {
    @GetMapping
    public String mostrarPagina() {
        return "painel"; // Nome do arquivo HTML na pasta templates
    }
}
