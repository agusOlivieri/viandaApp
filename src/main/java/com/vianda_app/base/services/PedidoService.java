package com.vianda_app.base.services;

import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ViandaService viandaService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Pedido> getAll() { return pedidoRepository.findAll(); }

    @Transactional
    public Pedido create(Integer usuarioId, Integer viandaId, LocalDateTime fechaHora) {
        Usuario usuario = usuarioService.getById(usuarioId);
        Vianda vianda = viandaService.getById(viandaId);

        Pedido pedido = new Pedido(usuario, vianda, fechaHora);
        return pedidoRepository.save(pedido);
    }
}
