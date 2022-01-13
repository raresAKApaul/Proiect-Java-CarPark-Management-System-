import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

public class OccupyWindows extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OccupyWindows frame = new OccupyWindows();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public OccupyWindows() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 190, 398, 230);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        String[] parkingSpot = {"A1", "A2", "A3", "B1", "B2", "B3"};
        JComboBox<String> comboBox = new JComboBox(parkingSpot);
        comboBox.setEditable(false);
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        comboBox.setBounds(219, 72, 50, 35);
        contentPane.add(comboBox);
        
        JLabel lblPlate = new JLabel("License plate: ");
        lblPlate.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPlate.setBounds(10, 19, 130, 43);
        contentPane.add(lblPlate);
        
        JLabel lblChooseTheParking = new JLabel("Parking spot:");
        lblChooseTheParking.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblChooseTheParking.setBounds(10, 68, 117, 43);
        contentPane.add(lblChooseTheParking);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setColumns(10);
        textField.setBounds(150, 11, 228, 50);
        contentPane.add(textField);
        
        JButton btnDone = new JButton("Done");
        btnDone.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String id = LoginWindow.getId();
        		String parkSpot = parkingSpot[comboBox.getSelectedIndex()];
        		String licensePlate = textField.getText();
        		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm::ss");  
        		String now = dtf.format(LocalDateTime.now());  
        		
        		HomeWindow.setReservation(true);
        		
        		try {
        			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");

                    String query = "INSERT INTO occupied (spot, user_id, license_plate, started, ended) values('" + parkSpot + "','" + id+ "','" + licensePlate + "','" + now + "'," + null + ")";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    
                    JOptionPane.showMessageDialog(btnDone, x);
                    
                    connection.close();
                    
                    dispose();
        		} catch (Exception exception) {
                    exception.printStackTrace();
                }
        	}
        });
        btnDone.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnDone.setBounds(91, 118, 204, 50);
        contentPane.add(btnDone);
        
        
	}
}
