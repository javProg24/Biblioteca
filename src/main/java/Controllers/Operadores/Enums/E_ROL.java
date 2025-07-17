package main.java.Controllers.Operadores.Enums;
/**
 * Enumeración que define los parámetros utilizados en las operaciones de la aplicación.
 * Cada valor representa un atributo específico relacionado con las entidades manejadas.
 */
public enum E_ROL {
    _BIBLIOTECARIO,
    _USUARIO,
    _LIBRO,
    _PRESTAMO,
    _EJEMPLAR;
    /**
     * Obtiene el nombre del rol en un formato legible para mostrar.
     *
     * @return El nombre del rol con la primera letra en mayúscula y el resto en minúscula,
     *         eliminando los guiones bajos.
     */
    public String getDisplayName() {
        String clean = this.name().replace("_", "").toLowerCase();
        return clean.substring(0, 1).toUpperCase() + clean.substring(1);
    }
}
