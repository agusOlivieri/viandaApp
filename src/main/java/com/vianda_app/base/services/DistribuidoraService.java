package com.vianda_app.base.services;

import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.repositories.DistribuidoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistribuidoraService {
    @Autowired
    private DistribuidoraRepository distribuidoraRepository;

    public List<ViandaDistribuidora> getAll() { return distribuidoraRepository.findAll(); }
}
