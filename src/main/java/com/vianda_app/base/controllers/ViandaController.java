package com.vianda_app.base.controllers;

import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.services.DistribuidoraService;
import com.vianda_app.base.services.UsuarioService;
import com.vianda_app.base.services.ViandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/viandas")
public class ViandaController {
    @Autowired
    private ViandaService viandaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DistribuidoraService distribuidoraService;

    @GetMapping
    public List<Vianda> getAllViandas() {
        return viandaService.getAll();
    }

    @GetMapping("/distribuidoras")
    public List<ViandaDistribuidora> getAllDistribuidoras() {
        return distribuidoraService.getAll();
    }

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAll();
    }

}
