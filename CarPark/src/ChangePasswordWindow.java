import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class ChangePasswordWindow extends JFrame{

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;
    private JLabel lblConfirm;
    private JLabel lblOldPassword;
    private JTextField textField_1;
    private JTextField textField_2;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePasswordWindow frame = new ChangePasswordWindow(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ChangePasswordWindow(String name) {
		setBounds(450, 360, 634, 232);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JPasswordField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textField.setBounds(233, 51, 377, 31);
        contentPane.add(textField);
        textField.setColumns(10);
        
        lblEnterNewPassword = new JLabel("Enter new password:");
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEnterNewPassword.setBounds(29, 43, 194, 47);
        contentPane.add(lblEnterNewPassword);
        
        lblConfirm = new JLabel("Confirm new password:");
        lblConfirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblConfirm.setBounds(10, 84, 237, 47);
        contentPane.add(lblConfirm);
        
        lblOldPassword = new JLabel("Enter old password:");
        lblOldPassword.setHorizontalAlignment(SwingConstants.LEFT);
        lblOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblOldPassword.setBounds(39, 2, 184, 47);
        contentPane.add(lblOldPassword);
        
        textField_1 = new JPasswordField();
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textField_1.setColumns(10);
        textField_1.setBounds(233, 10, 377, 31);
        contentPane.add(textField_1);
        
        textField_2 = new JPasswordField();
        textField_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textField_2.setColumns(10);
        textField_2.setBounds(233, 92, 377, 31);
        contentPane.add(textField_2);

        JButton btnSearch = new JButton("Enter");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	String Old = textField_1.getText();
                String New = textField.getText();
                String Confirm = textField_2.getText();
                String Current = null;
                String id = LoginWindow.getId();
                
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");
                    
                    PreparedStatement st = (PreparedStatement) connection
                    		.prepareStatement("Select password from account where user_id=?");
                    
                    st.setString(1, id);
                    ResultSet rs = st.executeQuery();
                    if(rs.next()) {
                    	Current = rs.getString("password");
                    }
                    
                    connection.close();

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                
                if(Current.equals(Old)) {
                	JOptionPane.showMessageDialog(btnSearch, "The new password can't be the same as the old password");
                }else if(Old.equals(New)) {
                	JOptionPane.showMessageDialog(btnSearch, "The password can't be the same");
                } else if(!(New.equals(Confirm))) {
                	JOptionPane.showMessageDialog(btnSearch, "The passwords don't match");
                } else {
                	try {
                        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");

                        PreparedStatement st1 = (PreparedStatement) con
                            .prepareStatement("Update account set password=? where user_id=?");

                        st1.setString(1, New);
                        st1.setString(2, id);
                        st1.executeUpdate();
                        JOptionPane.showMessageDialog(btnSearch, "Password has been successfully changed");
                        dispose();
                        con.close();

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(243, 141, 112, 38);
        contentPane.add(btnSearch);
	}
}
