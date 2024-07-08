package br.upe.api.techcycle.marketplace.unidade.service;

import br.upe.api.techcycle.marketplace.dominio.Anuncio;
import br.upe.api.techcycle.marketplace.repository.AnuncioRepository;
import br.upe.api.techcycle.marketplace.service.AnuncioService;
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

class AnuncioServiceTest {

    @InjectMocks
    private AnuncioService anuncioService;

    @Mock
    private AnuncioRepository anuncioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar() {
        Anuncio anuncio1 = new Anuncio();
        anuncio1.setId(1L);
        anuncio1.setTitulo("TV de tubo que não liga");
        anuncio1.setDescricao("TV de tubo parada que não liga, mas pode ser usada para retirada de peças.\u2028\u2028Interessados, mandem mensagem.");
        anuncio1.setPreco(40.00);

        Anuncio anuncio2 = new Anuncio();
        anuncio2.setId(2L);
        anuncio2.setTitulo("Celular Nokia");
        anuncio2.setDescricao("Celular antigo da marca Nokia, em bom estado de conservação.");
        anuncio2.setPreco(80.00);

        when(anuncioRepository.findAll()).thenReturn(Arrays.asList(anuncio1, anuncio2));

        List<Anuncio> anuncios = anuncioService.listar();

        assertEquals(2, anuncios.size());
        verify(anuncioRepository, times(1)).findAll();
    }

    @Test
    void listarPorNome() {
        String nome = "TV";
        Anuncio anuncio1 = new Anuncio();
        anuncio1.setId(1L);
        anuncio1.setTitulo("TV de tubo que não liga");
        anuncio1.setDescricao("TV de tubo parada que não liga, mas pode ser usada para retirada de peças.\u2028\u2028Interessados, mandem mensagem.");
        anuncio1.setPreco(40.00);

        when(anuncioRepository.findByTituloContaining(nome)).thenReturn(List.of(anuncio1));

        List<Anuncio> anuncios = anuncioService.listarPorNome(nome);

        assertEquals(1, anuncios.size());
        assertEquals(anuncio1.getTitulo(), anuncios.get(0).getTitulo());
        verify(anuncioRepository, times(1)).findByTituloContaining(nome);
    }

    @Test
    void listarPorId() {
        Long id = 1L;
        Anuncio anuncio = new Anuncio();
        anuncio.setId(id);
        anuncio.setTitulo("Anúncio Teste");

        when(anuncioRepository.findById(id)).thenReturn(Optional.of(anuncio));

        Optional<Anuncio> resultado = anuncioService.listarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals(anuncio.getTitulo(), resultado.get().getTitulo());
        verify(anuncioRepository, times(1)).findById(id);
    }

    @Test
    void adicionarAnuncio() {
        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo("Novo Anúncio");

        when(anuncioRepository.save(any(Anuncio.class))).thenReturn(anuncio);

        Anuncio novoAnuncio = anuncioService.adicionarAnuncio(anuncio);

        assertEquals(anuncio.getTitulo(), novoAnuncio.getTitulo());
        verify(anuncioRepository, times(1)).save(anuncio);
    }

    @Test
    void adicionarImagens() {
    }
}
