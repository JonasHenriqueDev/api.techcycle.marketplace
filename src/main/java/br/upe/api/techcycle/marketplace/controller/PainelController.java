package br.upe.api.techcycle.marketplace.controller;

import br.upe.api.techcycle.marketplace.dominio.PontoColeta;
import br.upe.api.techcycle.marketplace.service.PontoColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/painel")
public class PainelController {
    @Autowired
    private PontoColetaService pontoColetaService;

    @GetMapping
    public String listarPontosColeta(Model model) {
        List<PontoColeta> pontos = pontoColetaService.findAll();
        model.addAttribute("pontos", pontos);
        return "pontos-coleta";
    }

    @PostMapping("/adicionar")
    public String adicionarPontoColeta(PontoColeta pontoColeta) {
        pontoColetaService.save(pontoColeta);
        return "redirect:/pontos-coleta";
    }

    @PostMapping("/atualizar")
    public String atualizarPontoColeta(PontoColeta pontoColeta) {
        pontoColetaService.save(pontoColeta);
        return "redirect:/pontos-coleta";
    }

    @PostMapping("/remover")
    public String removerPontoColeta(@RequestParam Long id) {
        pontoColetaService.deleteById(id);
        return "redirect:/pontos-coleta";
    }
}
