package main.java.Controllers.Operadores.Creators;

import main.java.Models.Entidad;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropiedadesCreator {
    private final Map<Integer,Object>lista;
    private static int index=1;
    private boolean esPrimerCampo;
    private final static String metodo = "get";
    public PropiedadesCreator(){
        lista=new LinkedHashMap<>();
        esPrimerCampo=true;
    }
    //SIRVE PARA LOS METODOS INSERT, UPDATE Y DELETE
    public<T extends Entidad> Map<Integer, Object> crearPropiedadesNoID(T entidad){// para crear
        lista.clear();
        try {
            Class<?> clazz=entidad.getClass();
            Field[]campos=clazz.getDeclaredFields();
            esPrimerCampo = true;
            for (Field campo:campos){
                String nombreCampo=campo.getName();
                if (esPrimerCampo) {
                    esPrimerCampo = false; // Salta el primer campo (ID)
                    continue;
                }
                String nombreGetter=metodo+nombreCampo;
                Method getter=clazz.getMethod(nombreGetter);
                Object valor=getter.invoke(entidad);
                lista.put(index++,tipoParametro(valor));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }
    public <T extends Entidad> Map<Integer, Object> listarPropiedad(T entidad,String atributo){// para eliminar y consultas de 1 parametro
        lista.clear();
        try {
            Class<?> clazz = entidad.getClass();
            String nombreGetter = metodo  + atributo;
            Method getter = clazz.getMethod(nombreGetter);
            Object valor = getter.invoke(entidad);
            lista.put(index, tipoParametro(valor));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }
    public <T extends Entidad> Map<Integer, Object> crearPropiedades(T entidad){// para actualizar y consultas de 2 parametros
        lista.clear();
        try {
            Class<?> clazz=entidad.getClass();
            Field[]campos=clazz.getDeclaredFields();
            for (Field campo:campos){
                String nombreCampo=campo.getName();
                String nombreGetter=metodo+nombreCampo;
                Method getter=clazz.getMethod(nombreGetter);
                Object valor=getter.invoke(entidad);
                lista.put(index++,tipoParametro(valor));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }
//    private boolean esGetterValido(Method metodo) {
//        return metodo.getName().startsWith("get")
//                && !metodo.getName().equals("getClass")
//                && metodo.getParameterCount() == 0;
//    }
    private Object tipoParametro(Object parametro){
        if (parametro instanceof Integer){
            return parametro;
        }
        if( parametro instanceof String){
            return parametro;
        }
        if(parametro instanceof Boolean){
            return parametro;
        }
        if (parametro instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format((Date) parametro);
        }
        throw new IllegalArgumentException("Tipo de parametro no soportado: " + parametro.getClass().getName());
    }
}
