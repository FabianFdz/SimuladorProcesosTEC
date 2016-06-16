public class Instruccion {
    
    public Instruccion(String operacion, int val, int desplazamiento) {
        this.operacion = operacion;
        this.desplazamiento = desplazamiento;
        this.valor = val;
    }

    public Instruccion(String operacion, String nombre) {
        this.operacion = operacion;
        this.nombre = nombre;
    }

    public Instruccion(String operacion) {
        this.operacion = operacion;
    }

    public String operacion;
    public String nombre;
    public int duracion = 0;
    public int desplazamiento;
    public int valor;
}
