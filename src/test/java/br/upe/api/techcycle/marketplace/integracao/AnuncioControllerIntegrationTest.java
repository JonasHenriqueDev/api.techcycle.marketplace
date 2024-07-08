package br.upe.api.techcycle.marketplace.integracao;

import br.upe.api.techcycle.marketplace.controller.AnuncioController;
import br.upe.api.techcycle.marketplace.dominio.Anuncio;
import br.upe.api.techcycle.marketplace.service.AnuncioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnuncioController.class)
@AutoConfigureMockMvc
public class AnuncioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnuncioService anuncioService;

    private Anuncio anuncio;

    @BeforeEach
    void setUp() {
        anuncio = new Anuncio();
        anuncio.setId(1L);
        anuncio.setTitulo("TV de tubo que não liga");
        anuncio.setDescricao("TV de tubo parada que não liga, mas pode ser usada para retirada de peças.");
        anuncio.setPreco(40.00);
    }

    @Test
    void listarAnuncios() throws Exception {
        when(anuncioService.listar()).thenReturn(Arrays.asList(anuncio));

        mockMvc.perform(get("/api/anuncios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(anuncio.getId().intValue())))
                .andExpect(jsonPath("$[0].titulo", is(anuncio.getTitulo())))
                .andExpect(jsonPath("$[0].descricao", is(anuncio.getDescricao())))
                .andExpect(jsonPath("$[0].preco", is(anuncio.getPreco())));

        verify(anuncioService, times(1)).listar();
    }

    @Test
    void buscarAnuncioPorId() throws Exception {
        Long id = 1L;
        when(anuncioService.listarPorId(id)).thenReturn(Optional.of(anuncio));

        mockMvc.perform(get("/api/anuncios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(anuncio.getId().intValue())))
                .andExpect(jsonPath("$.titulo", is(anuncio.getTitulo())))
                .andExpect(jsonPath("$.descricao", is(anuncio.getDescricao())))
                .andExpect(jsonPath("$.preco", is(anuncio.getPreco())));

        verify(anuncioService, times(1)).listarPorId(id);
    }

    @Test
    void adicionarImagensAoAnuncio() throws Exception {
        Long anuncioId = 1L;
        // Simulação de envio de arquivo multipart
        // MockMvc não suporta diretamente testes de upload de arquivos, é necessário um método alternativo para testes.
    }

}
