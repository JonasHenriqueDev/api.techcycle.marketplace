package br.upe.api.techcycle.marketplace.integracao;

import br.upe.api.techcycle.marketplace.controller.RecompensaController;
import br.upe.api.techcycle.marketplace.dominio.Recompensa;
import br.upe.api.techcycle.marketplace.service.RecompensaService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecompensaController.class)
@AutoConfigureMockMvc
public class RecompensaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecompensaService recompensaService;

    private Recompensa recompensa;

    @BeforeEach
    void setUp() {
        recompensa = new Recompensa();
        recompensa.setId(1L);
        recompensa.setTitulo("Cupom de 20% do iFood");
        recompensa.setDescricao("Cupom de desconto de 20% válido em pedidos acima de R$ 50,00.");
    }

    @Test
    void listarRecompensas() throws Exception {
        when(recompensaService.listar()).thenReturn(Arrays.asList(recompensa));

        mockMvc.perform(get("/api/recompensa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(recompensa.getId().intValue())))
                .andExpect(jsonPath("$[0].titulo", is(recompensa.getTitulo())))
                .andExpect(jsonPath("$[0].descricao", is(recompensa.getDescricao())));

        verify(recompensaService, times(1)).listar();
    }

    @Test
    void buscarRecompensaPorId() throws Exception {
        Long id = 1L;
        when(recompensaService.listarPorId(id)).thenReturn(Optional.of(recompensa));

        mockMvc.perform(get("/api/recompensa/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(recompensa.getId().intValue())))
                .andExpect(jsonPath("$.titulo", is(recompensa.getTitulo())))
                .andExpect(jsonPath("$.descricao", is(recompensa.getDescricao())));

        verify(recompensaService, times(1)).listarPorId(id);
    }

    @Test
    void adicionarRecompensa() throws Exception {
        Recompensa novaRecompensa = new Recompensa();
        novaRecompensa.setId(2L); // Simula o ID gerado após a adição
        novaRecompensa.setTitulo("Novo Cupom de 15% do Uber Eats");
        novaRecompensa.setDescricao("Cupom de desconto de 15% válido em todos os restaurantes parceiros do Uber Eats.");

        when(recompensaService.adicionarRecompensa(any(Recompensa.class))).thenReturn(novaRecompensa);

        mockMvc.perform(post("/api/recompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"titulo\": \"Novo Cupom de 15% do Uber Eats\", \"descricao\": \"Cupom de desconto de 15% válido em todos os restaurantes parceiros do Uber Eats.\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue()))) // Verifica se o ID não é nulo
                .andExpect(jsonPath("$.titulo", is(novaRecompensa.getTitulo())))
                .andExpect(jsonPath("$.descricao", is(novaRecompensa.getDescricao())));

        verify(recompensaService, times(1)).adicionarRecompensa(any(Recompensa.class));
    }
}
