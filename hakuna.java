/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hakuna_matata;

import static hakuna_matata.matata.raise;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NNAMDI
 */
//Our model class, it serves the controller class
public class hakuna {

    static enrollment_module_panel enroll;
    static gate_module_panel gate;
    static Connection conn;
    static boolean state;
    static ResultSet rs;
    finger_print_gate_module finger;
    String row_approve_clicked;
    MainForm2 many;
    Printout printout;
    int row ;
    int col ;
               
    hakuna() {
       // many = new MainForm2("1");
       
    }

    //retrieve value for name in enrollment
    public String get_enrollment_name(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        String mh = enroll.jTextField1.getText();
        return mh;
    }

    //retrieve value for designation in enrollment
    public String get_designation_name(enrollment_module_panel enroll) {
        // enroll = new enrollment_module_panel();
        String mh = enroll.jTextField2.getText();
        return mh;
    }

    //retrieve value for designation in enrollment
    public String get_id_number(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        String mh = enroll.jTextField3.getText();
        return mh;
    }

    //retrieve value for fingerprint in enrollment
    public String get_fingerprint(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        String mh = enroll.jLabel6.getText();
        return mh;
    }

    //retrieve value for photo in enrollment
    public String get_photo(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        String mh = enroll.jLabel7.getText();
        return mh;
    }

    //setting value for enrollment in enrollment
    public void set_enrollment_name(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        enroll.jTextField1.setText("");
    }

    //setvalue for designation in enrollment
    public void set_designation_name(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        enroll.jTextField2.setText("");

    }

    //setting value for fingerprint in enrollment
    public void set_fingerprint(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        enroll.jLabel6.setText("Fingerprint will appear here ");

    }

    //retrieve value for photo in enrollment
    public void set_photo(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        enroll.jLabel7.setText("Photo will appear here ");

    }

    //se5 value for designation in enrollment
    public void set_id_number(enrollment_module_panel enroll) {
        //enroll = new enrollment_module_panel();
        enroll.jTextField3.setText("");

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

    //Methods for enrollment module
    //Retrieve all the values in enroll
    public static ResultSet retrieve_value_enroll() {

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `enrollment` group by visitor_id ";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            rs = preparedStmt.executeQuery();
            state = true;
            //conn.close();

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(enroll, e.getMessage());

            state = false;
        };
        return rs;

    } //End of retrieve all the dates in internal audit

    //insert values name, db into designation  
    public static boolean insert_value_enroll(String designation, String fingerprint,
            String photo, String id_number, String name, enrollment_module_panel enroll) {

        try {
            //create a mysql connection
            create_mysql_connection();

            String designations = designation;
            String fingerprints = fingerprint;
            String photos = photo;
            String id_numbers = id_number;
            String names = name;

            // Sql query
            String query = "insert into enrollment(designation,fingerprint,photo,id_number,name)"
                    + "values (?,?,?,?,?)";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString(1, designations);
            preparedStmt.setString(2, fingerprints);
            preparedStmt.setString(3, photos);
            preparedStmt.setString(4, id_numbers);
            preparedStmt.setString(5, names);

            preparedStmt.execute();
            //conn.close();

            state = true;

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(enroll, e.getMessage());

            state = false;
        }
        return state;
    } //End of insert_value_enroll

