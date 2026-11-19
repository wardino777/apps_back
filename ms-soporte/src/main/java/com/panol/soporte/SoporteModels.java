package com.panol.soporte;

public class SoporteModels {
    public record TicketSoporteRequest(String equipoId, String nombreEquipo, String descripcionFalla, String reportadoPor) {}
    public record TicketSoporteResponse(String ticketId, String estadoTicket, String mensaje) {}
}