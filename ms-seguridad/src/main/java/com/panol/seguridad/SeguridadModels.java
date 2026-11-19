package com.panol.seguridad;

public class SeguridadModels {
    public record AlertaSeguridadRequest(String correoUsuario, String tipoEvento, String descripcion) {}
    public record AlertaSeguridadResponse(String alertaId, String estado, String mensaje) {}
}