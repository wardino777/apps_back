package com.auditoria.auditoria;

import com.auditoria.auditoria.AuditoriaService.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuditoriaServiceTest {

    private AuditoriaService service;

    @BeforeEach
    void setUp() {
        service = new AuditoriaService();
    }

    @Test
    void registrarLog_DatosValidos_GuardaRegistro() {
        AuditoriaLogRequest req = new AuditoriaLogRequest("admin@duocuc.cl", "Administrador", "NUEVO_EQUIPO", "Notebook Dell");
        AuditoriaRegistro res = service.registrarLog(req);
        assertEquals("NUEVO_EQUIPO", res.accion());
        assertEquals(1, service.obtenerLogs().size());
    }

    @Test
    void registrarLog_RolInvalido_LanzaExcepcion() {
        AuditoriaLogRequest req = new AuditoriaLogRequest("admin@duocuc.cl", "Invitado", "LOGIN", "Detalle");
        assertThrows(IllegalArgumentException.class, () -> service.registrarLog(req));
    }
}