package com.vianda_app.base.dtos;

import java.time.LocalDateTime;

public class PedidoDTO {
    private Integer usuarioId;

    private Integer viandaId;

    private LocalDateTime fechaHora;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getViandaId() {
        return viandaId;
    }

    public void setViandaId(Integer viandaId) {
        this.viandaId = viandaId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
