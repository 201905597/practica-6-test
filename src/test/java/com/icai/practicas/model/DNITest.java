package com.icai.practicas.model;

import com.icai.practicas.model.DNI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=DNITest.class)
public class DNITest {

    private DNI dni;

    @Test
    public void given_dni_when_valid_then_ok(){

        // Casuística (given)
        dni = new DNI("54366490X"); // DNI válido

        // Implementación de la función (when)
        boolean res = dni.validar();

        // Comprobación (then)
        assertEquals(res,true);
    }

    @Test
    public void given_dni_when_binarysearch_then_not_ok(){

        // Casuística: DNI incluido en el array de los no válidos
        dni = new DNI("00000001R"); // DNI inválido

        // Implementación de la función
        boolean res = dni.validar();

        // Comprobación (asserts)
        assertEquals(res,false);
    }

    @Test
    public void given_dni_when_not_pattern_then_not_ok(){

        // Casuística
        dni = new DNI("5236678B");  // DNI inválido (no sigue el patrón; tiene un número menos)

        // Implementación de la función
        boolean res = dni.validar();

        // Comprobación
        assertEquals(res,false);
    }

    @Test
    public void given_dni_when_digitocontrol_invalid_then_not_ok(){

        // Casuística
        dni = new DNI("54366491R"); // DNI inválido

        // Implementación de la función
        boolean res = dni.validar();

        // Comprobación
        assertEquals(res,false);
    }
}
