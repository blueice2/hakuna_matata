/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hakuna_matata;

import java.io.FileInputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Finishings;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

/**
 *
 * @author NNAMDI
 */
public class PrintPS {
    public static void main(String args[]){
    PrintPS ps = new PrintPS();
    }
    
    public PrintPS(){
    DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        
        aset.add(MediaSizeName.ISO_A4);
        aset.add(new Copies(2));
        aset.add(Sides.TWO_SIDED_LONG_EDGE);
        aset.add(Finishings.STAPLE);
        
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
        
        if(pservices.length>0){
        System.out.println("Selected Printer "+pservices[0].getName());
        
        DocPrintJob pj = pservices[0].createPrintJob();
        
        try{
        FileInputStream fis = new FileInputStream("readme.txt");
        Doc doc = new SimpleDoc(fis, flavor, null);
        pj.print(doc, aset);
        }
        catch(Exception ertyu){System.err.print(ertyu);}
        }
    }
}