    // deletes value based on visitor_id
    public static boolean delete_value_enroll(String visitor_id, enrollment_module_panel enroll) {

        try {
            //create a mysql connection
            create_mysql_connection();

            String visitor_ids = visitor_id;

            // Sql query DELETE FROM `enrollment` WHERE visitor_id = 1
            String query = " DELETE FROM `enrollment` WHERE visitor_id =?";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, visitor_ids);
            preparedStmt.executeUpdate();
            JOptionPane.showMessageDialog(enroll, "Enrolled data deleted successfully");

            state = true;

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(enroll, e.getMessage());

            state = false;
        }
        return state;
    } //End of insert_value_enroll

    //End of methods for enrollment module
    // Methods for gate_module
    //Retrieve data for the gate table.load_gate_table relies on it.
   
    
    // Refresh table approval
            //Refreshes the table
    public void refresh_table_approval(String approver, approval_module_panel modu, Object[] rowData,Object[] columnsName, DefaultTableModel model){
      
        columnsName = new Object[8];

        columnsName = new Object[8];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Date Raised";
        columnsName[5] = "Status";
        columnsName[6] = "Name of Raisee";
        columnsName[7] = "Visitor ID";

        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        //model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        
        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);
      

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where approver = ? order by visitor_id DESC";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, approver);

            rs = preparedStmt.executeQuery();
            int counter = 0;
            // retrieve the blob fingerprint image of the user in the database.
            while (rs.next()) {

                // populate table here based on fingerprint
                rowData[0] = counter;
                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("date_time_raised");
                rowData[5] = rs.getString("status");
                rowData[6] = rs.getString("name_of_raisee");
                rowData[7] = rs.getString("visitor_id");

                model.addRow(rowData);
                counter++;

            }

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            //state = false;jComboBox1.removeAllItems();
       
        };
        modu.jTable1.setModel(model);
    }
    //Refreshes the table security
    
        //Refreshes the table
    public void refresh_table_security(String date_of_visit, Object[] rowData,Object[] columnsName, DefaultTableModel model){
      
        columnsName = new Object[9];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Time Raised";
        columnsName[5] = "Status";
        columnsName[6] = "Approving Authority";
        columnsName[7] = "Security Status";
        columnsName[8] = "Visitor ID";

        //model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        
        try {

            rs = retrieve_all_security_data(date_of_visit);
            System.err.println("Reached here");
            int counter = 1;
            while (rs.next()) {
                rowData[0] = counter;
                System.err.println(rs.getString("name_of_visitor"));

                 rowData[0] = counter;
                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("date_time_raised");
                rowData[5] = rs.getString("status");
                rowData[6] = rs.getString("approver");
                rowData[7] = rs.getString("security_approval_staus");
                rowData[8] = rs.getString("visitor_id");

                model.addRow(rowData);
                counter++;

            }
           
             gate.jTable1.setModel(model);
             gate.jTable1.revalidate();
             
        } catch (Exception e) {
            System.err.println("Got an exception from hakuna load_gate_table() ");
            System.err.println(e.getMessage());

        }
    }
    
    //Refreshes the table
    public void refresh_table(String date_of_visit, Object[] rowData,Object[] columnsName, DefaultTableModel model){
        
                columnsName = new Object[9];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Time In";
        columnsName[5] = "Time Out";
        columnsName[6] = "Approving Authority";
        columnsName[7] = "Visitor ID";
        columnsName[8] = "Pass Type";
        //model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        
        try {

            rs = retrieve_all_gate_data(date_of_visit);
            System.err.println("Reached here");
            int counter = 1;
            while (rs.next()) {
                rowData[0] = counter;
                System.err.println(rs.getString("name_of_visitor"));

                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("gate_time_in");
                rowData[5] = rs.getString("gate_time_out");
                rowData[6] = rs.getString("approver");
                rowData[7] = rs.getString("visitor_id");
                rowData[8] = rs.getString("pass_type");
                model.addRow(rowData);
                counter++;

            }
           
             gate.jTable1.setModel(model);
             gate.jTable1.revalidate();
             
        } catch (Exception e) {
            System.err.println("Got an exception from hakuna load_gate_table() ");
            System.err.println(e.getMessage());

        }
    }
    
    public static ResultSet retrieve_all_gate_data(String date_of_visit) {

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where date_time_raised = ? and security_approval_staus ='approved' order by visitor_id";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, date_of_visit);

            rs = preparedStmt.executeQuery();
            state = true;

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            state = false;
        };
        return rs;

    } //End of retrieve all gate data using date

    int gate_count = 0;
 
    //Loads the value of the visitor's table based on the date
 
    public void load_gate_table(gate_module_panel gate) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
        Date date = new Date();

        final String date_of_visit = df.format(date);

        if (gate_count != 0) {
            String day = gate.jComboBox2.getSelectedItem().toString();
            String month = gate.jComboBox3.getSelectedItem().toString();
            String year = gate.jComboBox4.getSelectedItem().toString();
           // date_of_visit = day + "/" + month + "/" + year;
            gate_count++;
        }

        System.out.println(date_of_visit);

        DefaultTableModel model;
        model = new DefaultTableModel();
        Object[] columnsName;
        Object[] rowData;
        ResultSet rs;

        columnsName = new Object[9];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Time In";
        columnsName[5] = "Time Out";
        columnsName[6] = "Approving Authority";
        columnsName[7] = "Visitor ID";
         columnsName[8] = "Pass Type";
        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        try {

            rs = retrieve_all_gate_data(date_of_visit);
            System.err.println("Reached here");
            int counter = 1;
            while (rs.next()) {
                rowData[0] = counter;
                System.err.println(rs.getString("name_of_visitor"));

                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("gate_time_in");
                rowData[5] = rs.getString("gate_time_out");
                rowData[6] = rs.getString("approver");
                rowData[7] = rs.getString("visitor_id");
                rowData[8] = rs.getString("pass_type");
                System.out.println("Pass Type: "+rowData[8] );
                model.addRow(rowData);
                counter++;

            }

        } catch (Exception e) {
            System.err.println("Got an exception from hakuna load_gate_table() ");
            System.err.println(e.getMessage());

        }

        gate.jTable1.setModel(model);

        gate.jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancel");
                System.exit(0);
            }
        });
       
        gate.jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Table refreshed");
                //refresh table
                                     DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
            Date date = new Date();

        final String date_of_visits = df.format(date);
                refresh_table( date_of_visits,  rowData, columnsName,  model);
                
               gate.jTable1.revalidate();
            }
        });

              gate.jButton4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              // many.setVisible(false);Printout(String name, String pass_type, String to_see_whom, String picture, String visitor_id, String pass_color)
              
              String name = gate.jTable1.getValueAt(row, 1).toString();
              String pass_type = gate.jTable1.getValueAt(row, 8).toString();
              String to_see_whom = gate.jTable1.getValueAt(row, 3).toString();
              String picture = gate.jTable1.getValueAt(row, 7).toString();
              String visitor_id = gate.jTable1.getValueAt(row, 7).toString();
              String pass_color= gate.jTable1.getValueAt(row, 8).toString();
              
              printout = new Printout(name,pass_type,to_see_whom,picture,visitor_id,pass_color);
              printout.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
              printout.setVisible(true);
            }
        });
        
               
        gate.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("Na wah ooo");
                 row = gate.jTable1.rowAtPoint(e.getPoint());
               col = gate.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
                //int col = 8;

                     DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
            Date date = new Date();

        final String date_of_visitss = df.format(date);

                String visitor_idsss = gate.jTable1.getValueAt(row, 7).toString();
                System.out.println(visitor_idsss);
              
                capture_fingerprint(e,many, visitor_idsss);
                DefaultTableModel models = model;
        
                Object[] columnsNames = columnsName ;
                Object[] rowDatas = rowData;
                //String date_of_visits = date_of_visit;
                
               refresh_table( date_of_visitss,  rowData, columnsName,  model);
               gate.jTable1.revalidate();
               
                //System.out.println("Value in the cell clicked: " + gate.jTable1.getValueAt(row, col).toString());

            }
        });

     /**    gate.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = gate.jTable1.rowAtPoint(e.getPoint());
                int col = gate.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
                //int col = 8;

              
                System.out.println("Value in the cell clicked: " + row );

            }
        });**/
        close_mysql_connection();
    }
