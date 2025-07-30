package test;

import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorBiblioteca;
import Controllers.Operadores.Metodos.ControladorGeneral;
import Models.Bibliotecario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorBibliotecaTest {

    private Bibliotecario bibliotecario;

    @BeforeEach
    void setup() {
        bibliotecario = Bibliotecario.builder()
                .ID(1)
                .Usuario("admin")
                .Contrasena("admin123")
                .build();
    }

    @Test
    void TC01_testCrearBibliotecario() {
        System.out.println("TC01 - Crear bibliotecario");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.crearEntidad(bibliotecario, E_ROL._BIBLIOTECARIO))
                    .thenReturn(true);

            boolean resultado = ControladorBiblioteca.crearBibliotecario(bibliotecario);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC02_testValidarBibliotecario() {
        System.out.println("TC02 - Validar bibliotecario (login)");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Usuario", "admin", "Contrasena", "admin123")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.validarEntidad(bibliotecario, E_ROL._BIBLIOTECARIO))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorBiblioteca.validarBibliotecario(bibliotecario);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }
}
