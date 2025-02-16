package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.PedidoDTO;
import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAll();
    }

    @PostMapping("/new")
    public ResponseEntity<Object> createPedido(@RequestBody PedidoDTO request) {
        try {
            Pedido pedido = pedidoService.create(request.getUsuarioId(), request.getViandaId(), request.getFechaHora());
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "No se pudo crear el pedido");
            errorResponse.put("Detalle", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @GetMapping("/{distribuidora}")
    public ResponseEntity<List<Pedido>> getAllPedidosDelDiaFromDistribuidora(@PathVariable String distribuidora) {
        List<Pedido> pedidos = pedidoService.getAllByDistribuidora(distribuidora);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/remitos/csv")
    public ResponseEntity<byte[]> generarRemitoCSV(@RequestParam String distribuidora) {
        byte[] csvBytes = pedidoService.generarRemitoCSV(distribuidora);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.attachment().filename("Remito_Pedidos.csv").build());

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
