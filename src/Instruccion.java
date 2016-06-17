
import java.util.Arrays;
import java.util.List;

public class Instruccion {
    
    public Instruccion(String operacion, int desplazamiento, int val) {
        this.operacion = operacion;
        this.desplazamiento = desplazamiento;
        this.valor = val;
    }
    
    public Instruccion(String operacion, int desplazamiento) {
        this.operacion = operacion;
        this.desplazamiento = desplazamiento;
    }

    public Instruccion(String operacion, String nombre) {
        this.operacion = operacion;
        this.nombre = nombre;
    }

    public Instruccion(String operacion) {
        this.operacion = operacion;
    }
    
    public void determinarDuracion(){
        List<String> lista1 = Arrays.asList("Load","StoreD","Store","Inc","Dec","Halt");
        List<String> lista2 = Arrays.asList("Add","Substract","ShiftL","ShiftR");
        List<String> lista3 = Arrays.asList("ReadF");
        List<String> lista5 = Arrays.asList("PrintS");
        
        if(lista1.contains(operacion)){
           duracion = 1;
        }
        if(lista2.contains(operacion)){
           duracion = 2;
        }
        if(lista3.contains(operacion)){
           duracion = 3;
        }
        if(lista5.contains(operacion)){
           duracion = 5;
        }
    }

    public String operacion;
    public String nombre;
    public int duracion = 0;
    public int desplazamiento;
    public int valor;
}
