package main.java.Controllers.Operadores.Creators;

import main.java.Models.Entidad;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PropiedadesCreator {
    private final Map<Integer,Object>lista;
    private int index; // Índice para los parámetros, comienza en 1 para evitar el ID
    private boolean esPrimerCampo;
    private final static String metodo = "get"; // Prefijo para los métodos getter
    public PropiedadesCreator(){
        lista=new LinkedHashMap<>();
        esPrimerCampo=false;
    }
    //SIRVE PARA LOS METODOS INSERT, UPDATE Y DELETE
    /**
     * Crea un mapa de propiedades de una entidad, excluyendo el ID, para operaciones como creación.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad de la cual se extraerán las propiedades.
     * @return Un mapa donde las claves son índices incrementales y los valores son las propiedades de la entidad,
     *         formateadas según su tipo.
     */
    public <T extends Entidad> Map<Integer, Object> crearPropiedadesNoID(T entidad){// para crear
        lista.clear();
        index=1;
        esPrimerCampo=true;
        try {
            Class<?> clase=entidad.getClass();
            Field[]campos=clase.getDeclaredFields();
            for (Field campo:campos){
                String nombreCampo=campo.getName(); // obtiene el nombre del campo
                if (esPrimerCampo) { // Verifica si es el primer campo
                    esPrimerCampo = false; // Salta el primer campo (ID)
                    continue; // Salta el primer campo (ID)
                }
                String nombreGetter=metodo+nombreCampo;
                Method getter=clase.getMethod(nombreGetter);
                Object valor=getter.invoke(entidad);
                lista.put(index++,tipoParametro(valor));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }
    // para eliminar y consultas de n parametros
    // Este metodo crea un mapa de propiedades de una entidad, incluyendo el ID
    /**
     * Crea un mapa de propiedades de una entidad utilizando una lista de atributos especificados.
     * Este metodo incluye el ID de la entidad y es útil para operaciones que requieren parámetros personalizados.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad de la cual se extraerán las propiedades.
     * @param atributos Una lista de nombres de atributos que se utilizarán para construir las propiedades de la entidad.
     * @return Un mapa donde las claves son índices incrementales y los valores son las propiedades de la entidad,
     *         formateadas según su tipo.
     */
    public <T extends Entidad> Map<Integer,Object>listarPropiedades(T entidad, List<String>atributos){
        lista.clear();
        index=1;
        try {
            Class<?> clase = entidad.getClass();
            for (String atributo: atributos){
                String nombreGetter=metodo+atributo; // get+NombreAtributo
                Method getter=clase.getMethod(nombreGetter);
                Object valor=getter.invoke(entidad);
                lista.put(index++, tipoParametro(valor)); // agrega el valor a la lista con un índice incremental
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }


    /**
     * Crea un mapa de propiedades de una entidad, incluyendo el ID, para operaciones como actualización.
     * Para actualizar y consultas de 2 parametros.
     * Este metodo crea un mapa de propiedades de una entidad, incluyendo el ID
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad de la cual se extraerán las propiedades.
     * @return Un mapa donde las claves son índices incrementales y los valores son las propiedades de la entidad,
     *         formateadas según su tipo.
     */
    public <T extends Entidad> Map<Integer, Object> crearPropiedades(T entidad){
        lista.clear(); // limpia la lista antes de agregar nuevos valores
        index=1;
        try {
            Class<?> clase=entidad.getClass(); // obtiene la clase de la entidad
            Field[]campos=clase.getDeclaredFields(); // obtiene todos los campos de la clase y los guarda en un array
            for (Field campo:campos){ // itera sobre cada campo
                String nombreCampo=campo.getName(); // obtiene el nombre del campo
                String nombreGetter=metodo+nombreCampo; // construye el nombre del metodo getter correspondiente al campo
                Method getter=clase.getMethod(nombreGetter); // obtiene el metodo getter de la clase
                Object valor=getter.invoke(entidad); // invoca el metodo getter para obtener el valor del campo
                lista.put(index++,tipoParametro(valor)); // agrega el valor a la lista con un índice incremental
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista; // devuelve la lista de propiedades con sus valores
    }
//    private boolean esGetterValido(Method metodo) {
//        return metodo.getName().startsWith("get")
//                && !metodo.getName().equals("getClass")
//                && metodo.getParameterCount() == 0;
//    }
    /**
     * Formatea un parámetro dependiendo de su tipo.
     *
     * @param parametro El objeto que se desea formatear. Puede ser de tipo Integer, String, Boolean, Byte o Date.
     * @return El parámetro formateado. Si es un tipo primitivo o String, se devuelve directamente.
     *         Si es una fecha, se convierte a un String con el formato "yyyy-MM-dd".
     * @throws IllegalArgumentException Si el tipo del parámetro no es soportado.
     */
    private Object tipoParametro(Object parametro){ // Formatea el parametro dependiendo de su tipo
        if (parametro instanceof Integer || parametro instanceof String || parametro instanceof Boolean) { // Si es un tipo primitivo o String, lo devuelve directamente
            return parametro; // no es necesario formatear
        }
        if(parametro instanceof Byte){
            return parametro;
        }
        if (parametro instanceof Date){ // Si es una fecha, la formatea a String
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
            return sdf.format((Date) parametro); // Convierte la fecha a String
        }
        throw new IllegalArgumentException("Tipo de parametro no soportado: " + parametro.getClass().getName());
    }
}
