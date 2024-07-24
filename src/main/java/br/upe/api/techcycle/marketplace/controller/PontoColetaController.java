package br.upe.api.techcycle.marketplace.controller;


import br.upe.api.techcycle.marketplace.dominio.PontoColeta;
import br.upe.api.techcycle.marketplace.service.PontoColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pontos-coleta")
public class PontoColetaController {

    @Autowired
    private PontoColetaService pontoColetaService;

    @GetMapping
    public List<PontoColeta> getAllPontosColeta() {
        return pontoColetaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoColeta> getPontoColetaById(@PathVariable Long id) {
        Optional<PontoColeta> pontoColeta = pontoColetaService.findById(id);
        return pontoColeta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PontoColeta createPontoColeta(@RequestBody PontoColeta pontoColeta) {
        return pontoColetaService.save(pontoColeta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoColeta> updatePontoColeta(@PathVariable Long id, @RequestBody PontoColeta pontoColetaDetails) {
        Optional<PontoColeta> pontoColeta = pontoColetaService.findById(id);

        if (pontoColeta.isPresent()) {
            PontoColeta updatedPontoColeta = pontoColeta.get();
            updatedPontoColeta.setNome(pontoColetaDetails.getNome());
            updatedPontoColeta.setEndereco(pontoColetaDetails.getEndereco());
            updatedPontoColeta.setDescricao(pontoColetaDetails.getDescricao());
            pontoColetaService.save(updatedPontoColeta);
            return ResponseEntity.ok(updatedPontoColeta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoColeta(@PathVariable Long id) {
        Optional<PontoColeta> pontoColeta = pontoColetaService.findById(id);

        if (pontoColeta.isPresent()) {
            pontoColetaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
