package com.panol.soporte;

import com.panol.soporte.SoporteModels.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@Service
public class SoporteService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final List<TicketSoporteResponse> tickets = new CopyOnWriteArrayList<>();

    public TicketSoporteResponse crearTicket(TicketSoporteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (request.equipoId() == null || request.equipoId().isBlank()) {
            throw new IllegalArgumentException("El ID del equipo/ticket es obligatorio");
        }
        if (request.nombreEquipo() == null || request.nombreEquipo().isBlank()) {
            throw new IllegalArgumentException("El nombre del equipo es obligatorio");
        }
        if (request.descripcionFalla() == null || request.descripcionFalla().trim().length() < 5) {
            throw new IllegalArgumentException("La descripción de la falla debe tener al menos 5 caracteres");
        }
        if (request.reportadoPor() == null || !EMAIL_REGEX.matcher(request.reportadoPor().trim()).matches()) {
            throw new IllegalArgumentException("El correo de quien reporta es obligatorio y válido");
        }

        String codigoTicket = "TCK-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        TicketSoporteResponse respuesta = new TicketSoporteResponse(
                codigoTicket,
                "ABIERTO",
                "Ticket registrado para " + request.nombreEquipo().trim()
        );
        tickets.add(respuesta);
        return respuesta;
    }

    public List<TicketSoporteResponse> listarTickets() {
        return List.copyOf(tickets);
    }
}