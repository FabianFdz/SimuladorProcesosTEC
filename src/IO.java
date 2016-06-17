public class IO {   
    public String nombreIO;
    public Proceso usadoPor = null;
    
    public IO(String nom) {
        nombreIO = nom;
    }
    
    public void asignaProc(Proceso proc){
        usadoPor = proc;
    }
    
    public void liberaArchivo(Proceso objP){
        if(this.usadoPor.equals(objP)){
            usadoPor = null;
        }
    }
}
