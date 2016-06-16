
import java.util.*;

public class DTO {
    
    public static DTO objDTO = new DTO();
    
    public DTO() {
        listaProcesos = new ArrayList<Proceso>();
        listaArchivos = new ArrayList<Archivo>();
        listaIO = new ArrayList<IO>();
        listaProcesadores = new ArrayList<Procesador>();
        memoria = new Memoria();
        bloqueo = new ArrayList();
        accesoIlegal = new ArrayList();
    }
    
    public ArrayList<Proceso> listaProcesos;
    public Memoria memoria;
    public ArrayList<Archivo> listaArchivos;
    public ArrayList<IO> listaIO;
    public ArrayList bloqueo;
    public ArrayList accesoIlegal; //[[segundo de bloqueo, proceso],[..,..],..]
    public ArrayList<Procesador> listaProcesadores;
    
    public ArrayList<Proceso> getListaProcesos() {
        return listaProcesos;
    }
    
    public void setListaProcesos(ArrayList<Proceso> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }
    
    public Memoria getMemoria() {
        return memoria;
    }
    
    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
    }
    
    public ArrayList<Archivo> getListaArchivos() {
        return listaArchivos;
    }
    
    public void setListaArchivos(ArrayList<Archivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }
    
    public ArrayList<IO> getListaIO() {
        return listaIO;
    }
    
    public void setListaIO(ArrayList<IO> listaIO) {
        this.listaIO = listaIO;
    }
    
    public ArrayList isBloqueo() {
        return bloqueo;
    }
    
    public void addBloqueo(Proceso procesoBloqueado) {
        this.bloqueo.add(procesoBloqueado);
    }
    
    public ArrayList<Proceso> getAccesoIlegal() {
        return accesoIlegal;
    }
 
    public void addAccesoIlegal(Proceso procesoIlegal) {
        this.accesoIlegal.add(procesoIlegal);
    }
    
    public ArrayList<Procesador> getListaProcesadores() {
        return listaProcesadores;
    }
    
    public void setListaProcesadores(ArrayList<Procesador> listaProcesadores) {
        this.listaProcesadores = listaProcesadores;
    }
    
}
