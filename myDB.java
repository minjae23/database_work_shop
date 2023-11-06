package twitter;
import java.sql.*;
import java.util.Scanner;
public class myDB {
	
	public static void main(String[] args) {
		int pcnt =0;
		Connection con =null;
		Statement stmt = null;
		ResultSet rs= null;
		PreparedStatement pstm = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/myDB";
			String user = "root", passwd = "1619";
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			System.out.println(con);
			
			while(true)
			{
				stmt=con.createStatement();
				
				int op1;
				String id =null;
				String pwd=null;
				Scanner s = new Scanner(System.in);
				System.out.println("Input one to log in, zero to sign up");
				op1= s.nextInt();
				
				if(op1==0)
				{
					String s1=null;
					
					System.out.println("input userid/password");
					id=s.next();
					pwd=s.next();
					
					stmt= con.createStatement();
					String s2= "select user_id from user where user_id = \""+id+"\"";
					rs=stmt.executeQuery(s2);
					
					if(rs.next())
					{
						System.out.println("User name already exists.Please try again!");
					}
					else {
						s1="insert into user values( \'" + id +"\',\'"+pwd+"\')";
						
						pstm = con.prepareStatement(s1);
						pstm.executeUpdate();
					}
				}
				else if(op1==1)
				{
					System.out.println("Input userid / password");
					id=s.next();
					pwd=s.next();
					
					stmt= con.createStatement();
					String s1= "select user_id from user where user_id = \""+id+"\"and pwd=\""+pwd+"\"";
					rs=stmt.executeQuery(s1);
					
					if(rs.next())
					{
						System.out.println("Log in!");
						int op2;
						String pid = null;
						
						int plke =0;
						System.out.println("0 to write post, 1 to write comment,3 to like post,4 to like comment,5 to see my followers, 6 to see my following");
						op2= s.nextInt();
						if(op2==0)
						{
							String rub = s.nextLine();
							String text = null;
							text=s.nextLine();
							pid = "p" + pcnt;
							pcnt ++;
							
							s1 = "insert into posts values(\'"+pid+"\',\'"+text+"\',\'"+plke+"\',\'"+id+"\',\'"+"2020"+"\',\'"+"2021"+"\')";
							System.out.println(s1);
							
							pstm = con.prepareStatement(s1);
							pstm.executeUpdate();
						}
						else if(op2==3)
						{
							String rub = s.nextLine();
							
							String postid = null;
							
							postid=s.nextLine();
							String s3=null;
							String s4=null;
							int plid = 0;
							stmt = con.createStatement();
							String s2 = "select user_user_id from post_like where user_user_id = \""+id+ "\" and post_post_id=\""+postid+"\"";
							rs = stmt.executeQuery(s2);
							
							if(rs.next())
							{
								System.out.println("Already	liked post. Please try again!");
								
							}
							else {
								++plid;
								
								s3 = "insert into post_like values(\'" + plid+"\',\'"+postid+"\',\'"+id+"\')";
								s4 = "update posts set num_of_likes = num_of_likes + 1 where post_post_id = \'"+postid+"\'";
								
								pstm=con.prepareStatement(s3);
								pstm.executeUpdate();
								
								int count = stmt.executeUpdate(s4);
							}
						}
					}
					else {
						System.out.println("wrong id retry");
					}
				}
				
			}
		}catch(Exception e)
		{
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
		}
	}
	
}


		// database operations ...
		
		
	
