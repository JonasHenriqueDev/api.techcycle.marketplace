package br.upe.api.techcycle.marketplace.service;

import br.upe.api.techcycle.marketplace.dominio.Anuncio;
import br.upe.api.techcycle.marketplace.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    @Value("${app.upload.dir}") // Configuração do diretório de upload definido no application.properties
    private String uploadDir;

    public List<Anuncio> listar() {
        return anuncioRepository.findAll();
    }

    public List<Anuncio> listarPorNome(String nome) {
        return anuncioRepository.findByTituloContaining(nome);
    }

    public Optional<Anuncio> listarPorId(Long id) {
        return anuncioRepository.findById(id);
    }

    public Anuncio adicionarAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }

    public void adicionarImagens(Long anuncioId, List<MultipartFile> files) throws IOException {
        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(anuncioId);
        if (anuncioOptional.isPresent()) {
            Anuncio anuncio = anuncioOptional.get();
            List<String> nomesArquivos = new ArrayList<>();

            for (MultipartFile file : files) {
                String nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + nomeArquivo);
                Files.write(filePath, file.getBytes());
                nomesArquivos.add(nomeArquivo);
            }

            anuncio.setImagens(nomesArquivos);
            anuncioRepository.save(anuncio);
        } else {
            throw new RuntimeException("Anúncio não encontrado");
        }
    }
}
