package com.vianda_app.base.services;

import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> getAll() { return pedidoRepository.findAll(); }
}
