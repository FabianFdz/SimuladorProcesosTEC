public class IO {   
    public String nombreIO;
    public Proceso procesoAsignado = null;
    
    public IO(String nom) {
        nombreIO = nom;
    }
    
    public void asignaProc(Proceso proc){
        procesoAsignado = proc;
    }
}
