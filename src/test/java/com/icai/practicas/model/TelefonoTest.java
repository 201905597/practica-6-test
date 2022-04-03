package com.icai.practicas.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TelefonoTest.class)
public class TelefonoTest {

    @Test
    public void given_telefono_when_valid_then_ok(){

        // Casuística
        Telefono telefono = new Telefono("628197116"); // Teléfono válido

        // Implementación de la función
        boolean res = telefono.validar();

        // Comprobación
        assertEquals(res,true);
    }

    @Test
    public void given_telefono_pattern_not_valid_then_not_ok(){

        // Casuística
        Telefono telefono = new Telefono("62819711c"); // Teléfono no válido (un carácter no numérico)

        // Implementación de la función
        boolean res = telefono.validar();

        // Comprobación
        assertEquals(res,false);
    }
}
