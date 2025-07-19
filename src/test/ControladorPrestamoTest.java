package test;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Controllers.Operadores.Metodos.ControladorGeneral;
import main.java.Controllers.Operadores.Metodos.ControladorPrestamo;
import main.java.Models.Prestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorPrestamoTest {

    private Prestamo prestamoMock;

    @BeforeEach
    void setup() {
        prestamoMock = mock(Prestamo.class);

        // Simulamos valores realistas
        when(prestamoMock.getID()).thenReturn(1);
        when(prestamoMock.getFechaPrestamo()).thenReturn(new Date(1720800000000L)); // 2024-07-12
        when(prestamoMock.getID_Usuario()).thenReturn(101);
        when(prestamoMock.getID_Ejemplar()).thenReturn(202);
        when(prestamoMock.getEstado()).thenReturn(true);
    }
    @Test
    void TC01_testCrearPrestamo() {
        System.out.println("TC01 - Crear préstamo");

        Prestamo prestamo = new Prestamo(1, new Date(), null, true, 101, 202);

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.crearEntidad(prestamo, E_ROL._PRESTAMO))
                    .thenReturn(true);

            boolean resultado = ControladorPrestamo.crearPrestamo(prestamo);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

        @Test
        void TC02_testActualizarPrestamo() {
            System.out.println("TC02 - Actualizar préstamo");

            Prestamo prestamo = new Prestamo(1, new Date(), null, false, 105, 210);

            try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
                mock.when(() -> ControladorGeneral.actualizarEntidad(prestamo, E_ROL._PRESTAMO))
                        .thenReturn(true);

                boolean resultado = ControladorPrestamo.actualizarPrestamo(prestamo);
                System.out.println("Resultado esperado: true, Resultado real: " + resultado);
                assertTrue(resultado);
            }
        }

        @Test
        void TC03_testEliminarPrestamo() {
            System.out.println("TC03 - Eliminar préstamo");

            Prestamo prestamo = new Prestamo();
            prestamo.setID(1);

            try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
                mock.when(() -> ControladorGeneral.eliminarEntidad(eq(prestamo), eq(E_ROL._PRESTAMO), anyList()))
                        .thenReturn(true);

                boolean resultado = ControladorPrestamo.eliminarPrestamo(prestamo);
                System.out.println("Resultado esperado: true, Resultado real: " + resultado);
                assertTrue(resultado);
            }
        }

        @Test
        void TC04_testObtenerPrestamos() {
            System.out.println("TC04 - Obtener lista de préstamos");

            List<Map<String, Object>> prestamosEsperados = List.of(
                    Map.of("ID", 1, "Cliente", "Carlos", "Monto", 5000)
            );

            try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
                mock.when(() -> ControladorGeneral.obtenerEntidad(E_ROL._PRESTAMO))
                        .thenReturn(prestamosEsperados);

                List<Map<String, Object>> resultado = ControladorPrestamo.obtenerPrestamos();
                System.out.println("Resultado esperado: " + prestamosEsperados);
                System.out.println("Resultado real: " + resultado);
                assertEquals(prestamosEsperados, resultado);
            }
        }

        @Test
        void TC05_testObtenerPrestamoPorID() {
            System.out.println("TC05 - Obtener préstamo por ID");

            Prestamo prestamo = new Prestamo();
            prestamo.setID(1);

            List<Map<String, Object>> esperado = List.of(
                    Map.of("ID", 1, "Cliente", "Ana", "Monto", 3000)
            );

            try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
                mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                                eq(prestamo), eq(E_ROL._PRESTAMO), eq(E_PARAMETRO._ID), anyList()))
                        .thenReturn(esperado);

                List<Map<String, Object>> resultado = ControladorPrestamo.obtenerPrestamoID(prestamo);
                System.out.println("Resultado esperado: " + esperado);
                System.out.println("Resultado real: " + resultado);
                assertEquals(esperado, resultado);
            }
        }

        @Test
        void TC06_testObtenerPrestamoPorFecha() {
            System.out.println("TC06 - Obtener préstamo por fecha");

            Prestamo prestamo = new Prestamo();
            prestamo.setFechaPrestamo(new GregorianCalendar(2024, Calendar.JULY, 12).getTime());

            List<Map<String, Object>> esperado = List.of(
                    Map.of("FechaPrestamo", "2024-07-12", "Cliente", "Luis", "Monto", 2000)
            );

            try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
                mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                                eq(prestamo), eq(E_ROL._PRESTAMO), eq(E_PARAMETRO._FECHA_PRESTAMO), anyList()))
                        .thenReturn(esperado);

                List<Map<String, Object>> resultado = ControladorPrestamo.obtenerPrestamoFecha(prestamo);
                System.out.println("Resultado esperado: " + esperado);
                System.out.println("Resultado real: " + resultado);
                assertEquals(esperado, resultado);
            }
        }
}
