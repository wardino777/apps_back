package com.panol.prestamos;

import com.panol.prestamos.PrestamoModels.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrestamoServiceTest {

    private PrestamoService service;

    @BeforeEach
    void setUp() {
        service = new PrestamoService();
    }

    @Test
    void validarUsuario_Habilitado_RetornaTrue() {
        ValidacionUsuarioResponse res = service.validarUsuario("estudiante@duocuc.cl");
        assertTrue(res.habilitado());
        assertNull(res.motivoBloqueo());
    }

    @Test
    void validarUsuario_Sancionado_RetornaFalse() {
        ValidacionUsuarioResponse res = service.validarUsuario("bloqueado@duocuc.cl");
        assertFalse(res.habilitado());
        assertNotNull(res.motivoBloqueo());
    }

    @Test
    void registrarMovimiento_DatosInvalidos_LanzaExcepcion() {
        RegistroPrestamoRequest req = new RegistroPrestamoRequest("", "Notebook", "test@duocuc.cl", "PRESTAMO");
        assertThrows(IllegalArgumentException.class, () -> service.registrarMovimiento(req));
    }
}