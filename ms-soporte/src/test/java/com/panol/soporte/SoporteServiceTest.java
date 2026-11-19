package com.panol.soporte;

import com.panol.soporte.SoporteModels.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SoporteServiceTest {

    private SoporteService service;

    @BeforeEach
    void setUp() {
        service = new SoporteService();
    }

    @Test
    void crearTicket_DatosValidos_GeneraCodigoTck() {
        TicketSoporteRequest req = new TicketSoporteRequest("EQ-01", "Proyector Epson", "No enciende la lámpara", "alumno@duocuc.cl");
        TicketSoporteResponse res = service.crearTicket(req);
        assertTrue(res.ticketId().startsWith("TCK-"));
        assertEquals("ABIERTO", res.estadoTicket());
    }

    @Test
    void crearTicket_DescripcionCorta_LanzaExcepcion() {
        TicketSoporteRequest req = new TicketSoporteRequest("EQ-01", "Proyector", "Malo", "alumno@duocuc.cl");
        assertThrows(IllegalArgumentException.class, () -> service.crearTicket(req));
    }
}