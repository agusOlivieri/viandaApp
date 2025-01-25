package com.vianda_app.base.services;

import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.repositories.DistribuidoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistribuidoraService {
    @Autowired
    private DistribuidoraRepository distribuidoraRepository;

    public List<ViandaDistribuidora> getAll() { return distribuidoraRepository.findAll(); }

    public ViandaDistribuidora getByNombre(String nombre) {
        return distribuidoraRepository.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Distribuidora no encontrada."));
    }
}
