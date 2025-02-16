package com.vianda_app.base.services;

import com.vianda_app.base.controllers.PedidoSseController;
import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ViandaService viandaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DistribuidoraService distribuidoraService;

    @Autowired
    private PedidoSseController pedidoSseController;

    public List<Pedido> getAll() { return pedidoRepository.findAll(); }

    @Transactional
    public Pedido create(Integer usuarioId, Integer viandaId, LocalDateTime fechaHora) {
        Usuario usuario = usuarioService.getById(usuarioId);
        Vianda vianda = viandaService.getById(viandaId);

        Pedido pedido = new Pedido(usuario, vianda, fechaHora);
        Pedido savedPedido = pedidoRepository.save(pedido);

        pedidoSseController.enviarPedido(savedPedido);

        return savedPedido;
    }

    public List<Pedido> getAllByDistribuidora(String distribuidoraNombre) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);

        return pedidoRepository.findPedidosDelDiaByDistribuidora(distribuidoraNombre, inicioDia, finDia);
    }

    public byte[] generarRemitoCSV(String distribuidoraNombre) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);

        List<Pedido> pedidos = pedidoRepository.findPedidosDelDiaByDistribuidora(distribuidoraNombre, inicioDia, finDia);

        StringBuilder csv = new StringBuilder();
        csv.append("LEGAJO,NOMBRE,VIANDA,FECHA\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Pedido pedido : pedidos) {
            csv.append(pedido.getId()).append(",");
            csv.append(pedido.getUsuario().getNombre()).append(",");
            csv.append(pedido.getVianda().getNombre()).append(",");
            csv.append(pedido.getFecha().format(formatter)).append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }
}
