package com.vianda_app.base.controllers;

import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.services.ViandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class viandaController {
    @Autowired
    private ViandaService viandaService;

    @GetMapping("/viandas")
    public List<Vianda> getAllViandas() {
        return viandaService.getAll();
    }
}
