package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.PedidoDTO;
import com.vianda_app.base.dtos.ViandaDTO;
import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.services.DistribuidoraService;
import com.vianda_app.base.services.UsuarioService;
import com.vianda_app.base.services.ViandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/new")
    public ResponseEntity<Object> createVianda(@RequestBody ViandaDTO request) {
        try {
            Vianda vianda = viandaService.create(request.getNombre(), request.getDescripcion(), request.getPrecio(), request.getDistribuidora());
            return ResponseEntity.status(HttpStatus.CREATED).body(vianda);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "No se pudo crear la vianda");
            errorResponse.put("Detalle", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateVianda(@RequestBody ViandaDTO request) {
        try {
            Vianda vianda = viandaService.update();
            return ResponseEntity.status(HttpStatus.OK).body(vianda);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "No se pudo actualizar la vianda");
            errorResponse.put("Detalle", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAll();
    }

}
