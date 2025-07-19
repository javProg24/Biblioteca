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
    private int index;
    private boolean esPrimerCampo;
    private final static String metodo = "get";
    public PropiedadesCreator(){
        lista=new LinkedHashMap<>();
        esPrimerCampo=false;
    }

    public <T extends Entidad> Map<Integer, Object> crearPropiedadesNoID(T entidad){// para crear
        lista.clear();
        index=1;
        esPrimerCampo=true;
        try {
            Class<?> clase=entidad.getClass();
            Field[]campos=clase.getDeclaredFields();
            for (Field campo:campos){
                String nombreCampo=campo.getName();
                if (esPrimerCampo) {
                    esPrimerCampo = false;
                    continue;
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

    public <T extends Entidad> Map<Integer,Object>listarPropiedades(T entidad, List<String>atributos){
        lista.clear();
        index=1;
        try {
            Class<?> clase = entidad.getClass();
            for (String atributo: atributos){
                String nombreGetter=metodo+atributo;
                Method getter=clase.getMethod(nombreGetter);
                Object valor=getter.invoke(entidad);
                lista.put(index++, tipoParametro(valor));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public <T extends Entidad> Map<Integer, Object> crearPropiedades(T entidad){
        lista.clear();
        index=1;
        try {
            Class<?> clase=entidad.getClass();
            Field[]campos=clase.getDeclaredFields();
            for (Field campo:campos){
                String nombreCampo=campo.getName();
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

    private Object tipoParametro(Object parametro){
        if (parametro instanceof Integer || parametro instanceof String || parametro instanceof Boolean) {
            return parametro;
        }
        if(parametro instanceof Byte){
            return parametro;
        }
        if (parametro instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format((Date) parametro);
        }
        throw new IllegalArgumentException("Tipo de parametro no soportado: " + parametro.getClass().getName());
    }
}
