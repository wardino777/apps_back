package com.panol.seguridad;

import com.panol.seguridad.SeguridadModels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seguridad")
@CrossOrigin(origins = "*")
public class SeguridadController {

    private final SeguridadService seguridadService;

    public SeguridadController(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

    @PostMapping("/alertas")
    public ResponseEntity<AlertaSeguridadResponse> crearAlerta(@RequestBody AlertaSeguridadRequest request) {
        return ResponseEntity.ok(seguridadService.registrarAlerta(request));
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<AlertaSeguridadResponse>> obtenerAlertas() {
        return ResponseEntity.ok(seguridadService.listarAlertas());
    }

    public record ErrorResponse(String error) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}