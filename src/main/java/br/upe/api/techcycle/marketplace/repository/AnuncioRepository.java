package br.upe.api.techcycle.marketplace.repository;

import br.upe.api.techcycle.marketplace.dominio.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByTituloContaining(String titulo);
}
