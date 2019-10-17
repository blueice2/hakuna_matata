package hakuna_matata;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Enrollment control test 
 */
public class EnrollmentDialog
	extends JDialog
{
    public EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    byte[]  ehg;
   DPFPTemplate eryt;

    public byte[] get_ehg(){
    return ehg;
    
    }
    
       public DPFPTemplate get_unserialized(){
    return eryt;
    
    }
    public EnrollmentDialog(Frame owner, int maxCount, final String reasonToFail, EnumMap<DPFPFingerIndex, DPFPTemplate> templates) {
        super (owner, true);
        this.templates = templates;

        setTitle("Fingerprint Enrollment");

        DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();

        EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf(DPFPFingerIndex.class);
        fingers.addAll(templates.keySet());
        enrollmentControl.setEnrolledFingers(fingers);
        enrollmentControl.setMaxEnrollFingerCount(maxCount);

        enrollmentControl.addEnrollmentListener(new DPFPEnrollmentListener()
        {
            public void fingerDeleted(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                } else {
                    EnrollmentDialog.this.templates.remove(e.getFingerIndex());
                }
            }

            public void fingerEnrolled(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
//                  e.setStopCapture(false);
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                } else
                    EnrollmentDialog.this.templates.put(e.getFingerIndex(), e.getTemplate());
               // ehg = e.getTemplate().serialize();
               ehg = e.getTemplate().serialize();
               eryt = e.getTemplate();
              // ehg =e;
               
                
              
            }
        });

		getContentPane().setLayout(new BorderLayout());

		JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);                //End Dialog
                
                //System.out.println("EnrollmentDialog.this.templates "+templates);
            }
        });
        
        
        // When you want to save to a database work with button save, variable ehg is what you save in enrollmentDisalog
        // Save variable "ehg" to the database.It is called template in VerificationDialog which handles the verification.
        //YOu can find this template in code snippet: final DPFPVerificationResult result = verification.verify(e.getFeatureSet(), template);"
        //in VerificationDialog
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ed) {
                //setVisible(false);                //End Dialog
                
                
                
              try{
                  File file = new File("hey.ser");
                if(!file.exists()){file.createNewFile();}  
                    FileOutputStream fout = new FileOutputStream(file.getAbsoluteFile());
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    //oos.writeObject( ehg);
                     //oos.writeObject( templates.get(DPFPFingerIndex.RIGHT_THUMB));
                    // System.out.println("Templates enum size: "+templates.size());
                    // System.out.println("Templates contains: "+templates.get(DPFPFingerIndex.RIGHT_THUMB));
                    // next write ehg to a database and load it from the database in verificationdialog
                    
                    
              }catch(Exception eh){eh.printStackTrace();}
               
           
           
                    //using(FileStream fs = File.Open(save.FileName, FileMode.Create, Fileccess.Write))
                  
                System.out.println("EnrollmentDialog.this.templates "+templates);
            }
        });

		JPanel bottom = new JPanel();
		bottom.add(closeButton);
                bottom.add(saveButton);
		add(enrollmentControl, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

		pack();
        setLocationRelativeTo(null);         
   }
}