package com.vianda_app.base.services;

import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.repositories.DistribuidoraRepository;
import com.vianda_app.base.repositories.ViandaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViandaService {
    @Autowired
    private ViandaRepository viandaRepository;

    @Autowired
    private DistribuidoraService distribuidoraService;

    public List<Vianda> getAll() { return viandaRepository.findAll(); }

    public Vianda getById(Integer viandaId) {
        return viandaRepository.findById(viandaId).orElseThrow(() -> new RuntimeException("Vianda no encontrada."));
    }

    @Transactional
    public Vianda create(String nombre, String desc, Integer precio, String distribuidoraNombre) {
        ViandaDistribuidora distribuidora = distribuidoraService.getByNombre(distribuidoraNombre);

        Vianda vianda = new Vianda(nombre, desc, precio, distribuidora);
        return viandaRepository.save(vianda);
    }
}
