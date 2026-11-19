package com.panol.seguridad;

import com.panol.seguridad.SeguridadModels.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@Service
public class SeguridadService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Set<String> EVENTOS_PERMITIDOS = Set.of("RECUPERACION_PASSWORD", "LOGIN_FALLIDO", "BLOQUEO_CUENTA");

    private final List<AlertaSeguridadResponse> alertas = new CopyOnWriteArrayList<>();

    public AlertaSeguridadResponse registrarAlerta(AlertaSeguridadRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo");
        }
        if (request.correoUsuario() == null || !EMAIL_REGEX.matcher(request.correoUsuario().trim()).matches()) {
            throw new IllegalArgumentException("El correo del usuario es obligatorio y debe tener formato válido");
        }
        if (request.tipoEvento() == null || !EVENTOS_PERMITIDOS.contains(request.tipoEvento().trim().toUpperCase())) {
            throw new IllegalArgumentException("Tipo de evento no válido. Permitidos: " + EVENTOS_PERMITIDOS);
        }
        if (request.descripcion() == null || request.descripcion().trim().length() < 5) {
            throw new IllegalArgumentException("La descripción debe tener al menos 5 caracteres");
        }

        String id = "SEC-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        AlertaSeguridadResponse response = new AlertaSeguridadResponse(
                id,
                "REGISTRADA",
                "Evento " + request.tipoEvento().toUpperCase() + " auditado para " + request.correoUsuario().trim().toLowerCase()
        );
        alertas.add(response);
        return response;
    }

    public List<AlertaSeguridadResponse> listarAlertas() {
        return List.copyOf(alertas);
    }
}