package br.upe.api.techcycle.marketplace.controller;

import br.upe.api.techcycle.marketplace.dominio.Recompensa;
import br.upe.api.techcycle.marketplace.service.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recompensa")
public class RecompensaController {

    @Autowired
    private RecompensaService recompensaService;

    @GetMapping
    public ResponseEntity<List<Recompensa>> listar() {
        List<Recompensa> recompensas = recompensaService.listar();
        return ResponseEntity.ok(recompensas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recompensa> listarPorId(@PathVariable Long id) {
        Optional<Recompensa> recompensa = recompensaService.listarPorId(id);
        return recompensa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Recompensa> adicionar(@RequestBody Recompensa recompensa) {
        Recompensa novaRecompensa = recompensaService.adicionarRecompensa(recompensa);
        return ResponseEntity.ok(novaRecompensa);
    }

    @DeleteMapping
    public String deletarPorId(@PathVariable Long id) {
        recompensaService.deletarRecompensa(id);
        return "Deletado com sucesso!";
    }
}
