package twitter2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QnAScreen {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String url = "jdbc:mysql://localhost/twitter_team3";
	private String user = "root", password = "1619";  	// 오류 시 개인 root password 사용해 볼 것
	
	public QnAScreen() {
		JFrame frame = new JFrame("Q&A Screen");
		ImagePanel imagePanel = new ImagePanel(new ImageIcon("C:/Users/김민재2/eclipse-workspace/twitter2/Image/test.png").getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(imagePanel.getWidth(), imagePanel.getHeight());
		
		JPanel topPanel1 = new JPanel();
		JButton refresh = new JButton("Refresh");
		JPanel topPanel2 = new JPanel(new GridLayout(1, 3));
		JLabel label1 = new JLabel("QID");
		JLabel label2 = new JLabel("Writer");
		JLabel label3 = new JLabel("Date");
		JPanel questionPanel = new JPanel(new GridLayout(1, 10));
		JTextArea textArea = new JTextArea();
		JPanel bottomPanel = new JPanel();
		JButton write = new JButton("Start Q&A");
		JTextField textField = new JTextField();
		JLabel label4 = new JLabel("Enter QID to explore: ");
		JButton explore = new JButton("Explore");
		
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
			
			String s1 = "select * from question";
			rs = stmt.executeQuery(s1);
			
			int size = 0;
			
			while (rs.next()) {
				textArea.append(rs.getString("question_id") + " by " + rs.getString("user_id")
					+ " at " + rs.getString("question_date") + "\n");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO 새로고침 추가
				try {
					stmt = con.createStatement();
					
					String s1 = "select * from question";
					rs = stmt.executeQuery(s1);
					
					int size = 0;
					
					if (rs != null) {
						rs.last();
						size = rs.getRow();
						rs.first();
					}
					
					while (rs.next()) {
						textArea.append(rs.getString("question_id") + " by " + rs.getString("user_id")
							+ " at " + rs.getString("question_date") + "\n");
					}
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		write.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Q&A 쓰기 버튼
				// 다른 패널 넘어가기
				//QnAWrite();
			}
		});
		
		explore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO QID 입력 받은 후 탐색
				try {
					String questionID = textField.getText();
					String s2 = "select * from question where question_id = \"" + questionID + "\"";
					
					stmt = con.createStatement();
					
					rs = stmt.executeQuery(s2);
					
					textArea.setText(null);
					textArea.append("[" + rs.getString("question_id") + "]\n" + rs.getString("context") + "\nBy " + rs.getString("user_id") + "\nWritten at " + rs.getString("question_date") + "\n");
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		topPanel1.add(refresh);
		topPanel2.add(label1);
		topPanel2.add(label2);
		topPanel2.add(label3);
		questionPanel.add(textArea);
		bottomPanel.add(write);
		bottomPanel.add(label4);
		bottomPanel.add(textField);
		bottomPanel.add(explore);
		
		frame.getContentPane().add(BorderLayout.NORTH, topPanel1);
		frame.getContentPane().add(BorderLayout.NORTH, topPanel2);
		frame.getContentPane().add(BorderLayout.CENTER, questionPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		frame.setVisible(true);
	}
}
