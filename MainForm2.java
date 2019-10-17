package hakuna_matata;



import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import static hakuna_matata.hakuna.conn;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;

/** 
 * Enrollment control test console
 */
public class MainForm2
	extends JFrame
{
    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    private EnumMap<DPFPFingerIndex, JCheckBox> checkBoxes = new EnumMap<DPFPFingerIndex, JCheckBox>(DPFPFingerIndex.class);

    private static final DPFPTemplate fakeTemplate;
    private SpinnerNumberModel maxCount = new SpinnerNumberModel(DPFPFingerIndex.values().length, 0, DPFPFingerIndex.values().length, 1);
    private JSpinner maxCountSpinner;

   public final JButton enrollButton = new JButton("Enroll Fingerprints");
    public static final JButton verifyButton = new JButton("Verify Fingerprint");
    private SpinnerNumberModel farRequested = 
    	new SpinnerNumberModel(	DPFPVerification.MEDIUM_SECURITY_FAR, 1, DPFPVerification.PROBABILITY_ONE, 100);
    private JSpinner farRequestedSpinner;
    private JTextField farAchieved;
    JCheckBox fingerMatched;
    EnumMap<DPFPFingerIndex, DPFPTemplate> ehjkl;
    static String visitor_ids;
    static ResultSet rs;
    


    static {
        fakeTemplate = DPFPGlobal.getTemplateFactory().createTemplate();
        try {
            fakeTemplate.deserialize(new byte[0]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
        // Creates the connection to the database
    public static void create_mysql_connection() {
        try {
            //Select a jdbc driver which was added to the class path
            Class.forName("com.mysql.jdbc.Driver");

            //select the database - shop_floor
            String myUrl = "jdbc:mysql://localhost/hakuna";

            //Create the connection
            conn = DriverManager.getConnection(myUrl, "root", "");

        } catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }

    }// End of create_mysql_connection()
    
        // Closes the connection to the database
    public static void close_mysql_connection() {
        try {
            conn.close();
        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

        };
    }
static  DPFPTemplate tempfds = DPFPGlobal.getTemplateFactory().createTemplate();
    public static void update_fingerprint(  DPFPTemplate temp, byte[] templatesdd , String visitor_id) {
  System.out.println("Templates: "+templates);
  System.out.println("Templates: "+templates.keySet());
  System.out.println("Templates: "+templates.values());
  
           String ed =templates.values().toString();
                String BasicBase64format = Base64.getEncoder().encodeToString(ed.getBytes());
                byte[] actualbyte = Base64.getDecoder().decode(BasicBase64format);
              
        
        
        System.out.println("Before serializing: "+temp.toString());
          byte[] ab = new byte[1024];
         ab = temp.serialize();
   
        System.out.println(" templates serialized: "+ab); 
     temp = DPFPGlobal.getTemplateFactory().createTemplate(ab);
   
            // temp.deserialize(ab);
  
     System.out.println(" templates unserialized: "+temp);
     
    System.out.println("From enroll button's update_fingerprint temp: "+temp);
    //visitor_id ="1";
   ResultSet rs;
       byte[] buffer = new byte[1024];
try {
                //create a mysql connection
                create_mysql_connection();
                DateFormat df = new SimpleDateFormat("HH:mm:ss ");
                Date date = new Date();
                 
                String date_of_visit = df.format(date);
                // Sql query
                String query = "UPDATE visitor SET finger_print = ?, gate_time_in = ?,purpose_of_visit = ? WHERE visitor_id = ?";

          
            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                

            };
           
       
    }
    // Loads approval module based on the visitor_id for security
    
    
        public static DPFPTemplate  verify_fingerprint(String visitor_id) {
    System.out.println("From verify_fingerprint indeed: "+visitor_id); 
    visitor_id ="1";
    
    
    byte[] buffer = new byte[500000];
       
try {
                //create a mysql connection
                create_mysql_connection();
             
                // Sql query "SELECT * FROM `visitor` where visitor_id = ? "
                String query = "SELECT * FROM `visitor` where visitor_id = ? ";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                
                preparedStmt.setString(1, visitor_id);
                rs = preparedStmt.executeQuery();
                
                while(rs.next()){
                InputStream input = rs.getBinaryStream("finger_print");
                
                input.read(buffer);
               System.out.println("From verify fingerprint's buffer: "+buffer);
                }
               
            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                
               
            };
            
            DPFPTemplate tempfds = DPFPGlobal.getTemplateFactory().createTemplate();
            byte[] buffer_empty = new byte[500000];
            
            tempfds.deserialize(buffer);
           
            System.out.println("This is what we got from mysql database before deserializing temp: "+buffer);
                System.out.println("This is what we got from mysql database after deserialzing temp: "+tempfds);
   return tempfds;
    }
        
    public MainForm2(java.awt.event.MouseEvent ef, gate_module_panel gate,String visitor_id) {
    	super("Java UI Sample-Wilson Abercombie");
        visitor_ids = visitor_id;
        
    	setState(Frame.NORMAL);
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	setResizable(false);    	

    	//// Enrollment Panel
    	
        JPanel enrollmentConfigPanel = new JPanel();
        enrollmentConfigPanel.setBorder(BorderFactory.createTitledBorder("Enrollment"));
        enrollmentConfigPanel.setLayout(new BoxLayout(enrollmentConfigPanel, BoxLayout.Y_AXIS));

        ///// Count
        maxCountSpinner = new JSpinner(maxCount);
        JSpinner.DefaultEditor maxEditor = (JSpinner.DefaultEditor)maxCountSpinner.getEditor();
        DefaultFormatter maxFormatter = (DefaultFormatter)(maxEditor.getTextField().getFormatter());
        maxFormatter.setAllowsInvalid(false);

		JPanel maxcountPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		maxcountPanel.add(new JLabel("Max. enrolled fingerprints count"));
        maxcountPanel.add(maxCountSpinner);

        ///// Fingers
        JPanel fingersPanel = new JPanel(new GridBagLayout());
        fingersPanel.setBorder(BorderFactory.createTitledBorder("Enrolled Fingerprints"));
        for (DPFPFingerIndex finger : DPFPFingerIndex.values())
        {
            JCheckBox jCheckBox = new JCheckBox(Utilities.fingerName(finger));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            final int rows = DPFPFingerIndex.values().length / 2;
            gridBagConstraints.gridx = finger.ordinal() / rows;
            gridBagConstraints.gridy = rows - 1 - Math.abs(rows - 1 - finger.ordinal()) + gridBagConstraints.gridx;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            fingersPanel.add(jCheckBox, gridBagConstraints);
            checkBoxes.put(finger, jCheckBox);

            final DPFPFingerIndex dummyFinger = finger;
            jCheckBox.addActionListener(new ActionListener()
            {
                DPFPFingerIndex index;
                {
                    index = dummyFinger;
                }
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    if (cb.isSelected()) {
    					JOptionPane.showMessageDialog(MainForm2.this,
    							"To enroll the finger, click Enroll Fingerprints.", "Fingerprint Enrollment",
    							JOptionPane.INFORMATION_MESSAGE);
                    	cb.setSelected(false);
//                      templates.put(index, fakeTemplate);
                    } else {
    					if (JOptionPane.showConfirmDialog(MainForm2.this,
    		            		"Are you sure you want to delete the " + Utilities.fingerprintName(index) + "?", "Fingerprint Enrollment",
    							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
    					)
    						templates.remove(index);
    					else
                        	cb.setSelected(true);
                    }
                    UpdateUI();
                }
            });
        }

        final JCheckBox enforceFailure = new JCheckBox("Enforce enrollment or unenrollment failure");
        enforceFailure.setAlignmentX(CENTER_ALIGNMENT);
        enforceFailure.setHorizontalTextPosition(SwingConstants.LEADING);


        ///// Button
   EnrollmentDialog enroll_dialog =  new EnrollmentDialog(MainForm2.this,
           				maxCount.getNumber().intValue(),
           				enforceFailure.isSelected() ? "Just because I'm not in a mood." : null,
           				templates
           			); 
   
        enrollButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
           		/**new EnrollmentDialog(MainForm2.this,
           				maxCount.getNumber().intValue(),
           				enforceFailure.isSelected() ? "Just because I'm not in a mood." : null,
           				templates
           			).setVisible(true);**/
                       
                      enroll_dialog.setVisible(true);
                       // templates.deserialize(diag.get_ehg());
                       
                        System.out.println("Templates enum size in main_form2: "+enroll_dialog.templates.size());
                     System.out.println("Templates contains in main_form2: "+enroll_dialog.templates.get(DPFPFingerIndex.RIGHT_THUMB));
                       
                       
                     
                      update_fingerprint( enroll_dialog.templates.get(DPFPFingerIndex.RIGHT_THUMB) ,enroll_dialog.get_ehg() ,  visitor_ids);
            	UpdateUI();
                  //update_fingerprint( enroll_dialog.get_unserialized() ,enroll_dialog.get_ehg() ,  visitor_ids);
            }
        });
        enrollButton.setAlignmentX(CENTER_ALIGNMENT);

        enrollmentConfigPanel.add(maxcountPanel);
        enrollmentConfigPanel.add(fingersPanel);
        enrollmentConfigPanel.add(enforceFailure);
        enrollmentConfigPanel.add(Box.createVerticalStrut(4));
        enrollmentConfigPanel.add(enrollButton);
        enrollmentConfigPanel.add(Box.createVerticalStrut(4));

        //// Verification Panel

        JPanel verificationConfigPanel = new JPanel();
        verificationConfigPanel.setBorder(BorderFactory.createTitledBorder("Verification"));
        verificationConfigPanel.setLayout(new BoxLayout(verificationConfigPanel, BoxLayout.Y_AXIS));

        ///// False Accept Rate
        JPanel farPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints;

        // FAR requested
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        farPanel.add(new JLabel("False Accept Rate (FAR) requested: "), gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 0, 0, 4);
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        farRequestedSpinner = new JSpinner(farRequested);
        farRequestedSpinner.setPreferredSize(new Dimension(100, 20));
        JSpinner.DefaultEditor farEditor = (JSpinner.DefaultEditor)farRequestedSpinner.getEditor();
        DefaultFormatter farFormatter = (DefaultFormatter)(farEditor.getTextField().getFormatter());
        farFormatter.setAllowsInvalid(false);
        
        farPanel.add(farRequestedSpinner, gridBagConstraints);

        // FAR achieved
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0, 4, 0, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        farPanel.add(new JLabel("False Accept Rate (FAR) achieved: "), gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0, 0, 0, 4);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        farAchieved = new JTextField();
        farAchieved.setEditable(false);
        farAchieved.setPreferredSize(new Dimension(100, 20));
        farPanel.add(farAchieved, gridBagConstraints);

        fingerMatched = new JCheckBox("Fingerprint matched");
        fingerMatched.setEnabled(false);
        fingerMatched.setAlignmentX(CENTER_ALIGNMENT);
        fingerMatched.setHorizontalTextPosition(SwingConstants.LEADING);
        
        ///// Button
        verifyButton.addActionListener(new ActionListener()
        {
            //DPFPTemplate tempfds = verify_fingerprint(visitor_ids);
            //DPFPTemplate tempfds = DPFPGlobal.getTemplateFactory().createTemplate();
            //tempfds.deserialize(ree);
         
            
            public void actionPerformed(ActionEvent e) {
                try {
                    DPFPTemplate tempfds =  verify_fingerprint(visitor_ids);
                    
                	VerificationDialog dlg = new VerificationDialog(ef,gate,visitor_ids, tempfds,MainForm2.this, templates, farRequested.getNumber().intValue());
            		dlg.addPropertyChangeListener(new PropertyChangeListener()
            		{
            			public void propertyChange(final PropertyChangeEvent e) {
            				String name = e.getPropertyName();
            				if (VerificationDialog.FAR_PROPERTY.equals(name)) {
              			        farAchieved.setText("" + (Integer)e.getNewValue());
            				} else
            				if (VerificationDialog.MATCHED_PROPERTY.equals(name)) {
            					fingerMatched.setSelected((Boolean)e.getNewValue());
                                                System.out.println("fingerMatched.setSelected((Boolean)e.getNewValue());");
            				}
            			}
            		});
                	dlg.setVisible(true);
                } catch (Exception ex) {
                    farRequestedSpinner.requestFocusInWindow();
                }
            }
        });
        verifyButton.setAlignmentX(CENTER_ALIGNMENT);

        verificationConfigPanel.add(farPanel);
        verificationConfigPanel.add(fingerMatched);
        verificationConfigPanel.add(Box.createVerticalStrut(4));
        verificationConfigPanel.add(verifyButton);
        verificationConfigPanel.add(Box.createVerticalStrut(4));

        //// Main frame

        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
        dummy.add(Box.createVerticalStrut(4));
        dummy.add(enrollmentConfigPanel);
        dummy.add(verificationConfigPanel);
        dummy.add(Box.createVerticalStrut(4));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(Box.createHorizontalStrut(4));
        add(dummy);
        add(Box.createHorizontalStrut(4));
    	
        pack();
        setLocationRelativeTo(null);
        UpdateUI();
        setVisible(true);
        
        
        verifyButton.setEnabled(true);// added this myself
        
        
        enrollButton.requestFocusInWindow();
    }
    
    private void UpdateUI() {
    	// update enrolled fingers checkboxes
                verifyButton.setEnabled(!templates.isEmpty());

   
    }
    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
          //  MainForm2 ui =    new MainForm2(ef, gate,visitor_ids); 
               // ui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                //ui.setVisible(true);
            }
        });
    }

}
