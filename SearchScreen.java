package twitter2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class SearchScreen {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String url = "jdbc:mysql://localhost/twitter_team3";
	private String user = "root", password = "1619";  	// 오류 시 개인 root password 사용해 볼 것
	
	public SearchScreen() {
		JFrame frame = new JFrame("Search Screen");
		ImagePanel imagePanel = new ImagePanel(new ImageIcon("C:/Users/김민재2/eclipse-workspace/twitter2/Image/test.png").getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(imagePanel.getWidth(), imagePanel.getHeight());
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Find User");
		JTextField textField = new JTextField(15);
		JButton search = new JButton("Search");
		JTextArea textArea = new JTextArea();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		try {
			stmt = con.createStatement();
			
			String s1 = "select nickname from user order by rand()";
			
			rs = stmt.executeQuery(s1);
			
			while (rs.next()) {
				textArea.append(rs.getString("nickname")+ "(@" +rs.getString("user_id") + ")" + "\n");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stmt = con.createStatement();
					
					String searchUser = textField.getText();
					String s2;
					
					if (searchUser.charAt(0) == '@') s2 = "select * from user where user_id = \"" + searchUser.substring(1) + "\"";
					else s2 = "selct * from user where nickname = \"" + searchUser + "\"";
					
					rs = stmt.executeQuery(s2);
					
					while (rs.next()) {
						textArea.append(rs.getString("nickname") + "\n");
					}
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		panel.add(label);
		panel.add(textField);
		panel.add(search);
		
		frame.getContentPane().add(BorderLayout.NORTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, textArea);
		frame.setVisible(true);
	}
}
