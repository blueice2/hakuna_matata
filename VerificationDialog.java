package hakuna_matata;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.*;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationControl;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationEvent;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationListener;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationVetoException;
import static hakuna_matata.MainForm2.create_mysql_connection;
import static hakuna_matata.hakuna.conn;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Enrollment control test
 */
public class VerificationDialog
        extends JDialog {

    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    private int farRequested;
    private int farAchieved;

    private DPFPVerificationControl verificationControl;
    private boolean matched;
    private boolean enrolled;
    // Use prepared statement to set the ball roling
    PreparedStatement preparedStmt;
    static final String FAR_PROPERTY = "FAR";
    static final String MATCHED_PROPERTY = "Matched";
    public DPFPTemplate temperamental;
    String visitor_id;
    byte[] buffer = new byte[1024];
    DPFPTemplate templarssd = DPFPGlobal.getTemplateFactory().createTemplate();
    DPFPTemplate templars = DPFPGlobal.getTemplateFactory().createTemplate();

    public void enroll_and_save() {

        //To enroll finger to the db for the first time - run this block of commented code 
        // and  query = "SELECT * FROM `visitor` where visitor_id = ? ";
        create_mysql_connection();
        DateFormat df = new SimpleDateFormat("HH:mm:ss ");
        Date date = new Date();

        String date_of_visit = df.format(date);
        // Sql query
        String query;

        try {
            byte[] ab = new byte[1024];

            DPFPTemplate template = VerificationDialog.this.templates.get(DPFPFingerIndex.RIGHT_THUMB);

            ab = template.serialize();

            templars.deserialize(ab);

            System.out.println(" Wilson's template template : " + template);
            System.out.println(" Wilson's template templars : " + templars);

            query = "UPDATE visitor SET finger_print = ?, gate_time_in = ? WHERE visitor_id = ?";

            preparedStmt = conn.prepareStatement(query);

            preparedStmt.setBytes(1, ab);
            preparedStmt.setString(2, date_of_visit);
            preparedStmt.setString(3, visitor_id);

            preparedStmt.executeUpdate();
//                      
            //System.out.println("This is what we saved to mysql database after serializing temp: "+template.serialize());
            // Get it out again
            // Stop uncomment here
            //End to enroll finger to the db for the first time

        } catch (Exception ef) {

            System.err.println("Got an exception");
            System.err.println(ef.getMessage());

        };
    }

    public void verify_alone() {
        // To verify fingerprint identity 

        String query;
        ResultSet rs;
        String time_in = "";
        String time_out = "";
        try {
            query = "SELECT * FROM `visitor` where visitor_id = ? ";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, visitor_id);
            rs = preparedStmt.executeQuery();

            while (rs.next()) {

                buffer = rs.getBytes("finger_print");
                time_in = rs.getString("gate_time_in");
                time_out = rs.getString("gate_time_out");
                System.out.println("Time in: " + time_in);
                 System.out.println("Time out: " + time_out);
            }
            templarssd.deserialize(buffer);

            System.out.println(" Wilson's template templarssd: " + templarssd);

        } catch (Exception ef) {

            System.err.println("Got an exception");
            System.err.println(ef.getMessage());

        };

        try {
            if (time_in.equals(null)) {
                System.out.println("So it's null");
            } 
           
            else{
                System.out.println("It is not null");
                String querys = "UPDATE visitor SET gate_time_out = ? WHERE visitor_id = ?";
                PreparedStatement preparedStmts = conn.prepareStatement(querys);
                DateFormat df = new SimpleDateFormat("HH:mm:ss ");
                Date date = new Date();

                //String date_of_visit = df.format(date);
                preparedStmts.setString(1, "No value");
                preparedStmts.setString(2, visitor_id);
                 preparedStmts.executeUpdate();
            }
            
             if(time_out.equals("No value")) {

                System.out.println("It is not null");
                String querys = "UPDATE visitor SET gate_time_out = ? WHERE visitor_id = ?";
                PreparedStatement preparedStmts = conn.prepareStatement(querys);
                DateFormat df = new SimpleDateFormat("HH:mm:ss ");
                Date date = new Date();

                String date_of_visits = df.format(date);
                preparedStmts.setString(1, date_of_visits);
                preparedStmts.setString(2, visitor_id);
                 preparedStmts.executeUpdate();

            }
          
            
        } catch (Exception errr) {
            System.err.println("Got an exception");
            System.err.println(errr.getMessage());

        }

    }

    public VerificationDialog(java.awt.event.MouseEvent ef, gate_module_panel gate, String visitor_id, DPFPTemplate temperamental, Frame owner, EnumMap<DPFPFingerIndex, DPFPTemplate> templates, int farRequested) {
        super(owner, true);
        this.templates = templates;
        this.farRequested = farRequested;
        this.visitor_id = visitor_id;

        // this.temperamental = temperamental;
        //System.out.println("From verificationdialog" + temperamental);
        setTitle("Fingerprint Verification");
        setResizable(false);

        verificationControl = new DPFPVerificationControl();
        verificationControl.addVerificationListener(new DPFPVerificationListener() {
            public void captureCompleted(DPFPVerificationEvent e) throws DPFPVerificationVetoException {
                // final DPFPVerification verification
                //  = DPFPGlobal.getVerificationFactory().createVerification(VerificationDialog.this.farRequested);

                final DPFPVerification verification
                        = DPFPGlobal.getVerificationFactory().createVerification();

                e.setStopCapture(false);	// we want to continue capture until the dialog is closed
                int bestFAR = DPFPVerification.PROBABILITY_ONE;
                boolean hasMatch = false;
                //for (DPFPTemplate template : VerificationDialog.this.templates.values()) {

                //String visitor_id = "33";  // hard coded visitor_id
                System.out.println(" visitor_id : " + visitor_id);

                byte[] ab = new byte[1024];

                // Altered from here
                ResultSet rs;

                try {
                    //create a mysql connection
                    create_mysql_connection();
                    DateFormat df = new SimpleDateFormat("HH:mm:ss ");
                    Date date = new Date();

                    String date_of_visit = df.format(date);
                    // Sql query
                    
                    int col = 4;
                    String time_in ="";
                   try{ 
                      int row = gate.jTable1.rowAtPoint(ef.getPoint());
                     time_in = gate.jTable1.getValueAt(row, col ).toString();
                   }
                   
                   catch(Exception edf){
                   time_in  ="";
                   
                   }//To enroll and save
                    if(time_in.equals("")){System.out.println("Time in is null");
                    enroll_and_save();
                    verify_alone();
                    }
                    else{verify_alone();}
                    
                    //enroll_and_save();

                    //To verify alone
                    //verify_alone();

                } catch (Exception ef) {

                    System.err.println("Got an exception");
                    System.err.println(ef.getMessage());

                };

                //Altered complete
                final DPFPVerificationResult result = verification.verify(e.getFeatureSet(), templarssd);
                //final DPFPVerificationResult result = verification.verify(e.getFeatureSet(), temperamental);
                // Verification done by result
                System.out.println("result:  " + result.isVerified());

                e.setMatched(result.isVerified());		// report matching status
                bestFAR = Math.min(bestFAR, result.getFalseAcceptRate());
                if (e.getMatched()) {
                    hasMatch = true;

                    System.out.println("Verification confirmed");

                }
                // }
                setMatched(hasMatch);
                setFAR(bestFAR);
            }
        });

        getContentPane().setLayout(new BorderLayout());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); 		//End Dialog
            }
        });

        JPanel center = new JPanel();
        center.add(verificationControl);
        center.add(new JLabel("To verify your identity, touch fingerprint reader with any enrolled finger."));

        JPanel bottom = new JPanel();
        bottom.add(closeButton);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    public int getFAR() {
        return farAchieved;
    }

    protected void setFAR(int far) {
        final int old = getFAR();
        farAchieved = far;
        firePropertyChange(FAR_PROPERTY, old, getFAR());
    }

    public boolean getMatched() {
        return matched;
    }

    protected void setMatched(boolean matched) {
        final boolean old = getMatched();
        this.matched = matched;
        firePropertyChange(MATCHED_PROPERTY, old, getMatched());
    }

    /**
     * Shows or hides this component depending on the value of parameter
     * <code>b</code>.
     *
     * @param b if <code>true</code>, shows this component; otherwise, hides
     * this component
     * @see #isVisible
     * @since JDK1.1
     */
    public void setVisible(boolean b) {
        if (b) {
            matched = false;
            verificationControl.start();
        } else {
            if (!matched) {
                verificationControl.stop();
            }
        }
        super.setVisible(b);
    }
}
