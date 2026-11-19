package com.panol.soporte;

import com.panol.soporte.SoporteModels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/soporte")
@CrossOrigin(origins = "*")
public class SoporteController {

    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketSoporteResponse> crearTicket(@RequestBody TicketSoporteRequest request) {
        return ResponseEntity.ok(soporteService.crearTicket(request));
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketSoporteResponse>> obtenerTickets() {
        return ResponseEntity.ok(soporteService.listarTickets());
    }

    public record ErrorResponse(String error) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}