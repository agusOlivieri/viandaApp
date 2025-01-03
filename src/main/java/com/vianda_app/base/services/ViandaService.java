package com.vianda_app.base.services;

import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.repositories.ViandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViandaService {
    @Autowired
    private ViandaRepository viandaRepository;

    public List<Vianda> getAll() { return viandaRepository.findAll(); }
}
