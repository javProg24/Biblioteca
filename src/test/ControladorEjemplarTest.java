package test;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Controllers.Operadores.Metodos.ControladorEjemplar;
import main.java.Controllers.Operadores.Metodos.ControladorGeneral;
import main.java.Models.Ejemplar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorEjemplarTest {

    private Ejemplar ejemplar;

    @BeforeEach
    void setup() {
        ejemplar = new Ejemplar(1, "EJ-001", true, 10);
    }

    @Test
    void TC01_testCrearEjemplar() {
        System.out.println("TC01 - Crear ejemplar");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.crearEntidad(ejemplar, E_ROL._EJEMPLAR))
                    .thenReturn(true);

            boolean resultado = ControladorEjemplar.crearEjemplar(ejemplar);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC02_testActualizarEstadoEjemplar() {
        System.out.println("TC02 - Actualizar estado del ejemplar");

        ejemplar.setEstado(false); // Simulamos un cambio de estado

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.actualizarEntidadParametro(
                            eq(ejemplar), eq(E_ROL._EJEMPLAR), eq(E_PARAMETRO._ESTADO), anyList()))
                    .thenReturn(true);

            boolean resultado = ControladorEjemplar.actualizarEstadoEjemplar(ejemplar);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC03_testEliminarEjemplar() {
        System.out.println("TC03 - Eliminar ejemplar");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.eliminarEntidad(eq(ejemplar), eq(E_ROL._EJEMPLAR), anyList()))
                    .thenReturn(true);

            boolean resultado = ControladorEjemplar.eliminarEjemplar(ejemplar);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC04_testObtenerEjemplares() {
        System.out.println("TC04 - Obtener todos los ejemplares");

        List<Map<String, Object>> ejemplaresEsperados = List.of(
                Map.of("ID", 1, "Codigo_Interno", "EJ-001", "Estado", true),
                Map.of("ID", 2, "Codigo_Interno", "EJ-002", "Estado", false)
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidad(E_ROL._EJEMPLAR))
                    .thenReturn(ejemplaresEsperados);

            List<Map<String, Object>> resultado = ControladorEjemplar.obtenerEjemplares();
            System.out.println("Resultado esperado: " + ejemplaresEsperados);
            System.out.println("Resultado real: " + resultado);
            assertEquals(ejemplaresEsperados, resultado);
        }
    }

    @Test
    void TC05_testObtenerEjemplarPorID() {
        System.out.println("TC05 - Obtener ejemplar por ID");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Codigo_Interno", "EJ-001", "Estado", true)
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(ejemplar), eq(E_ROL._EJEMPLAR), eq(E_PARAMETRO._ID), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorEjemplar.obtenerEjemplarID(ejemplar);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }

    @Test
    void TC06_testObtenerEjemplarPorLibroID() {
        System.out.println("TC06 - Obtener ejemplares por ID de libro");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "ID_Libro", 10, "Codigo_Interno", "EJ-001")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(ejemplar), eq(E_ROL._EJEMPLAR), eq(E_PARAMETRO._LIBRO_ID), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorEjemplar.obtenerEjemplarLibroID(ejemplar);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }
}
