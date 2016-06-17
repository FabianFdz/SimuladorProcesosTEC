
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Procesador {

    public Proceso procesoActual;
    public Instruccion PC;
    public int AC = 0;
    public Instruccion IR;

    public Procesador() {
        procesoActual = null;
        PC = null;
        IR = null;
    }

    public void cogerProcesoDeCola() { //Para agarrar el primer proceso disponible en la cola y lo pasa a procesoActual
        procesoActual = DTO.objDTO.listaProcesos.remove(0);
        //asigna memoria
        DTO.objDTO.memoria.asignaMemoria(procesoActual);
    }

    public void ejecutarUnSegundoDeProceso() {  //Antes de llamar a este, ya se debe tener un proceso asignado
                                                //Este es para correr un segundo de un procesador
        if (Objects.isNull(IR)) {
            if (procesoActual.listaInstrucciones.size() > 1) {
                DTO.objDTO.memoria.removerProcesoMemoria(procesoActual);
                IR = procesoActual.listaInstrucciones.remove(0);
                PC = procesoActual.listaInstrucciones.remove(0);
                DTO.objDTO.memoria.asignaMemoria(procesoActual);
                this.ejecutaOperacion();
            } else if (procesoActual.listaInstrucciones.size() == 1) {
                DTO.objDTO.memoria.removerProcesoMemoria(procesoActual);
                IR = procesoActual.listaInstrucciones.remove(0);
                DTO.objDTO.memoria.asignaMemoria(procesoActual);
                this.ejecutaOperacion();
            } else {
                IR = null;
                PC = null;
                DTO.objDTO.memoria.removerProcesoMemoria(procesoActual);
                return;
            }
        } else {
            if (DTO.objDTO.bloqueo.contains(IR)) {
                return;
            }            
            DTO.objDTO.memoria.removerProcesoMemoria(procesoActual);
            IR.duracion--;
            procesoActual.tiempoEjecucion--;
            //asigna memoria
            DTO.objDTO.memoria.asignaMemoria(procesoActual);
            if (IR.duracion == 0) {
                IR = null;
            }
        }
    }
    
    public void terminaProcesoBloqueado(){ //Para terminar proceso
        DTO.objDTO.memoria.removerProcesoMemoria(procesoActual);
        liberaArchivosYIO();
        IR = null;
        PC = null;
        procesoActual = null;
    }

    public void liberaArchivosYIO() {
        for (int i = 0; i < DTO.objDTO.listaIO.size(); i++) {
            if (DTO.objDTO.listaIO.get(i).usadoPor.nombreProceso.equals(procesoActual.nombreProceso)) {
                DTO.objDTO.listaIO.get(i).liberaArchivo(procesoActual);
            }
        }
        for (int i = 0; i < DTO.objDTO.listaArchivos.size(); i++) {
            if (DTO.objDTO.listaArchivos.get(i).usadoPor.nombreProceso.equals(procesoActual.nombreProceso)) {
                DTO.objDTO.listaArchivos.get(i).liberaArchivo(procesoActual);
            }
        }
    }

    private void ejecutaOperacion() {
        if (this.IR.operacion.equalsIgnoreCase("Load")) {
            this.Load();
        } else if (this.IR.operacion.equalsIgnoreCase("StoreD")) {
            this.StoreD();
        } else if (this.IR.operacion.equalsIgnoreCase("Inc")) {
            this.Inc();
        } else if (this.IR.operacion.equalsIgnoreCase("Dec")) {
            this.Dec();
        } else if (this.IR.operacion.equalsIgnoreCase("Store")) {
            this.Store();
        } else if (this.IR.operacion.equalsIgnoreCase("Halt")) {
            this.Halt();
        } else if (this.IR.operacion.equalsIgnoreCase("Add")) {
            this.Add();
        } else if (this.IR.operacion.equalsIgnoreCase("Substract")) {
            this.Substract();
        } else if (this.IR.operacion.equalsIgnoreCase("ReadF")) {
            this.ReadF();
        } else if (this.IR.operacion.equalsIgnoreCase("PrintS")) {
            this.PrintS();
        }
    }

    public void Load() {//Carga	un	valor	de	Memoria	al	AC
        AC = AC + 1;
    }

    public void StoreD() {
        DTO.objDTO.memoria.asignaValor(this.IR.valor, this.IR.desplazamiento, this.procesoActual);

        //Almacena	un	valor	directamente	a	una	  	posición	en	memoria	
    }

    public void Store() {
        DTO.objDTO.memoria.asignaValor(AC, this.IR.desplazamiento, this.procesoActual);
        //Almacena	un	valor	del	AC	a	una	posición	de	Memoria
    }

    public void Add() {

        AC = AC + (DTO.objDTO.memoria.getPosicion(this.IR.valor));
        //Suma	el	valor	contenido	de	una	posición 	de	Memoria	al	valor	del	AC
    }

    public void Substract() {
        AC = AC - (DTO.objDTO.memoria.getPosicion(this.IR.valor));
        //Resta el	valor	contenido	de	una	posición	de	Memoria	al	valor	del	AC
    }

    public void Inc() {
        AC = AC + 1;
        //Incrementa en	1 el	valor	almacenado	en		el	AC
    }

    public void Dec() {
        AC = AC - 1;
        //Resta en	1	el	valor	almacenado	en	el	AC
    }

    public void ShifL() {

        //Hace	un	corrimiento	en	una	posición a	la Izquierda de	la	representación	binaria	del    	valor	almacenado	en	AC
    }

    public void ShiftR() {
        //Hace	un	corrimiento	en	una	posición a	la Derecha de	la	representación	binaria	del    	valor	almacenado	en	AC
    }

    public void PrintS() {
        for (int i = 0; i < DTO.objDTO.listaIO.size(); i++) {
            if (DTO.objDTO.listaIO.get(i).nombreIO.equalsIgnoreCase(this.IR.nombre)) {
                if (DTO.objDTO.listaIO.get(i).usadoPor.equals(procesoActual) || DTO.objDTO.listaIO.get(i).usadoPor.equals(null)) {
                    DTO.objDTO.listaIO.get(i).usadoPor = procesoActual;
                    return;
                } else {
                    DTO.objDTO.log.add(new ArrayList());
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add("Bloqueo");
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(procesoActual.tiempoEjecucion);
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(procesoActual);
                }
            }
        }
        //Envía el	valor	del AC a	un	dispositivo	de	salida
    }

    public void ReadF() {
        for (int i = 0; i < DTO.objDTO.listaArchivos.size(); i++) {
            if (DTO.objDTO.listaArchivos.get(i).nombreArchivo.equalsIgnoreCase(this.IR.nombre)) {
                if (DTO.objDTO.listaArchivos.get(i).usadoPor.equals(procesoActual) || DTO.objDTO.listaIO.get(i).usadoPor.equals(null)) {
                    DTO.objDTO.listaArchivos.get(i).usadoPor = procesoActual;
                    this.AC = DTO.objDTO.listaArchivos.get(i).valor;
                    return;
                } else {
                    DTO.objDTO.log.add(new ArrayList());
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add("Bloqueo");
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(procesoActual.tiempoEjecucion);
                    DTO.objDTO.log.get(DTO.objDTO.log.size() - 1).add(procesoActual);
                }
            }
        }

        //Lee	el	valor	de	un	archivo	y	lo	guarda	en	el AC
    }

    public void WriteF() {
        for (int i = 0; i < DTO.objDTO.listaArchivos.size(); i++) {
            if (DTO.objDTO.listaArchivos.get(i).nombreArchivo.equalsIgnoreCase(this.IR.nombre) || DTO.objDTO.listaIO.get(i).usadoPor.equals(null)) {
                DTO.objDTO.listaArchivos.get(i).usadoPor = procesoActual;
                DTO.objDTO.listaArchivos.get(i).valor = AC;
            }
        }
        //Guarda	un	el	valor	almacenado	en	el	AC	en	el	archivo	que	se	especifica	por	su	nombre
    }

    public void Halt() {
        this.procesoActual.tiempoEjecucion -= IR.duracion;
        if (procesoActual.listaInstrucciones.size() > 1) {
            IR = procesoActual.listaInstrucciones.remove(0);
            PC = procesoActual.listaInstrucciones.remove(0);
            this.ejecutaOperacion();
        } else if (procesoActual.listaInstrucciones.size() == 1) {
            IR = procesoActual.listaInstrucciones.remove(0);
            this.ejecutaOperacion();
        } else {
            IR = null;
            PC = null;
        }
    }

}
