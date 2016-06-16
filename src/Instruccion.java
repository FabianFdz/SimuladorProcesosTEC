public class Instruccion {

    /**
     * Default constructor
     */
    public Instruccion() {
    }

    public void Instruccion(String op) {
        String operacion = op;
    }

    public Instruccion(String operacion, int val, int desplazamiento) {
        this.operacion = operacion;
        this.duracion = 0; //Hacer metodo que calcule
        this.desplazamiento = desplazamiento;
        this.valor = val;
    }   

    public String operacion;
    public int duracion;
    public int desplazamiento;
    public int valor;
}