// End of load_gate_table

// Visitor row fingerprint verification-used by  gate module to bring up the fingerprint gate module prefilled
    public void visitor_row_fingerprint_verification(String row_number, String visitor_id, MainForm2 frame) {

        String row_numbers = row_number;
        String visitor_ids = visitor_id;
       // frame = new MainForm2(e,gate,"1");

        frame.setDefaultCloseOperation(finger.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }
//  end of Visitor row fingerprint verification

    //capture_fingerprint- used by the fingerprint gate module to capture the fingerprint
    public void capture_fingerprint(java.awt.event.MouseEvent e, MainForm2 many, String visitor_id) {
        many = new MainForm2(e,gate,visitor_id);
         many.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        many.setResizable(false);
        //many.setVisible(false);
        
        int row = gate.jTable1.rowAtPoint(e.getPoint());
        int col = gate.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
        //String time_out = gate.jTable1.getValueAt(row, 5).toString();
        String time_in ="";
        String time_out ="";
        

        
       if(gate.jTable1.getValueAt(row, 4).toString().isEmpty()){
        many.enrollButton.setVisible(true);
        }
        
        else{ many.enrollButton.setVisible(false);}
        
        if(gate.jTable1.getValueAt(row, 5).toString().equals("No value")){
        System.out.println("No value");
        }
        
        time_out = gate.jTable1.getValueAt(row, 5).toString();
             time_in = gate.jTable1.getValueAt(row, 4).toString();
        if(time_out.contains(":")){
        many.setVisible(false);}
        
        if(!time_out.contains(":")){
        many.setVisible(true);
        }

    } // End of capture_fingerprint 

    //capture_fingerprint- used by the fingerprint gate module to capture the fingerprint
    public void capture_photo() {

    } // End of capture_photo

    public void gate_visitor_exit_building() {

    } // gate_visitor_exit_building

    //End of methods of gate module
    //Beginning of security module
    //approve_decline for approval module 2
    public static String approve_module2__approve_decline_security(boolean approve_decline, String visitor_id) {
        boolean approve_declines = approve_decline;
        String value = "empty";

        if (approve_declines == true) {
            value = "approved";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET security_approval_staus = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        if (approve_declines == false) {

            value = "declined";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET security_approval_staus = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        return value;
    }
    // Loads approval module based on the visitor_id for security

    public void load_approval_module_security(String visitor_id) {
        approval_module_2 approve = new approval_module_2();

        ResultSet rs;

        try {

            rs = retrieve_all_load_approval_module2(visitor_id);

            while (rs.next()) {
                approve.jTextField1.setText(rs.getString("name_of_visitor"));
                approve.jTextField2.setText(rs.getString("name_of_raisee"));
                approve.jTextField3.setText(rs.getString("from_where"));
                approve.jTextField4.setText(rs.getString("to_see_whom"));
                approve.jTextField5.setText(rs.getString("raisee_id"));
                approve.jTextField6.setText(rs.getString("date_of_visit"));
                approve.jTextField7.setText(rs.getString("date_time_raised"));

                approve.jTextField1.setEditable(false);
                approve.jTextField2.setEditable(false);
                approve.jTextField3.setEditable(false);
                approve.jTextField4.setEditable(false);
                approve.jTextField5.setEditable(false);
                approve.jTextField6.setEditable(false);
                approve.jTextField7.setEditable(false);

                approve.jComboBox2.removeAllItems();
                approve.jComboBox2.addItem(rs.getString("former_employee"));
                approve.jComboBox3.removeAllItems();
                approve.jComboBox3.addItem(rs.getString("purpose_of_visit"));
                approve.jComboBox1.removeAllItems();
                approve.jComboBox1.addItem(rs.getString("pass_type"));

                approve.jComboBox2.setEditable(false);
                approve.jComboBox1.setEditable(false);
                approve.jComboBox3.setEditable(false);
            }

        } catch (Exception e) {
            System.err.println("Got an exception from hakuna load_approval_module_security() ");
            System.err.println(e.getMessage());

        }
        approve.setVisible(true);
        approve.setResizable(false);

        approve.jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                approve_module2__approve_decline_security(true, visitor_id);
                approve.setVisible(false);
            }
        });

        //For cancel button of enrollment_module
        approve.jButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                approve_module2__approve_decline_security(false, visitor_id);
                approve.setVisible(false);
            }
        });
        close_mysql_connection();
    }
