package main.java.Controllers.Operadores.Enums;

public enum E_ROL {
    _BIBLIOTECARIO,
    _USUARIO,
    _LIBRO,
    _PRESTAMO,
    _EJEMPLAR;
    public String getDisplayName() {
        String clean = this.name().replace("_", "").toLowerCase();
        return clean.substring(0, 1).toUpperCase() + clean.substring(1);
    }
}
