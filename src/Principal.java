import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Principal {

    public static int cantProcesadores;
    public Set<Proceso> listaProcesos;
    public Proceso lista;
    
    public static Document abrirArchivo(String ruta){
        try{
            //C:/Users/Esteban/Documents/GitHub/SimuladorProcesosTEC/archivo.xml
            //..//archivo.xml
            org.jsoup.nodes.Document doc = Jsoup.parse( new File(ruta) , "utf-8" );
            return doc;
        } 
        catch (IOException e){
        } 
        return null;
    }
    
    public static void cargarProcesadores(org.jsoup.nodes.Document doc){
        String procText = doc.select("processors").text();
        int numProc;
        if(procText.equals("")){numProc = 2;}
        else{numProc = Integer.parseInt(procText);}
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
        if (Character.isWhitespace(operacion.charAt(0))) {
            // do something
            operacion = operacion.substring(1);
          }
        
        String[] lis = operacion.split(" ");
        if(lis.length == 3){
            Instruccion temp = new Instruccion(lis[0],Integer.parseInt(lis[1]),Integer.parseInt(lis[2]));
            return temp;
        }
        if(lis[0].equals("Add") || lis[0].equals("Substract")){
            Instruccion temp = new Instruccion(lis[0],Integer.parseInt(lis[1]));
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
    
    public static int determinarDuracion(Instruccion inst){
        int duracion = 0;
        List<String> lista1 = Arrays.asList("Load","StoreD","Store","Inc","Dec","Halt");
        List<String> lista2 = Arrays.asList("Add","Substract","ShiftL","ShiftR");
        List<String> lista3 = Arrays.asList("ReadF");
        List<String> lista5 = Arrays.asList("PrintS");
        lista1.contains("hola");
        
        if(lista1.contains(inst.nombre)){
           duracion = 1;
        }
        if(lista2.contains(inst.nombre)){
           duracion = 2;
        }
        if(lista3.contains(inst.nombre)){
           duracion = 3;
        }
        if(lista5.contains(inst.nombre)){
           duracion = 5;
        }
        return duracion; 
    }
    
    public static void cargarPocesos(org.jsoup.nodes.Document doc){
        doc.select("processes").select("process").stream().forEach((org.jsoup.nodes.Element e) -> {
            Proceso temp = new Proceso(e.select("name").text());
            String[] parts = e.select("operators").text().split(",");
            for (int i = 0; i < parts.length; i++) {
                //crea instrucciones diferentes para cada tipo de instrucción
                Instruccion itemp = construirInstruccion(parts[i]);
                itemp.duracion = determinarDuracion(itemp);
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

    public static void cargarXMLenDTO(String ruta) {         
        org.jsoup.nodes.Document doc = abrirArchivo(ruta);
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
