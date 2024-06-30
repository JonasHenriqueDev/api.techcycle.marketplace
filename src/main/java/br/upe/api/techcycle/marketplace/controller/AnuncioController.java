package br.upe.api.techcycle.marketplace.controller;

import br.upe.api.techcycle.marketplace.dominio.Anuncio;
import br.upe.api.techcycle.marketplace.dominio.Imagem;
import br.upe.api.techcycle.marketplace.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @GetMapping
    public ResponseEntity<List<Anuncio>> listar() {
        List<Anuncio> anuncios = anuncioService.listar();
        return ResponseEntity.ok(anuncios);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Anuncio>> listarPorNome(@RequestParam String nome) {
        List<Anuncio> anuncios = anuncioService.listarPorNome(nome);
        return ResponseEntity.ok(anuncios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anuncio> listarPorId(@PathVariable Long id) {
        Optional<Anuncio> anuncio = anuncioService.listarPorId(id);
        return anuncio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Anuncio> adicionar(@RequestBody Anuncio anuncio) {
        Anuncio novoAnuncio = anuncioService.adicionarAnuncio(anuncio);
        return ResponseEntity.ok(novoAnuncio);
    }

    @PostMapping("/{anuncioId}/imagens")
    public ResponseEntity<String> adicionarImagens(@PathVariable Long anuncioId, @RequestParam("files") List<MultipartFile> files) {
        try {
            anuncioService.adicionarImagens(anuncioId, files);
            return ResponseEntity.ok("Imagens adicionadas com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar imagens: " + e.getMessage());
        }
    }
}
