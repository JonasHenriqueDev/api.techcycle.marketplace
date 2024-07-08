package br.upe.api.techcycle.marketplace.unidade.service;

import br.upe.api.techcycle.marketplace.dominio.Recompensa;
import br.upe.api.techcycle.marketplace.repository.RecompensaRepository;
import br.upe.api.techcycle.marketplace.service.RecompensaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecompensaServiceTest {

    @InjectMocks
    private RecompensaService recompensaService;

    @Mock
    private RecompensaRepository recompensaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarRecompensas() {
        Recompensa recompensa1 = new Recompensa();
        recompensa1.setId(1L);
        recompensa1.setTitulo("Cupom de 20% do iFood");
        recompensa1.setDescricao("Cupom de desconto de 20% válido para pedidos no iFood.");

        Recompensa recompensa2 = new Recompensa();
        recompensa2.setId(2L);
        recompensa2.setTitulo("Cupom de frete grátis");
        recompensa2.setDescricao("Cupom de frete grátis em qualquer compra.");

        when(recompensaRepository.findAll()).thenReturn(Arrays.asList(recompensa1, recompensa2));

        List<Recompensa> recompensas = recompensaService.listar();

        assertEquals(2, recompensas.size());
        verify(recompensaRepository, times(1)).findAll();
    }

    @Test
    void buscarRecompensaPorId() {
        Long id = 1L;
        Recompensa recompensa = new Recompensa();
        recompensa.setId(id);
        recompensa.setTitulo("Cupom de 20% do iFood");
        recompensa.setDescricao("Cupom de desconto de 20% válido para pedidos no iFood.");

        when(recompensaRepository.findById(id)).thenReturn(Optional.of(recompensa));

        Optional<Recompensa> resultado = recompensaService.listarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals(recompensa.getTitulo(), resultado.get().getTitulo());
        assertEquals(recompensa.getDescricao(), resultado.get().getDescricao());
        verify(recompensaRepository, times(1)).findById(id);
    }

    @Test
    void adicionarRecompensa() {
        Recompensa recompensa = new Recompensa();
        recompensa.setTitulo("Cupom de 20% do iFood");
        recompensa.setDescricao("Cupom de desconto de 20% válido para pedidos no iFood.");

        when(recompensaRepository.save(any(Recompensa.class))).thenReturn(recompensa);

        Recompensa novaRecompensa = recompensaService.adicionarRecompensa(recompensa);

        assertEquals(recompensa.getTitulo(), novaRecompensa.getTitulo());
        assertEquals(recompensa.getDescricao(), novaRecompensa.getDescricao());
        verify(recompensaRepository, times(1)).save(recompensa);
    }
}
