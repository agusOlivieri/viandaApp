package com.vianda_app.base.controllers;

import com.vianda_app.base.entities.Pedido;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoSseController {

    private final CopyOnWriteArrayList<SseEmitter> emisores = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPedidos() {
        SseEmitter emisor = new SseEmitter(Long.MAX_VALUE);
        emisores.add(emisor);

        emisor.onCompletion(() -> emisores.remove(emisor));
        emisor.onTimeout(() -> emisores.remove(emisor));

        return emisor;
    }

    public void enviarPedido(Pedido pedido) {
        emisores.forEach(emisor -> {
            try {
                emisor.send(pedido);
            } catch (IOException e) {
                emisor.complete();
                emisores.remove(emisor);
            }
        });
    }
}
