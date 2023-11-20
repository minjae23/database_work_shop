package twitter2;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Basic {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Basic window = new Basic();
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
	public Basic() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
		
		ImagePanel imagepanel = new ImagePanel(new ImageIcon("C:/Users/김민재2/eclipse-workspace/twitter2/Image/test.png").getImage());
		frame.setSize(imagepanel.getWidth(), imagepanel.getHeight());
		frame.getContentPane().add(imagepanel);
		imagepanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign up");
		lblNewLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		lblNewLabel.setBounds(45, 268, 190, 85);
		imagepanel.add(lblNewLabel);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		lblLogin.setBounds(55, 372, 190, 85);
		imagepanel.add(lblLogin);
		
		JButton sign_up_btn = new JButton("New button");
		sign_up_btn.setBounds(167, 303, 93, 23);
		imagepanel.add(sign_up_btn);
		
		JButton Log_btn= new JButton("New button");
		Log_btn.setBounds(167, 407, 93, 23);
		imagepanel.add(Log_btn);
		
		
		sign_up_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// sign up gui
            	new Sign_up();
            	frame.dispose();
            }
		});
		Log_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Login_window();
            	// 현재 창 닫기
            	frame.dispose();
            }
		});
		
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