//Beginning of security module
    //Retrieve data for the security table. populate_table_security relies on it.

    public static ResultSet retrieve_all_security_data(String date_of_visit) {

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where date_time_raised = ? order by visitor_id";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, date_of_visit);

            rs = preparedStmt.executeQuery();
            state = true;

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            state = false;
        };
        return rs;

    } //End of retrieve all security data using date

    //populate security table
    int security_count = 0;

    // to test if charater is numeric
    public static boolean isNumeric(final String str) {
        // or empty
        if (str == null || str.length() == 0) {
            return false;

        }
        return str.chars().allMatch(Character::isDigit);
    }

    public void populate_table_security(security_module_panel security) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
        Date date = new Date();

        String date_of_visit = df.format(date);
        System.out.println(date_of_visit);

        if (security_count != 0) {
            String day = security.jComboBox2.getSelectedItem().toString();
            String month = security.jComboBox3.getSelectedItem().toString();
            String year = security.jComboBox4.getSelectedItem().toString();
            date_of_visit = day + "/" + month + "/" + year;
            security_count++;
        }

        DefaultTableModel model;
        model = new DefaultTableModel();
        Object[] columnsName;
        Object[] rowData;
        ResultSet rs;

        columnsName = new Object[9];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Date Raised";
        columnsName[5] = "Status";
        columnsName[6] = "Approving Authority";
        columnsName[7] = "Security Status";
        columnsName[8] = "Visitor ID";

        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);
        try {

            rs = retrieve_all_security_data(date_of_visit);

            int counter = 1;
            while (rs.next()) {
                rowData[0] = counter;
                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("date_time_raised");
                rowData[5] = rs.getString("status");
                rowData[6] = rs.getString("approver");
                rowData[7] = rs.getString("security_approval_staus");
                rowData[8] = rs.getString("visitor_id");

                model.addRow(rowData);
                counter++;
System.out.println("Row 7: "+rowData[7]);
            }

        } catch (Exception e) {
            System.err.println("Got an exception from hakuna populate_table_security() ");
            System.err.println(e.getMessage());

        }

        security.jTable1.setModel(model);

        DefaultTableModel models = model;
        
        Object[] columnsNames = columnsName ;
        Object[] rowDatas = rowData ;
        String date_of_visits = date_of_visit;
        
          security.jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Table refreshed");
                //refresh table
                refresh_table_security( date_of_visits,  rowDatas, columnsNames,  models);
                
               security.jTable1.revalidate();
            }
        });
          
          
        security.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = security.jTable1.rowAtPoint(e.getPoint());
               int col = security.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
                //int col = 7;
                
                row_approve_clicked = security.jTable1.getValueAt(row, col).toString();
                
                System.out.println("Row approved: "+row_approve_clicked);
                
                
                
                
               /*  if (staus.equals("Pending")) {
                    load_approval_module2(get_visitor_id());
                } else {
                    //JOptionPane.showMessageDialog(modu, "You have already authorized or declined this pass.");
                }*/
                 
                try{
                    
                    String security_statusss= security.jTable1.getValueAt(row, 7).toString();
                    
                    
                    System.out.println("Security status: "+security_statusss);
                    
                if(security_statusss.equals("approved")|| security_statusss.equals("declined") ){
                // do nothing
                }
                } catch(Exception exx){
                    String statusss= security.jTable1.getValueAt(row, 5).toString();
                    System.out.println("Status: "+statusss);
                     if(statusss.equals("approved") ){
                // load approval module security
                load_approval_module_security(get_visitor_id());
                }
                
                }

                //row_approve_clicked = security.jTable1.getValueAt(row, col).toString();
                //String staus = security.jTable1.getValueAt(row, 5).toString();

               

                System.out.println("Value in the cell clicked: " + get_visitor_id());

            }
        });
        close_mysql_connection();

    }

