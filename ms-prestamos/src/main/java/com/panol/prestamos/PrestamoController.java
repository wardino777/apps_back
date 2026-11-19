package com.panol.prestamos;

import com.panol.prestamos.PrestamoModels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/validar/{correo}")
    public ResponseEntity<ValidacionUsuarioResponse> validar(@PathVariable String correo) {
        return ResponseEntity.ok(prestamoService.validarUsuario(correo));
    }

    @PostMapping("/registrar")
    public ResponseEntity<MovimientoResponse> registrar(@RequestBody RegistroPrestamoRequest request) {
        return ResponseEntity.ok(prestamoService.registrarMovimiento(request));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> manejarErrores(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}