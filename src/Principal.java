import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Principal {

    public static int cantProcesadores;
    public Set<Proceso> listaProcesos;
    public Proceso lista;
    
    public static Document abrirArchivo(){
        try{
            //C:/Users/Esteban/Documents/GitHub/SimuladorProcesosTEC/archivo.xml
            //..//archivo.xml
            org.jsoup.nodes.Document doc = Jsoup.parse( new File("C:/Users/Esteban/Documents/GitHub/SimuladorProcesosTEC/archivo.xml") , "utf-8" );
            return doc;
        } 
        catch (IOException e){
        } 
        return null;
    }
    
    public static void cargarProcesadores(org.jsoup.nodes.Document doc){
        String procText = doc.select("processors").text();
        System.out.println(procText);
        int numProc = Integer.parseInt(procText);
        int i = 0;
        
        while(i < numProc){
            Procesador proc = new Procesador();
            DTO.objDTO.listaProcesadores.add(proc);
            i++;
        }        
    }
    
    public static void cargarIO(org.jsoup.nodes.Document doc){
        doc.select("io").select("name").stream().forEach((org.jsoup.nodes.Element e) -> {
            IO temp = new IO(e.text());
            DTO.objDTO.listaIO.add(temp);
        });
    }
    
    public static Instruccion construirInstruccion(String operacion){
        System.out.println(operacion);
        String[] lis = operacion.split(" ");
        if(lis.length == 3){
            Instruccion temp = new Instruccion(lis[0],Integer.parseInt(lis[1]),Integer.parseInt(lis[2]));
            return temp;
        }
        if(lis[0].equals("Add") || lis[0].equals("Substract")){
            Instruccion temp = new Instruccion(lis[0],lis[1]);
            return temp;
        }
        if(lis[0].equals("Inc") || lis[0].equals("Dec") || lis[0].equals("ShiftL") || lis[0].equals("ShiftR") || lis[0].equals("Halt")){
            Instruccion temp = new Instruccion(lis[0]);
            return temp;
        }
        else{
            Instruccion temp = new Instruccion(lis[0],lis[1]);
            return temp;
        }
            
    }
    
    public static void cargarPocesos(org.jsoup.nodes.Document doc){
        doc.select("processes").select("process").stream().forEach((org.jsoup.nodes.Element e) -> {
            Proceso temp = new Proceso(e.select("name").text());
            String[] parts = e.select("operators").text().split(",");
            for (int i = 0; i < parts.length; i++) {
                //crea instrucciones diferentes para cada tipo de instrucción
                Instruccion itemp = construirInstruccion(parts[i]);
                temp.listaInstrucciones.add(itemp);
            }
            DTO.objDTO.listaProcesos.add(temp);
        });
    }
    
    public static void cargarArchivos(org.jsoup.nodes.Document doc){
        doc.select("files").select("file").stream().forEach((org.jsoup.nodes.Element e) -> {
            String name = e.select("name").text();
            Double val = Double.parseDouble(e.select("value").text());
            Archivo temp = new Archivo(name,val);
            DTO.objDTO.listaArchivos.add(temp);
        });
    }

    public static void cargarXMLenDTO(DTO objDTO) {         
        org.jsoup.nodes.Document doc = abrirArchivo();
        cargarProcesadores(doc);
        cargarIO(doc);
        cargarPocesos(doc);
        cargarArchivos(doc);
    }

    public static DTO verEstadoGeneral(DTO objDTO) {
        // TODO implement here
        return null;
    }
    
    public static DTO ejecutarEnProcesadores(DTO objDTO) {
        // TODO implement here
        return null;
    }

    public static DTO crearEntorno(DTO objDTO) {
        // TODO implement here
        return null;
    }
}
