import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Esteban
 */
public class main {
    public static void main (String [ ] args) {
        
        DTO objdto = new DTO();
        
        Principal.cargarXMLenDTO(objdto);
        

    } //Cierre del main
    
}
