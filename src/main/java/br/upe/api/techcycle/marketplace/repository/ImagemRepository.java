package br.upe.api.techcycle.marketplace.repository;

import br.upe.api.techcycle.marketplace.dominio.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
