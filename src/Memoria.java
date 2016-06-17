
import java.util.*;

public class Memoria {

    public Memoria() {
    }

    public ArrayList<ArrayList> fisica = new ArrayList<ArrayList>(); // -> [[val,proc],[val,proc],...]
    public ArrayList<ArrayList> virtual = new ArrayList<ArrayList>(); // -> [[val,proc],[val,proc],...]

    public Proceso asignaMemoria(Proceso objProceso) {
        int ind_dis = verificaExisteSegmentoFisica(objProceso);
        if (ind_dis >= 0) {
            for (int i = ind_dis; i < objProceso.tamanhoBits + ind_dis; i++) {
                fisica.get(i).set(1, objProceso);
            }
            objProceso.rangosMemoria.add(0, ind_dis);
            objProceso.rangosMemoria.add(1, ind_dis + objProceso.tamanhoBits);
            objProceso.rangosMemoria.add(2, -1);
            objProceso.rangosMemoria.add(3, -1);
        } else if (ind_dis == -1) {
            ind_dis = verificaExisteSegmentoVirtual(objProceso);
            for (int i = ind_dis; i < objProceso.tamanhoBits; i++) {
                virtual.get(i).set(1, objProceso);
            }
            objProceso.rangosMemoria.add(0, -1);
            objProceso.rangosMemoria.add(1, -1);
            objProceso.rangosMemoria.add(2, ind_dis);
            objProceso.rangosMemoria.add(3, ind_dis + objProceso.tamanhoBits);
        } else {
            DTO.objDTO.addAccesoIlegal(objProceso);
            DTO.objDTO.log.add(new ArrayList());
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add("Bloqueo");
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(objProceso.tiempoEjecucion);
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(objProceso);
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
        mem.asignaValor(7, 34, proc);
        System.out.println(mem.getPosicion(2));
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

    public void asignaValor(int val, int des, Proceso proc) {
        if (verificaPermanenciaEnZona(proc, des)) {
            if (proc.rangosMemoria.get(0) == -1) {
                virtual.get(proc.rangosMemoria.get(2) + des).set(0, val);
            } else if (proc.rangosMemoria.get(2) == -1) {
                fisica.get(proc.rangosMemoria.get(2) + des).set(0, val);
            }
        } else {
            DTO.objDTO.addAccesoIlegal(proc);
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add("Acceso Ilegal");
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(proc.tiempoEjecucion);
            DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(proc);
        }
    }

    public boolean verificaPermanenciaEnZona(Proceso proc, int desplaza) {
        int r1_fisica = proc.rangosMemoria.get(0);
        int r1_virtual = proc.rangosMemoria.get(2);
        if (r1_fisica != -1) {
            if (Objects.isNull(fisica.get(r1_fisica + desplaza).get(1))) {
                return false; //Ya no esta en su zona, sino en memoria disponible
            } else if (fisica.get(r1_fisica + desplaza).get(1).equals(proc)) {
                return true;
            } else {
                return false;
            }
        } else if (r1_virtual != -1) {
            if (Objects.isNull(virtual.get(r1_fisica + desplaza).get(1))) {
                return false; //Ya no esta en su zona, sino en memoria disponible
            } else if (virtual.get(r1_fisica + desplaza).get(1).equals(proc)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void removerProcesoMemoria(Proceso objProc) {
        for (ArrayList objMem : fisica) {
            if (objMem.get(1).equals(objProc)) {
                objMem.set(0, 0);
                objMem.set(1, null);
            }
        }
        for (ArrayList objMem : virtual) {
            if (objMem.get(1).equals(objProc)) {
                objMem.set(0, 0);
                objMem.set(1, null);
            }
        }
    }

    public int getPosicion(int i) {
        return Integer.parseInt(this.fisica.get(i - 1).get(0).toString());
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
