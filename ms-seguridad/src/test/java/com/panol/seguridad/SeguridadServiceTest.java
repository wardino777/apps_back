package com.panol.seguridad;

import com.panol.seguridad.SeguridadModels.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeguridadServiceTest {

    private SeguridadService service;

    @BeforeEach
    void setUp() {
        service = new SeguridadService();
    }

    @Test
    void registrarAlerta_DatosValidos_GeneraCodigoSec() {
        AlertaSeguridadRequest req = new AlertaSeguridadRequest(
                "alumno@duocuc.cl",
                "RECUPERACION_PASSWORD",
                "Solicitud de restablecimiento de contraseña vía Firebase"
        );

        AlertaSeguridadResponse res = service.registrarAlerta(req);

        assertTrue(res.alertaId().startsWith("SEC-"));
        assertEquals("REGISTRADA", res.estado());
        assertEquals(1, service.listarAlertas().size());
    }

    @Test
    void registrarAlerta_CorreoInvalido_LanzaExcepcion() {
        AlertaSeguridadRequest req = new AlertaSeguridadRequest(
                "correo-sin-arroba",
                "LOGIN_FALLIDO",
                "Intento fallido de inicio de sesión"
        );

        assertThrows(IllegalArgumentException.class, () -> service.registrarAlerta(req));
    }

    @Test
    void registrarAlerta_TipoEventoNoPermitido_LanzaExcepcion() {
        AlertaSeguridadRequest req = new AlertaSeguridadRequest(
                "alumno@duocuc.cl",
                "EVENTO_INVENTADO",
                "Descripción válida de prueba"
        );

        assertThrows(IllegalArgumentException.class, () -> service.registrarAlerta(req));
    }

    @Test
    void registrarAlerta_DescripcionCorta_LanzaExcepcion() {
        AlertaSeguridadRequest req = new AlertaSeguridadRequest(
                "alumno@duocuc.cl",
                "BLOQUEO_CUENTA",
                "Test"
        );

        assertThrows(IllegalArgumentException.class, () -> service.registrarAlerta(req));
    }
}