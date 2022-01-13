import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;


public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String id = null;
	
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 473, 592);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lblNewLabel.setBounds(177, 10, 107, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(159, 91, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(159, 169, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(10, 99, 139, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(10, 177, 139, 52);
        contentPane.add(lblPassword);
        
        String[] userType = {"User", "Admin"};
        JComboBox<String> comboBox = new JComboBox(userType);
        comboBox.setEditable(false);
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        comboBox.setBounds(149, 368, 162, 35);
        contentPane.add(comboBox);
        

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(149, 266, 162, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                
                if(comboBox.getSelectedItem().toString().equals("User")) {
                	try {
                        Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");

                        
                        PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Select user_id, user_name, password from account where user_name=? and password=?");
                        
                        st.setString(1, userName);
                        st.setString(2, password);
                        ResultSet rs = st.executeQuery();
                        
                        if (rs.next()) {
                        	id = rs.getString("user_id");
                            dispose();
                            HomeWindow ah = new HomeWindow(userName);
                            ah.setTitle("Welcome");
                            ah.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                        }
                        connection.close();
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                } else {
                	if(userName.equals("admin") && password.equals("admin")) {
                		dispose();
                		AdminWindow ah = new AdminWindow();
                		ah.setVisible(true);
                	} else {
                		JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                	}
                }
            }
        });

        contentPane.add(btnNewButton);
        
        JButton btnRegister = new JButton("Register instead");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		RegistrationWindow ah = new RegistrationWindow();
        		ah.setVisible(true);
        	}
        });
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnRegister.setBounds(249, 502, 210, 29);
        contentPane.add(btnRegister);
        }
	
	public static String getId() {
		return id;
	}
}