//security_approve_decline
// Returns true if approved and false if declined, don't use the security guard's fingerprint for verification yet.
    public static String security_approve_decline(boolean approve_decline, String visitor_id) {
        boolean approve_declines = approve_decline;
        String value = "empty";

        if (approve_declines == true) {
            value = "approved";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET security_approval_status = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        if (approve_declines == false) {

            value = "declined";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET security_approval_status = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        return value;
    } //End of  security_approve_decline
    //End of methods of security module

// Beginning of approval module 1
    //Load table module
    public void load_table(approval_module_panel modu, String approver) {

        DefaultTableModel model;
        model = new DefaultTableModel();
        Object[] columnsName;
        Object[] rowData;
        ResultSet rs;
        int counter = 1;

        columnsName = new Object[8];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Date Raised";
        columnsName[5] = "Status";
        columnsName[6] = "Name of Raisee";
        columnsName[7] = "Visitor ID";

        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);
      

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where approver = ? order by visitor_id DESC";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, approver);

            rs = preparedStmt.executeQuery();

            // retrieve the blob fingerprint image of the user in the database.
            while (rs.next()) {

                // populate table here based on fingerprint
                rowData[0] = counter;
                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("date_time_raised");
                rowData[5] = rs.getString("status");
                rowData[6] = rs.getString("name_of_raisee");
                rowData[7] = rs.getString("visitor_id");

                model.addRow(rowData);
                counter++;

            }

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            //state = false;jComboBox1.removeAllItems();
       
        };
        modu.jTable1.setModel(model);
        modu.jComboBox1.removeAllItems();
       modu. jComboBox1.addItem(approver);
        modu. jComboBox1.setEditable(false);

        modu.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = modu.jTable1.rowAtPoint(e.getPoint());
                int col = modu.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
                //int col = 8;

                row_approve_clicked = modu.jTable1.getValueAt(row, col).toString();
                System.out.println("Value in the cell clicked: " + get_visitor_id());

                String staus = modu.jTable1.getValueAt(row, 5).toString();

                if (staus.equals("Pending")) {
                    load_approval_module2(get_visitor_id());
                } else {
                    //JOptionPane.showMessageDialog(modu, "You have already authorized or declined this pass.");
                }
            }
        });
        
       
       
        modu.jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                refresh_table_approval( approver,  modu,  rowData, columnsName,  model);
         
      
               
            }
         });
        
        close_mysql_connection();

    }

    //T reload table module
    public void reload_table(approval_module_panel modu, String approver) {

        DefaultTableModel model;
        model = new DefaultTableModel();
        Object[] columnsName;
        Object[] rowData;
        ResultSet rs;
        int counter = 1;

        columnsName = new Object[8];

        columnsName[0] = "S/N";
        columnsName[1] = "Name of Visitor";
        columnsName[2] = "From Where";
        columnsName[3] = "To see whom";
        columnsName[4] = "Time Raised";
        columnsName[5] = "Status";
        columnsName[6] = "Name of Raisee";
        columnsName[7] = "Visitor ID";

        model.setColumnIdentifiers(columnsName);
        rowData = new Object[11];
        model.setRowCount(0);

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where approver = ? order by visitor_id DESC";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, approver);

            rs = preparedStmt.executeQuery();

            // retrieve the blob fingerprint image of the user in the database.
            while (rs.next()) {

                // populate table here based on fingerprint
                rowData[0] = counter;
                rowData[1] = rs.getString("name_of_visitor");
                rowData[2] = rs.getString("from_where");
                rowData[3] = rs.getString("to_see_whom");
                rowData[4] = rs.getString("date_time_raised");
                rowData[5] = rs.getString("status");
                rowData[6] = rs.getString("name_of_raisee");
                rowData[7] = rs.getString("visitor_id");

                model.addRow(rowData);
                counter++;

            }

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            //state = false;
        };
        modu.jTable1.setModel(model);

        modu.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = modu.jTable1.rowAtPoint(e.getPoint());
                // int col = modu.jTable1.columnAtPoint(e.getPoint()); // To display value in any cell
                int col = 7;

                row_approve_clicked = modu.jTable1.getValueAt(row, col).toString();
                System.out.println("Value in the cell clicked: " + get_visitor_id());

                String staus = modu.jTable1.getValueAt(row, 5).toString();

                if (staus.equals("Pending")) {
                   // load_approval_module2(get_visitor_id());
                } else {
                    //JOptionPane.showMessageDialog(modu, "You have already authorized or declined this pass."
                    //);

                }
            }
        });

        close_mysql_connection();

    }
