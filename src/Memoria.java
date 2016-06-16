import java.util.*;

public class Memoria {

    public Memoria() {
    }

    public ArrayList<ArrayList> fisica = new ArrayList<ArrayList>(); // -> [[val,proc],[val,proc],...]
    public ArrayList<ArrayList> virtual = new ArrayList<ArrayList>(); // -> [[val,proc],[val,proc],...]

    public Proceso asignaMemoria(Proceso objProceso) {
        int ind_dis = verificaExisteSegmentoFisica(objProceso);
        if (ind_dis >= 0) {
            for (int i = ind_dis; i < objProceso.tamanhoBits; i++) {
                fisica.get(i).set(0, 1);
                fisica.get(i).set(1, objProceso);                
            }
            objProceso.rangosMemoria.add(0, ind_dis);
            objProceso.rangosMemoria.add(1, ind_dis+objProceso.tamanhoBits);
        }else if (ind_dis == -1) {
            ind_dis = verificaExisteSegmentoVirtual(objProceso);
            for (int i = ind_dis; i < objProceso.tamanhoBits; i++) {
                virtual.get(i).set(0, 1);
                virtual.get(i).set(1, objProceso);
            }
        }else{
            DTO.objDTO.addAccesoIlegal(objProceso);
            //bloquea proceso hasta que se libere memoria
        }
        return objProceso;
    }   

    public static void main(String[] args) {
        Memoria mem = new Memoria();
        mem.llenaNulo();
        System.out.println("La memoria fisica es de " + mem.fisica.size() + " bytes.");
        System.out.println("La memoria virtual es de " + mem.virtual.size() + " bytes.");
        Proceso proc = new Proceso("");
        proc.tamanhoBits = 32;
        Proceso proc2 = new Proceso("");
        proc2.tamanhoBits = 64;
        mem.asignaMemoria(proc);
        mem.asignaMemoria(proc2);
        System.out.println("El proximo indice disponible es: " + (mem.verificaExisteSegmentoFisica(proc) + 1));
    }

    private int verificaExisteSegmentoFisica(Proceso proc) {
        for (int i = 0; i < 1024; i++) {
            if (fisica.get(i).get(1) == null) {
                for (int j = i; j < proc.tamanhoBits; j++) {
                    if (fisica.get(j).get(1) != null) {
                        break;
                    }
                }
                return i;
            }
        }
        return -1;
    }

    private int verificaExisteSegmentoVirtual(Proceso proc) {
        for (int i = 0; i < 1024; i++) {
            if (virtual.get(i).get(1) == null) {
                for (int j = i; j < proc.tamanhoBits; j++) {
                    if (virtual.get(j).get(1) != null) {
                        break;
                    }
                }
                return i;
            }
        }
        return -1;
    }

    public void asignaValor(int val, int des, Proceso proc){
        if(verificaPermanenciaEnZona(proc, des)){
            if (proc.rangosMemoria.get(0)==null) {
                virtual.get(proc.rangosMemoria.get(2)+des).set(0,val);
            }else if(proc.rangosMemoria.get(2)==null) {
                fisica.get(proc.rangosMemoria.get(2)+des).set(0,val);
            }
        }else{
            DTO.objDTO.addAccesoIlegal(proc);
            //mensaje emergente de acceso ilegal
        }
    }

    public boolean verificaPermanenciaEnZona(Proceso proc, int desplaza) {
        int r1_fisica = proc.rangosMemoria.get(0);
        int r1_virtual = proc.rangosMemoria.get(2);
        if (Objects.isNull(r1_fisica)) {
            if (Objects.isNull(virtual.get(r1_virtual + desplaza).get(1))) {
                return false; //Ya no esta en su zona, sino en memoria disponible
            } else if (virtual.get(r1_virtual + desplaza).equals(proc)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Object getPosicion(int i){
        return this.fisica.get(i).get(0);        
    }
    
    public void llenaNulo() {
        for (int i = 0; i < 1024; i++) {
            fisica.add(new ArrayList());
            fisica.get(fisica.size() - 1).add(0);
            fisica.get(fisica.size() - 1).add(null);
            virtual.add(new ArrayList());
            virtual.get(virtual.size() - 1).add(0);
            virtual.get(virtual.size() - 1).add(null);
        }
    }
}