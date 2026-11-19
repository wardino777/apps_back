package com.auditoria.auditoria;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@Service
public class AuditoriaService {

    public record AuditoriaLogRequest(String usuarioCorreo, String rolUsuario, String accion, String detalle) {}
    public record AuditoriaRegistro(String timestamp, String usuarioCorreo, String rolUsuario, String accion, String detalle) {}

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Set<String> ROLES_VALIDOS = Set.of("Administrador", "Solicitante", "ADMIN", "USER");

    private final List<AuditoriaRegistro> logs = new CopyOnWriteArrayList<>();

    public AuditoriaRegistro registrarLog(AuditoriaLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("El registro de auditoría no puede ser nulo");
        }
        if (request.usuarioCorreo() == null || !EMAIL_REGEX.matcher(request.usuarioCorreo().trim()).matches()) {
            throw new IllegalArgumentException("El correo del usuario es obligatorio y válido");
        }
        if (request.rolUsuario() == null || !ROLES_VALIDOS.contains(request.rolUsuario().trim())) {
            throw new IllegalArgumentException("Rol no reconocido en auditoría: " + request.rolUsuario());
        }
        if (request.accion() == null || request.accion().trim().length() < 3) {
            throw new IllegalArgumentException("La acción de auditoría debe tener al menos 3 caracteres");
        }
        if (request.detalle() == null || request.detalle().isBlank()) {
            throw new IllegalArgumentException("El detalle de auditoría es obligatorio");
        }

        AuditoriaRegistro registro = new AuditoriaRegistro(
                LocalDateTime.now().toString(),
                request.usuarioCorreo().trim().toLowerCase(),
                request.rolUsuario().trim(),
                request.accion().trim().toUpperCase(),
                request.detalle().trim()
        );
        logs.add(registro);
        return registro;
    }

    public List<AuditoriaRegistro> obtenerLogs() {
        return List.copyOf(logs);
    }
}