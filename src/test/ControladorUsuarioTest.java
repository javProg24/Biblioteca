package test;

import Controllers.Operadores.Enums.E_PARAMETRO;
import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorGeneral;
import Controllers.Operadores.Metodos.ControladorUsuario;
import Models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorUsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder()
                .ID(1)
                .Nombre("Juan")
                .Apellido("Pérez")
                .Dirreccion("Av. Siempre Viva")
                .Telefono(123456789)
                .Fecha_Nacimiento(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime())
                .build();
    }

    @Test
    void TC01_testCrearUsuario() {
        System.out.println("TC01 - Crear usuario");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.crearEntidad(usuario, E_ROL._USUARIO))
                    .thenReturn(true);

            boolean resultado = ControladorUsuario.crearUsuario(usuario);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC02_testActualizarUsuario() {
        System.out.println("TC02 - Actualizar usuario");

        usuario.setNombre("Juan Actualizado");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.actualizarEntidad(usuario, E_ROL._USUARIO))
                    .thenReturn(true);

            boolean resultado = ControladorUsuario.actualizarUsuario(usuario);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC03_testEliminarUsuario() {
        System.out.println("TC03 - Eliminar usuario");

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.eliminarEntidad(eq(usuario), eq(E_ROL._USUARIO), anyList()))
                    .thenReturn(true);

            boolean resultado = ControladorUsuario.eliminarUsuario(usuario);
            System.out.println("Resultado esperado: true, Resultado real: " + resultado);
            assertTrue(resultado);
        }
    }

    @Test
    void TC04_testObtenerUsuarios() {
        System.out.println("TC04 - Obtener todos los usuarios");

        List<Map<String, Object>> usuariosEsperados = List.of(
                Map.of("ID", 1, "Nombre", "Juan", "Cedula", "0102030405"),
                Map.of("ID", 2, "Nombre", "María", "Cedula", "0203040506")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidad(E_ROL._USUARIO))
                    .thenReturn(usuariosEsperados);

            List<Map<String, Object>> resultado = ControladorUsuario.obtenerUsuarios();
            System.out.println("Resultado esperado: " + usuariosEsperados);
            System.out.println("Resultado real: " + resultado);
            assertEquals(usuariosEsperados, resultado);
        }
    }

    @Test
    void TC05_testObtenerUsuarioPorID() {
        System.out.println("TC05 - Obtener usuario por ID");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Nombre", "Juan", "Cedula", "0102030405")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(usuario), eq(E_ROL._USUARIO), eq(E_PARAMETRO._ID), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorUsuario.obtenerUsuarioID(usuario);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }

    @Test
    void TC06_testObtenerUsuarioPorNombre() {
        System.out.println("TC06 - Obtener usuario por nombre");

        List<Map<String, Object>> esperado = List.of(
                Map.of("ID", 1, "Nombre", "Juan", "Cedula", "0102030405")
        );

        try (MockedStatic<ControladorGeneral> mock = mockStatic(ControladorGeneral.class)) {
            mock.when(() -> ControladorGeneral.obtenerEntidadParametro(
                            eq(usuario), eq(E_ROL._USUARIO), eq(E_PARAMETRO._NOMBRE), anyList()))
                    .thenReturn(esperado);

            List<Map<String, Object>> resultado = ControladorUsuario.obtenerUsuarioNombre(usuario);
            System.out.println("Resultado esperado: " + esperado);
            System.out.println("Resultado real: " + resultado);
            assertEquals(esperado, resultado);
        }
    }
}