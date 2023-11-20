package twitter2;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Login_window{
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement psmt = null;
	
	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_window window = new Login_window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login_window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/twitter_team3";
            String user = "root", password = "1619";
            
            con = DriverManager.getConnection(url, user, password);
            System.out.println(con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt = null;
        rs = null;
        
		frame = new JFrame();
		ImagePanel imagepanel = new ImagePanel(new ImageIcon("C:/Users/김민재2/eclipse-workspace/twitter2/Image/test.png").getImage());
		//imagepanel.setBounds(0, 1, 952, 560);
		frame.setSize(imagepanel.getWidth(), imagepanel.getHeight());
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(imagepanel);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		lblNewLabel.setBounds(92, 282, 93, 35);
		imagepanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LOG IN");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_1.setBounds(164, 104, 152, 62);
		imagepanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(60, 327, 152, 35);
		imagepanel.add(lblNewLabel_2);
		
		JTextField txtID = new JTextField(15);
		txtID.setBounds(220, 289, 172, 21);
		imagepanel.add(txtID);
		txtID.setColumns(10);
		
		JPasswordField txtPass = new JPasswordField(15);
		txtPass.setBounds(220, 334, 172, 21);
		imagepanel.add(txtPass);
		txtPass.setColumns(10);
		
		JButton logbtn = new JButton("Login");
		logbtn.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 15));
		logbtn.setBounds(178, 427, 93, 23);
		imagepanel.add(logbtn);
		
		logbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = con.createStatement();
                    String user_id = txtID.getText();
                    String user_pw = txtPass.getText();
                    String s1 = "select user_id from user where user_id = \"" + user_id + "\" and user_pw = \"" + user_pw + "\"";
                    rs = stmt.executeQuery(s1);

                    if (rs.next()) {
                    	JOptionPane.showMessageDialog(null,"Logged in!");
                    	frame.dispose();
                    	new Main();
                    	
                    } else {
                        JOptionPane.showMessageDialog(null,"Wrong ID / Password. Please try again!");
                    }
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
        });
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
