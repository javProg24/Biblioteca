package test;

import Controllers.Operadores.Enums.E_PARAMETRO;
import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorGeneral;
import Controllers.Operadores.Metodos.ControladorLibro;
import Models.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorLibroTest {

    private Libro libro;

    @BeforeEach
    void setup() {
        libro = Libro.builder()
                .ID(1)
                .Titulo("El Principito")
                .Autor("Antoine de Saint-Exupéry")
                .Anio_Publicacion(1943)
                .Categoria("Ficción")
                .ISBN(123456789)
                .build();
    }

    @Test
    void TC01_testCrearLibro() {
        System.out.println("TC01 - Crear libro");

        int indexOutput = 0;

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.crearEntidad(libro, E_ROL._LIBRO, indexOutput))
                    .thenReturn(true);

            boolean resultado = ControladorLibro.crearLibro(libro, indexOutput);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC02_testActualizarLibro() {
        System.out.println("TC02 - Actualizar libro");

        libro.setTitulo("El Principito - Edición Revisada");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.actualizarEntidad(libro, E_ROL._LIBRO))
                    .thenReturn(true);

            boolean resultado = ControladorLibro.actualizarLibro(libro);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC03_testEliminarLibro() {
        System.out.println("TC03 - Eliminar libro");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.eliminarEntidad(eq(libro), eq(E_ROL._LIBRO), anyList()))
                    .thenReturn(true);

            boolean resultado = ControladorLibro.eliminarLibro(libro);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC04_testObtenerLibros() {
        System.out.println("TC04 - Obtener todos los libros");

        List<Map<String, Object>> librosEsperados = List.of(
                Map.of("ID", 1, "Titulo", "El Principito", "Autor", "Antoine", "ISBN", 123456789),
                Map.of("ID", 2, "Titulo", "1984", "Autor", "George Orwell", "ISBN", 987654321)
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidad(E_ROL._LIBRO))
                    .thenReturn(librosEsperados);

            List<Map<String, Object>> resultado = ControladorLibro.obtenerLibros();
            System.out.println("Resultado esperado: " + librosEsperados);
            System.out.println("Resultado real: " + resultado);
            assertEquals(librosEsperados, resultado);
        }
    }

    @Test
    void TC05_testObtenerLibroPorID() {
        System.out.println("TC05 - Obtener libro por ID");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Titulo", "El Principito", "Autor", "Antoine")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(libro), eq(E_ROL._LIBRO), eq(E_PARAMETRO._ID), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorLibro.obtenerLibroID(libro);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }

    @Test
    void TC06_testObtenerLibroPorTitulo() {
        System.out.println("TC06 - Obtener libro por título");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Titulo", "El Principito", "Autor", "Antoine")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(libro), eq(E_ROL._LIBRO), eq(E_PARAMETRO._TITULO), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorLibro.obtenerLibroTitulo(libro);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }
}
