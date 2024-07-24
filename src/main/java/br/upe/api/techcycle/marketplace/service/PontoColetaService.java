package br.upe.api.techcycle.marketplace.service;


import br.upe.api.techcycle.marketplace.dominio.PontoColeta;
import br.upe.api.techcycle.marketplace.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PontoColetaService {

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    public List<PontoColeta> findAll() {
        return pontoColetaRepository.findAll();
    }

    public Optional<PontoColeta> findById(Long id) {
        return pontoColetaRepository.findById(id);
    }

    public PontoColeta save(PontoColeta pontoColeta) {
        return pontoColetaRepository.save(pontoColeta);
    }

    public void deleteById(Long id) {
        pontoColetaRepository.deleteById(id);
    }
}
