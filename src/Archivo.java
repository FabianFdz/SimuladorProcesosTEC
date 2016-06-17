public class Archivo {

    Archivo(String name, int val) {
        nombreArchivo = name;
        valor = val;
    }
    
    public void asignaProc(Proceso proc){
        usadoPor = proc;
    }
    
    public void liberaArchivo(Proceso objP){
        if(this.usadoPor.equals(objP)){
            usadoPor = null;
        }
    }

    public String nombreArchivo;
    public int valor;
    public Proceso usadoPor = null;
}
