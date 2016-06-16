import java.util.*;

public class Proceso {
    public String nombreProceso;
    public double tiempoEjecucion = 0;
    public double tamanhoBits = 0;
    public ArrayList<Integer> rangosMemoria = new ArrayList<Integer>();
    public ArrayList<Instruccion> listaInstrucciones = new ArrayList<Instruccion>();
    
    public Proceso(String nom) {
        nombreProceso = nom;
        this.calculaMemoriaYTiempo();
    }
    
    private void calculaMemoriaYTiempo(){
        for (Instruccion objProc : listaInstrucciones) {
            
        }
    }
}
