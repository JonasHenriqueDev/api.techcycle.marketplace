package br.upe.api.techcycle.marketplace.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String tipo;

    @Lob
    private byte[] dados;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;
}
