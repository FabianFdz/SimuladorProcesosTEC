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
            org.jsoup.nodes.Document doc = Jsoup.parse( new File("..//archivo.xml") , "utf-8" );
            return doc;
        } 
        catch (IOException e){
        } 
        return null;
    }
    
    public static void cargarProcesadores(org.jsoup.nodes.Document doc){
        String procText = doc.select("processors").text();
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
    
    public static void cargarPocesos(org.jsoup.nodes.Document doc){
        doc.select("processes").select("process").stream().forEach((org.jsoup.nodes.Element e) -> {
            Proceso temp = new Proceso(e.select("name").text());
            String[] parts = e.select("operators").text().split(",");
            for (int i = 0; i < parts.length; i++) {
                Instruccion itemp = new Instruccion();
                itemp.operacion = parts[i];
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
