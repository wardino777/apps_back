package com.panol.prestamos;

public class PrestamoModels {
    public record ValidacionUsuarioResponse(String correo, boolean habilitado, String motivoBloqueo) {}
    public record RegistroPrestamoRequest(String ticketId, String recursosSolicitados, String correoUsuario, String tipoMovimiento) {}
    public record MovimientoResponse(String estado, String mensaje) {}
    public record ErrorResponse(String error) {}
}