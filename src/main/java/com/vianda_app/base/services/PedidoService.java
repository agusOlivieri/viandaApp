package com.vianda_app.base.services;

import com.vianda_app.base.controllers.PedidoSseController;
import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import com.vianda_app.base.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public List<Map<String, Object>> generarReporteMensual(int year, int month) {
        List<Pedido> pedidos = pedidoRepository.findPedidosDelMes(year, month);

        Map<String, Map<String, Map<Integer, Double>>> reporte = new HashMap<>();

        Map<String, Integer> pedidosPorDia = new HashMap<>();

        pedidos.sort(Comparator.comparing(Pedido::getFecha));

        for (Pedido pedido : pedidos) {
            String empleadoKey = pedido.getUsuario().getId() + "-" + pedido.getUsuario().getNombre() + "-" + pedido.getUsuario().getApellido();
            String proveedor = pedido.getVianda().getDistribuidora().getNombre();
            int dia = pedido.getFecha().getDayOfMonth();
            double precio = pedido.getVianda().getPrecio();

            String claveDiaEmpleado = empleadoKey + "-" + dia;

            int cantidadPedidos = pedidosPorDia.getOrDefault(claveDiaEmpleado, 0);

            double precioFinal = (cantidadPedidos == 0) ? precio * 0.5 : precio;

            pedidosPorDia.put(claveDiaEmpleado, cantidadPedidos + 1);

            reporte.putIfAbsent(empleadoKey, new HashMap<>());
            Map<String, Map<Integer,Double>> porProveedor = reporte.get(empleadoKey);

            porProveedor.putIfAbsent(proveedor, new HashMap<>());
            Map<Integer, Double> preciosPorDia = porProveedor.get(proveedor);

            preciosPorDia.put(dia, preciosPorDia.getOrDefault(dia, 0.0) + precioFinal);
        }

        List<Map<String, Object>> filas = new ArrayList<>();
        for(var entryEmpleado : reporte.entrySet()) {
            String empleado = entryEmpleado.getKey();
            for(var entryProveedor : entryEmpleado.getValue().entrySet()) {
                String proveedor = entryProveedor.getKey();
                Map<Integer, Double> preciosPorDia = entryProveedor.getValue();

                Map<String, Object> fila = new LinkedHashMap<>();
                fila.put("Legajo", empleado.split("-")[0]);
                fila.put("Nombre y Apellido", empleado.split("-")[1]);
                fila.put("Proveedor", proveedor);
                fila.put("Área", "Distribución");

                for (int i = 1; i <= 30; i++) {
                    fila.put(String.valueOf(i), preciosPorDia.getOrDefault(i, 0.0));
                }

                filas.add(fila);
            }
        }

        return filas;
    }

    public byte[] generarExcelMensual(int year, int month) throws IOException {
        List<Map<String, Object>> datos = generarReporteMensual(year, month);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pedidos del Mes");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Legajo", "Nombre y Apellido", "Proveedor", "Área"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
        for (int i = 1; i <= 30; i++) {
            headerRow.createCell(i + 3).setCellValue(i);
        }

        int rowIdx = 1;
        for (Map<String, Object> fila : datos) {
            Row row = sheet.createRow(rowIdx++);
            int colIdx = 0;
            for (Object value: fila.values()) {
                row.createCell(colIdx++).setCellValue(value.toString());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}