//End of Load table module

    //get visitor id for approval module 1
    public String get_visitor_id() {
        return row_approve_clicked;
    }// End of reload table module

    //End of approval module 1
    // Beginning of approval module 2
    //Used by load_approval_module2
    public static ResultSet retrieve_all_load_approval_module2(String visitor_id) {

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "SELECT * FROM `visitor` where visitor_id = ?";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, visitor_id);

            rs = preparedStmt.executeQuery();
            state = true;

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());

            state = false;
        };
        return rs;

    }

    // Loads approval module based on the visitor_id
    public void load_approval_module2(String visitor_id) {
        approval_module_2 approve = new approval_module_2();

        ResultSet rs;

        try {

            rs = retrieve_all_load_approval_module2(visitor_id);

            while (rs.next()) {
                approve.jTextField1.setText(rs.getString("name_of_visitor"));
                approve.jTextField2.setText(rs.getString("name_of_raisee"));
                approve.jTextField3.setText(rs.getString("from_where"));
                approve.jTextField4.setText(rs.getString("to_see_whom"));
                approve.jTextField5.setText(rs.getString("raisee_id"));
                approve.jTextField6.setText(rs.getString("date_of_visit"));
                approve.jTextField7.setText(rs.getString("date_time_raised"));

                approve.jComboBox2.removeAllItems();
                approve.jComboBox2.addItem(rs.getString("former_employee"));
                approve.jComboBox3.removeAllItems();
                approve.jComboBox3.addItem(rs.getString("purpose_of_visit"));
                approve.jComboBox1.removeAllItems();
                approve.jComboBox1.addItem(rs.getString("pass_type"));

            }

        } catch (Exception e) {
            System.err.println("Got an exception from hakuna load_approval_module2() ");
            System.err.println(e.getMessage());

        }
        approve.setVisible(true);
        approve.setResizable(false);

        approve.jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                approve_module2__approve_decline(true, visitor_id);
         
      
                approve.setVisible(false);
            }
        });

        //For cancel button of enrollment_module
        approve.jButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                approve_module2__approve_decline(false, visitor_id);
                approve.setVisible(false);
            }
        });
        close_mysql_connection();
    }

    //approve_decline for approval module 2
    public static String approve_module2__approve_decline(boolean approve_decline, String visitor_id) {
        boolean approve_declines = approve_decline;
        String value = "empty";

        if (approve_declines == true) {
            value = "approved";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET status = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        if (approve_declines == false) {

            value = "declined";

            try {
                //create a mysql connection
                create_mysql_connection();

                // Sql query
                String query = "UPDATE visitor SET status = ? WHERE visitor_id = ?";

                // Use prepared statement to set the ball roling
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, value);
                preparedStmt.setString(2, visitor_id);

                preparedStmt.executeUpdate();

            } catch (Exception e) {

                System.err.println("Got an exception");
                System.err.println(e.getMessage());
                value = e.toString();

            };

        }

        return value;
    }
    //End of approve_decline for approval module 2
    // End of approval module 2

    //Beginning of raising module
    // To get name of visitor in raising module
    public String get_raise_name_of_visitor(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField1.getText();
        return value;
    }

    //To get name of raisee
    public String get_raise_name_of_raisee(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField2.getText();
        return value;
    }

    //TO get from where
    public String get_raise_from_where(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField3.getText();
        return value;
    }
    //TO get to see whom

    public String get_raise_to_see_whom(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField4.getText();
        return value;
    }

    //To get id number of raisee
    public String get_raise_id_number_of_raisee(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField5.getText();
        return value;
    }

    //To get is a visitor a former employee
    public String get_raise_is_a_visitor_a_former_employee(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jComboBox2.getSelectedItem().toString();
        return value;
    }

    //TO get purpose of visit
    public String get_raise_purpose_of_visit(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jComboBox3.getSelectedItem().toString()+"  "+raise.jTextField7.getText();
        return value;
    }
    //To get date of intended visit

    public String get_raise_date_of_intended_visit(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jTextField6.getText();
        return value;
    }
    //To get pass type

    public String get_raise_pass_type(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jComboBox1.getSelectedItem().toString();
        return value;
    }

    //To get approver
    public String get_raise_approver(raising_module_panel raise) {
        //raise = new raising_module_panel();
        String value = raise.jComboBox4.getSelectedItem().toString();
        return value;
    }

    // To get name of visitor in raising module
    public void set_raise_name_of_visitor(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField1.setText("");

    }

    //To get name of raisee
    public void set_raise_name_of_raisee(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField2.setText("");

    }

    //TO get from where
    public void set_raise_from_where(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField3.setText("");

    }
    //TO get to see whom

    public void set_raise_to_see_whom(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField4.setText("");

    }

    //To get id number of raisee
    public void set_raise_id_number_of_raisee(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField5.setText("");

    }

    //To get date of intended visit
    public void set_date_of_intended_visit(raising_module_panel raise) {
        //raise = new raising_module_panel();
        raise.jTextField6.setText("");

    }

    //insert values name, db into designation  
    public static boolean insert_value_raise(String name_of_visitor, String name_of_raisee,
            String from_where, String to_see_whom,
            String id_number_of_raisee, String former_employee,
            String purpose_of_visit, String date_of_intended_visit, String pass_type,
            String approver, raising_module_panel raise, String date_time_raised, String status) {

        try {
            //create a mysql connection
            create_mysql_connection();

            // Sql query
            String query = "insert into visitor(name_of_visitor,name_of_raisee,raisee_id,from_where, \n"
                    + "\n"
                    + "to_see_whom,former_employee,purpose_of_visit,date_of_visit, \n"
                    + "\n"
                    + "pass_type,approver, date_time_raised,status )"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString(1, name_of_visitor);
            preparedStmt.setString(2, name_of_raisee);
            preparedStmt.setString(3, id_number_of_raisee);
            preparedStmt.setString(4, from_where);
            preparedStmt.setString(5, to_see_whom);
            preparedStmt.setString(6, former_employee);
            preparedStmt.setString(7, purpose_of_visit);
            preparedStmt.setString(8, date_of_intended_visit);
            preparedStmt.setString(9, pass_type);
            preparedStmt.setString(10, approver);
            preparedStmt.setString(11, date_time_raised);
            preparedStmt.setString(12, status);

            preparedStmt.execute();

            JOptionPane.showMessageDialog(raise, "Pass Raised Successfully by " + name_of_raisee);

        } catch (Exception e) {

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(raise, e.getMessage());

            state = false;
        }
        return state;
    } //End of insert_value_enroll

// End of raising module
    //End of class hakuna
}
