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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.SystemColor;

public class HomeWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeWindow frame = new HomeWindow(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static boolean reserveDone;

	public static void setReservation(boolean x) {
		reserveDone = x;
	}


	public static boolean getOccupyStatus() {
		try {
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");
			String id = LoginWindow.getId();
			PreparedStatement st = (PreparedStatement) connection.prepareStatement("select ended from occupy where user_id=?");
            st.setString(1, id);
			ResultSet rs = st.executeQuery();
			if(rs == null) {
				return true;
			}
            connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reserveDone;
	}
	
	public HomeWindow(String userSes) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 620, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
				if (a == JOptionPane.YES_OPTION) {
					dispose();
					LoginWindow obj = new LoginWindow();
					obj.setVisible(true);
				}
				dispose();
				LoginWindow obj = new LoginWindow();
				obj.setVisible(true);

			}
		});
		btnNewButton.setBounds(10, 331, 104, 52);
		contentPane.add(btnNewButton);

		JButton btnChangePassword = new JButton("Change password\r\n");
		btnChangePassword.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePasswordWindow bo = new ChangePasswordWindow(userSes);
				bo.setTitle("Change Password");
				bo.setVisible(true);
			}
		});
		btnChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnChangePassword.setBounds(10, 268, 200, 52);
		contentPane.add(btnChangePassword);

		JButton btnA1 = new JButton("A1");
		btnA1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA1.setBounds(279, 81, 100, 100);
		contentPane.add(btnA1);

		JButton btnA2 = new JButton("A2");
		btnA2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA2.setBounds(389, 81, 100, 100);
		contentPane.add(btnA2);

		JButton btnA3 = new JButton("A3");
		btnA3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA3.setBounds(499, 81, 100, 100);
		contentPane.add(btnA3);

		JLabel lblNewLabel = new JLabel("Car park management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setBounds(28, 11, 554, 59);
		contentPane.add(lblNewLabel);

		JButton btnB1 = new JButton("B1");
		btnB1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB1.setBounds(279, 191, 100, 100);
		contentPane.add(btnB1);

		JButton btnB2 = new JButton("B2");
		btnB2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB2.setBounds(389, 191, 100, 100);
		contentPane.add(btnB2);

		JButton btnB3 = new JButton("B3");
		btnB3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB3.setBounds(499, 191, 100, 100);
		contentPane.add(btnB3);

		JButton btnOccupy = new JButton("Occupy a spot");
		btnOccupy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOccupy.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnOccupy.setBounds(10, 81, 200, 52);
		btnOccupy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OccupyWindows x = new OccupyWindows();
				x.setVisible(true);
			}
		});
		contentPane.add(btnOccupy);

		JButton btnReserve = new JButton("Reserve a spot");
		btnReserve.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReserve.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnReserve.setBounds(10, 205, 200, 52);
		contentPane.add(btnReserve);

		JButton btnRelease = new JButton("Release the spot");
		btnRelease.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRelease.setBackground(UIManager.getColor("Button.disabledForeground"));
		btnRelease.setBounds(10, 143, 200, 52);
		btnRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ocupat = getOccupyStatus();
				JOptionPane.showMessageDialog(btnRelease, ocupat);
				/*
				String id = LoginWindow.getId();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm::ss");  
        		String now = dtf.format(LocalDateTime.now()); 
        		
				try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/car_park", "root", "2309");
                    PreparedStatement st = (PreparedStatement) connection.prepareStatement("update occupied set ended=? where user_id=?");
                    
                    st.setString(1, now);
                    st.setString(2, id);
                    st.executeUpdate();
                    
                    connection.close();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }*/
			}
		});
		contentPane.add(btnRelease);

		/*if (reserveDone == false) {
			btnOccupy.enable(true);
			btnRelease.enable(false);
		} else {
			btnOccupy.enable(false);
			btnRelease.enable(true);
		}*/
	}
}
