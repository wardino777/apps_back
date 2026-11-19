package com.panol.prestamos;

import com.panol.prestamos.PrestamoModels.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@Service
public class PrestamoService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Map<String, String> sancionesActivas = new ConcurrentHashMap<>();
    private final List<RegistroPrestamoRequest> historial = new CopyOnWriteArrayList<>();

    public PrestamoService() {
        sancionesActivas.put("bloqueado@duocuc.cl", "Atraso pendiente en devolución de Notebook");
    }

    public ValidacionUsuarioResponse validarUsuario(String correo) {
        if (correo == null || !EMAIL_REGEX.matcher(correo.trim()).matches()) {
            throw new IllegalArgumentException("El correo proporcionado no tiene un formato válido");
        }
        String correoLimpio = correo.trim().toLowerCase();
        if (sancionesActivas.containsKey(correoLimpio)) {
            return new ValidacionUsuarioResponse(correoLimpio, false, sancionesActivas.get(correoLimpio));
        }
        return new ValidacionUsuarioResponse(correoLimpio, true, null);
    }

    public MovimientoResponse registrarMovimiento(RegistroPrestamoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de movimiento no puede ser nula");
        }
        if (request.ticketId() == null || request.ticketId().isBlank()) {
            throw new IllegalArgumentException("El ID del ticket es obligatorio");
        }
        if (request.recursosSolicitados() == null || request.recursosSolicitados().isBlank()) {
            throw new IllegalArgumentException("Debe especificar al menos un recurso solicitado");
        }
        if (request.tipoMovimiento() == null ||
                (!request.tipoMovimiento().equalsIgnoreCase("PRESTAMO") && !request.tipoMovimiento().equalsIgnoreCase("DEVOLUCION"))) {
            throw new IllegalArgumentException("El tipoMovimiento debe ser PRESTAMO o DEVOLUCION");
        }

        ValidacionUsuarioResponse validacion = validarUsuario(request.correoUsuario());
        if (!validacion.habilitado() && "PRESTAMO".equalsIgnoreCase(request.tipoMovimiento())) {
            throw new IllegalStateException("Usuario sancionado: " + validacion.motivoBloqueo());
        }

        historial.add(request);
        return new MovimientoResponse(
                "OK",
                "Movimiento " + request.tipoMovimiento().toUpperCase() + " registrado para ticket " + request.ticketId()
        );
    }

    public List<RegistroPrestamoRequest> obtenerHistorial() {
        return List.copyOf(historial);
    }
}