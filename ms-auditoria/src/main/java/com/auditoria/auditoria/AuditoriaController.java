package com.auditoria.auditoria;

import com.auditoria.auditoria.AuditoriaService.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping("/logs")
    public ResponseEntity<Void> guardarLog(@RequestBody AuditoriaLogRequest request) {
        auditoriaService.registrarLog(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditoriaRegistro>> verLogs() {
        return ResponseEntity.ok(auditoriaService.obtenerLogs());
    }

    public record ErrorResponse(String error) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}