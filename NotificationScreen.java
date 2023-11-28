package twitter2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class NotificationScreen {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String url = "jdbc:mysql://localhost/twitter_team3";
	private String user = "root", password = "1619";  	// 오류 시 개인 root password 사용해 볼 것
	private String user_id;
	
	public NotificationScreen(String userId) {
		this.user_id = userId;
		
		JFrame frame = new JFrame("Notification Screen");
		ImagePanel imagePanel = new ImagePanel(new ImageIcon("C:/Users/김민재2/eclipse-workspace/twitter2/Image/test.png").getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(imagePanel.getWidth(), imagePanel.getHeight());
		
		JPanel topPanel = new JPanel();
		JPanel notiPanel = null;
		JTextArea textArea = new JTextArea();
		JButton refresh = new JButton("Refresh");
		
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
			
			String s1 = "select * from follow where follower_id = \"" + user_id + "\" and approve_YN = \"N\"";
			rs = stmt.executeQuery(s1);
			
			while (rs.next()) {
				textArea.append(rs.getString("nickname") + "(@" + rs.getString("user_ID") + ")\n");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stmt = con.createStatement();
					
					String s1 = "select * from follow where follower_id = \"" + user_id + "\" and approve_YN = \"N\"";
					rs = stmt.executeQuery(s1);
					
					while (rs.next()) {
						textArea.append(rs.getString("nickname") + "(@" + rs.getString("user_ID") + ")\n");
					}
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		topPanel.add(refresh);
		
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
		frame.getContentPane().add(BorderLayout.CENTER, notiPanel);
		frame.setVisible(true);
	}
}
