package twitter2;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class twitter {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/twitter_team3";
			String user = "root", password = "1619";  	// 오류 시 개인 root password 사용해 볼 것
			
			con = DriverManager.getConnection(url, user, password);
			
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*try {
			stmt = con.createStatement();d
			
			String sql = "select * from user";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String id = rs.getString(1);
				
				if (rs.wasNull()) id = "null";
				
				String pswd = rs.getString(2);
				
				if (rs.wasNull()) pswd = "null";
				
				System.out.printf("%15s %15s%n", id, pswd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (stmt != null && !stmt.isClosed()) stmt.close();
			if (rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		stmt = null;
		rs = null;
		
		while (true) {
			try {
				Timestamp tmstp = new Timestamp(System.currentTimeMillis());
				stmt = con.createStatement();
				
				int op;
				String user_id = null, user_pw = null;
				//String create_date = default;
				String openness = null, user_name = null, user_email = null, phone_number = null, 
				gender = null, nickname = null, birthday = null;
				int interest1 = 0, interest2 =0, interest3 = 0;
		            
		        int bool[] = new int[8];
		        Arrays.fill(bool,0);

				Timestamp user_create_date = tmstp;
				Scanner s = new Scanner(System.in);
				
				while (true) {
					new Basic();
					System.out.println("Enter -1 to exit, 0 to sign up, 1 to sign in.");
					op = s.nextInt();
					
					if (op == 0) {                  // sign up
		                  System.out.println("Enter User ID");
		                  user_id = s.next();
		               
		                  if (user_id.equals("BREAK")) {   // BREAK 사용 변경 시 내용 수정할 것
		                     System.out.println("You can't use your ID as \"BREAK\"!");
		                     
		                     continue;
		                  }
		                  
		                  stmt = con.createStatement();
		               
		                  String s2 = "select user_id from user where user_id = \"" + user_id + "\"";
		               
		                  rs = stmt.executeQuery(s2);
		               
		                  if (rs.next()) {
		                     System.out.println("User ID already exists. Please try again!");
		                     
		                     continue;
		                  } else System.out.println("User ID Available!");
		               
		                  
		                  System.out.println("Enter password / Openness (Y / N)");
		                  user_pw = s.next();
		                  openness = s.next();
		               
		                  System.out.println("Enter User Name / User E-mail / Phone Number");
		                  user_name = s.next();
		                  user_email = s.next();
		                  phone_number = s.next();
		               
		                  System.out.println("(Optional) Enter Gender (M / F) / Nickname / Birthday (YYYY-MM-DD)");
		                  gender = s.next();
		                  nickname = s.next();
		                  birthday = s.next();
		                  
		                  System.out.println("Enter Your interest1,2,3 (1 : Exercise 2 : Games 3 : Music 4 : Food "
		                        + "5 : Movies 6 : Art 7 : Cartoon 8 : Pets");
		                  interest1 = s.nextInt();
		                  interest2 = s.nextInt();
		                  interest3 = s.nextInt();
		                  
		                  bool[interest1-1]=1;
		                  bool[interest2-1]=1;
		                  bool[interest3-1]=1;
		                  
		                  String s1 = "insert into user values ( \'" + user_id + "\', \'" + user_pw + "\', \'" + user_name
		                        + "\', \'" + user_email + "\', \'" + phone_number + "\', \'" + gender + "\', \'" + nickname
		                        + "\', \'" + birthday + "\', \'" + openness + "\', \'" + user_create_date + "\' )";
		                  
		                  psmt = con.prepareStatement(s1);
		                  psmt.executeUpdate();
		                  
		                  String interest = "insert into interest values(\'" + user_id + "\', \'" +bool[0]+"\', \'"+ bool[1]+
		                        "\',\'" + bool[2]+"\',\'"+bool[3]+"\',\'"+bool[4]+"\',\'" +bool[5]
		                              +"\',\'"+bool[6]+"\',\'"+bool[7]+"\')";
		                  psmt = con.prepareStatement(interest);
		                  psmt.executeUpdate();

					} else if (op == 1) {            	// sign in
						System.out.println("Enter User ID / Password:");
						user_id = s.next();
						user_pw = s.next();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from user where user_id = \"" + user_id + "\" and user_pw = \"" + user_pw + "\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							System.out.println("Logged in!");
							
							break;
						} else System.out.println("Wrong ID / Password. Please try again!");
					} else if (op == -1) {
						System.out.println("Exiting program.");
						
						con.close();
						stmt.close();
						rs.close();
						psmt.close();
						s.close();
						
						System.exit(0);
					} else System.out.println("Unknown input. Please try again!");
				}
				
				while (true) {
					// TODO 추가한 항목 별 입력 안내 메세지 추가
					System.out.println("Enter -1 to log out, 0 to write a post, 1 to write a comment, 2 to like a post, "
							+ "3 to dislike a post,\n 4 to like a comment, 5 to dislike a comment, 6 to see my followers, \n"
							+ "7 to see my followings, 8 to follow someone, \n9 to accept follow when your account is private, "
							+ "10 to unfollow someone.");
					op = s.nextInt();
					
					if (op == 0) {                  	// write a post
						String rub = s.nextLine();
						
						int post_count = 0;
						
						stmt = con.createStatement();
						
						String s1 = "select max(post_id) from post";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							String last_post_id = rs.getString(1);
							
							if (last_post_id != null) post_count = Integer.parseInt(last_post_id.substring(1)) + 1;
						}
						
						int number_of_likes = 0, number_of_dislikes = 0;
						String context = null, post_country = null, post_city = null, pin = null, visibility = null, origin_post_id = null;
						Timestamp posted_time = tmstp, modified_date = tmstp;
						
						System.out.println("Enter text, country, city, pin(Y/N), visibility(Y/N)");
						context = s.nextLine();
						post_country = s.next();
						post_city = s.next();
						pin = s.next();
						visibility = s.next();
						
						String post_id = "p" + post_count;
						origin_post_id = post_id;
						post_count++;
						
						String s2 = "insert into post values ( \'" + post_id + "\', \'" + user_id + "\', \'" + context + "\', \'"
								+ number_of_likes + "\', \'" + number_of_dislikes + "\', \'" + posted_time + "\', \'" + post_country
								+ "\', \'" + post_city + "\', \'" + origin_post_id + "\', \'" + modified_date + "\', \'" + pin
								+ "\', \'" + visibility + "\' )";
						
						//System.out.println(s2);
						
						psmt = con.prepareStatement(s2);
						psmt.executeUpdate();
					} else if (op == 1) {				// leave a comment
						String rub = s.nextLine();
						
						System.out.println("Which post I'm going to leave a comment : ");
						String post_id = s.nextLine();
						
						String s1 = "select post_id from post where post_id = \"" + post_id + "\"" ;
						stmt = con.createStatement();
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							int comment_count = 0;
							
							stmt = con.createStatement();
							
							String s2 = "select max(comment_id) from comment";
							rs = stmt.executeQuery(s2);
							
							if (rs.next()) {
								String last_comment_id = rs.getString(1);
								
								if (last_comment_id != null) comment_count = Integer.parseInt(last_comment_id.substring(1)) + 1;
							}
							
							int number_of_likes = 0, number_of_dislikes = 0;
							String context = null;
							Timestamp commented_time = tmstp, modified_date = tmstp;
							
							//String rub = s.nextLine();
							System.out.println("Enter comment : ");
							context = s.nextLine();
							
							//System.out.println(context);
							//String parent_comment_id = null;
							
							String comment_id = "c" + comment_count;
							comment_count++;
							
							String s3 = "insert into comment values ( \'" + comment_id + "\', \'" + context + "\', \'" 
									+ number_of_likes + "\', \'" + number_of_dislikes + "\', \'" + user_id + "\', \'"
									+ post_id + "\', \'" + commented_time + "\', \'" + modified_date + "\')";
							
							//System.out.println(s2);
						
							psmt = con.prepareStatement(s3);
							psmt.executeUpdate();
						} else continue;
					} else if (op == 2) {            	// like a post
						String rub = s.nextLine();
						
						System.out.println("Enter post ID to like the post");
						String post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from post_like where user_id = \"" + user_id + "\" and post_post_id = \"" + post_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) System.out.println("Already liked the post. Please try again!");
						else {
							Timestamp post_like_datetime = tmstp;
							
							//String s2 = "select number_of_likes from post_id where post_id = \"" + post_id + "\"";
							//rs = stmt.executeQuery(s2);
						
							//int number_of_likes = rs.getInt(1) + 1;
							
							String s3 = "insert into post_like values ( \'" + post_like_datetime + "\', \'" + post_id + "\', \'" + user_id + "\' )";
							String s4 = "update post set number_of_likes = number_of_likes + 1 where post_id = \'" + post_id + "\'";
							
							psmt = con.prepareStatement(s3);
							psmt.executeUpdate();
							
							int count = stmt.executeUpdate(s4);
						}
					} else if (op == 3) {            	// dislike a post
						String rub = s.nextLine();
						
						System.out.println("Enter post ID to dislike the post");
						String post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from post_dislike where user_id = \"" + user_id + "\" and post_post_id = \"" + post_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) System.out.println("Already disliked the post. Please try again!");
						else {
							Timestamp post_dislike_datetime = tmstp;
							
							//String s2 = "select number_of_dislikes from post_id where post_id = \"" + post_id + "\"";
							//rs = stmt.executeQuery(s2);
							
							String s3 = "insert into post_dislike values ( \'" + post_dislike_datetime + "\', \'" + post_id
									+ "\', \'" + user_id + "\' )";
							String s4 = "update post set number_of_dislikes = number_of_dislikes + 1 where post_id = \'" + post_id + "\'";
							
							psmt = con.prepareStatement(s3);
							psmt.executeUpdate();
							
							int count = stmt.executeUpdate(s4);
						}
					} else if (op == 4) {            	// like a comment
						String rub = s.nextLine();
						
						System.out.println("Enter comment ID to like the post");
						String liked_comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from comment_like where user_id = \"" + user_id + "\" and comment_comment_id = \"" + liked_comment_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							System.out.println("Already liked the comment. Try again.");
							
							continue;
						}
						
						Timestamp comment_like_datetime = tmstp;
						
						String s3 = "insert into comment_like values (\'" + comment_like_datetime
								+ "\', \'" + liked_comment_id + "\', \'" + user_id + "\')";
						String s4 = "update comment set num_of_likes = num_of_likes + 1 where comment_id = \'" + liked_comment_id + "\'";
						
						psmt = con.prepareStatement(s3);
						psmt.executeUpdate();
						
						int count = stmt.executeUpdate(s4);
					} else if (op == 5) {            	// dislike a comment
						String rub = s.nextLine();
						
						System.out.println("Enter Comment ID to dislike the Comment");
						String comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from comment_dislike where user_id = \"" + user_id + "\" and comment_comment_id = \"" + comment_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							System.out.println("Already disliked the comment. Please try again!");
							
							continue;
						}
						
						Timestamp comment_dislike_datetime = tmstp;
						
						//String s2 = "select number_of_dislikes from post_id where post_id = \"" + post_id + "\"";
						//rs = stmt.executeQuery(s2);
						
						String s3 = "insert into comment_dislike values ( \'" + comment_dislike_datetime + "\', \'" + comment_id
								+ "\', \'" + user_id + "\' )";
						String s4 = "update comment set num_of_dislikes = num_of_dislikes + 1 where comment_id = \'" + comment_id + "\'";
						
						psmt = con.prepareStatement(s3);
						psmt.executeUpdate();
						
						int count = stmt.executeUpdate(s4);
					} else if (op == 6) {            	// see my followers
						stmt = con.createStatement();
						
						String s6 = "SELECT follower_id FROM follow WHERE approve_YN = \"" + 'Y' + "\" AND following_id = \"" + user_id + "\"";
						rs = stmt.executeQuery(s6);
						
						while (rs.next()) {
							String result = rs.getString(1);
							
							System.out.println(result);
						}
					} else if (op == 7) {            	// see my followings
						stmt = con.createStatement();
						
						String s7 = "SELECT following_id FROM follow WHERE approve_YN = \"" + 'Y' + "\" AND follower_id = \"" + user_id + "\"";
						rs = stmt.executeQuery(s7);
						
						while (rs.next()) {
							String result = rs.getString(1);
							
							System.out.println(result);
						}
					} else if (op == 8) {               // follow someone
						String rub = s.nextLine();
						
						System.out.println("Input user ID to follow");
						
						String f_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String query = "select Openness from user where user_id = \"" + f_id + "\"";
						rs = stmt.executeQuery(query);
						
						String r = rs.getString("Openness");
						
						if (f_id.equals(user_id)) System.out.println("Can't follow yourself");
						else {
							stmt = con.createStatement();
							
							String s2 = "SELECT following_id FROM follow WHERE following_id = \"" + f_id + "\" AND follower_id = \"" + user_id + "\"";
							rs = stmt.executeQuery(s2);
							
							if (rs.next()) {
								System.out.println("Already followed the user. Please try again!");
								
								continue;
							}
							
							String s3;
							
							if (r.equals("N")) {		// 계정 공개범위 가져오기
								// 비공개 계정일 경우 팔로우 신청 보냄
								System.out.println("Follow request has been sent.");
									
								// 팔로우 정보 기록
								s3 = "insert into follow values ( \'" + tmstp + "\',\'" + 'N' + "\', \'" + user_id + "\', \'" + f_id + "\' )";
							} else {
								// 공개 계정일 경우 팔로우 처리
								System.out.println("You are now following.");
								
								// 팔로우 정보 기록
								s3 = "insert into follow values ( \'" + tmstp + "\',\'" + 'Y' + "\', \'" + user_id + "\', \'" + f_id + "\' )";
							}
							
							psmt = con.prepareStatement(s3);
							psmt.executeUpdate();
						}
					} else if (op == 9) {            	// 팔로우 받기(비공개 계정)
						stmt = con.createStatement();
						
						String s9 = "SELECT follower_id FROM follow WHERE approve_YN = \"" + 'N' + "\" AND following_id = \"" + user_id + "\"";
						rs = stmt.executeQuery(s9);
						
						while (rs.next()) {
							String result = rs.getString(1);
							
							System.out.println(result);
						}
						
						String f_id = null;
						
						while (true) {
							System.out.println("Select user to accept follow.(\"BREAK\" to break)");
							
							f_id = s.next();
							
							if (f_id.equals("BREAK")) break;
							else {
								String s2 = "UPDATE follow SET approve_YN = ? WHERE follower_id = ? ";
								
								psmt = con.prepareStatement(s2);
								psmt.setString(1, "Y");
								psmt.setString(2, f_id);
								
								psmt.executeUpdate();
							}
						}
					} else if (op == 10) {				// unfollow someone
						String rub = s.nextLine();
						
						// 언팔할 아이디 입력
						System.out.println("Input user ID to Unfollow");
						String f_id = s.nextLine();
						
						if (f_id.equals(user_id)) System.out.println("you can't unfollow yourself");
						else {
							stmt = con.createStatement();
							
							// 팔로우 테이블 튜플 삭제 sql쿼리
							String s8 = "DELETE FROM follow WHERE following_id = '" + f_id + "' AND follower_id = '" + user_id + "'";
							
							stmt.executeUpdate(s8);
						}
					} else if (op == 11) {				// 쓰레드 만들기
						String rub = s.nextLine();
						
						System.out.println("Enter post ID to start Thread");
						String post_id = s.nextLine();
						
						String s11 = "insert into Thread values ( \'" + post_id + "\' )";
						
						psmt = con.prepareStatement(s11);
						psmt.executeUpdate();
						
						System.out.println(post_id + " : is added");
					} else if (op == 12) {				// 쓰레드에 포스트 작성
						String rub = s.nextLine();
						
						System.out.println("Enter thread_id which you want to write on");
						String t_id = s.nextLine();
						
						System.out.println("you want to write a new post? (Y/N)");
						String console = s.nextLine();
						
						if (console.equals("Y")) {
							//포스트 새로 쓰기
							int post_count = 0;
							
							stmt = con.createStatement();
							
							String s1 = "select max(post_id) from post";
							rs = stmt.executeQuery(s1);
							
							if (rs.next()) {
								String last_post_id = rs.getString(1);
								
								if (last_post_id != null) post_count = Integer.parseInt(last_post_id.substring(1)) + 1;
							}
							
							int number_of_likes = 0, number_of_dislikes = 0;
							String context = null, post_country = null, post_city = null, pin = null, visibility = null, origin_post_id = null;
							Timestamp posted_time = tmstp, modified_date = tmstp;
							
							System.out.println("Enter text, country, city, pin(Y/N), visibility(Y/N)");
							context = s.nextLine();
							post_country = s.next();
							post_city = s.next();
							pin = s.next();
							visibility = s.next();
							
							String post_id = "p" + post_count;
							origin_post_id = post_id;
							post_count++;
							
							String s2 = "insert into post values ( \'" + post_id + "\', \'" + user_id + "\', \'" + context + "\', \'"
									+ number_of_likes + "\', \'" + number_of_dislikes + "\', \'" + posted_time + "\', \'" + post_country
									+ "\', \'" + post_city + "\', \'" + origin_post_id + "\', \'" + modified_date + "\', \'" + pin
									+ "\', \'" + visibility + "\' )";
							
							psmt = con.prepareStatement(s2);
							psmt.executeUpdate();
							
							//포스트 작성 완료했으면 쓰레드에 연결
							String s12 = "insert into thread_has_post values ( \'" + t_id + "\', \'" + post_id + "\' )";
							
							psmt = con.prepareStatement(s12);
							psmt.executeUpdate();
							
							System.out.println("post " + post_id + " is added in t_id " + t_id);
						} else if (console.equals("N")) {
							// 쓰레드에 추가할 포스트 입력
							System.out.println("Enter post_id to insert");
							String post_id = s.nextLine();
							
							String s12 = "insert into thread_has_post values ( \'" + t_id + "\', \'" + post_id + "\' )";
							
							psmt = con.prepareStatement(s12);
							psmt.executeUpdate();
							
							System.out.println("post " + post_id + " is added in t_id " + t_id);
						} else System.out.println("wrong input.");
					} else if (op == 13) {				// 북마크 만들기
						String rub = s.nextLine();
						
						System.out.println("set openness(Y/N)");
						String console = s.nextLine();
						
						int b_count = 0;
						String s13 = "select max(bookmark_id) from BookMark";
						rs = stmt.executeQuery(s13);
						
						if (rs.next()) {
							String last_b_id = rs.getString(1);
							
							if (last_b_id != null) b_count = Integer.parseInt(last_b_id.substring(1)) + 1;
						}
						
						String b_id = "b" + b_count;
						
						if (console.equals("Y")) s13 = "insert into BookMark values ( \'" + b_id + "\', \'" + tmstp + "\', \'" + console + "\', \'" + user_id + "\')";
						else if (console.equals("N")) s13 = "insert into BookMark values ( \'" + b_id + "\', \'" + tmstp + "\', \'" + console + "\', \'" + user_id + "\')";
						else {
							System.out.println("wrong input.");
							
							continue;
						}
						
						psmt = con.prepareStatement(s13);
						psmt.executeUpdate();
					} else if (op == 14) {				// 북마크 추가
						String rub = s.nextLine();
						
						System.out.println("Enter bookmark_id which you want to add post");
						String b_id = s.nextLine();
						
						System.out.println("Enter post_id which you want to add");
						String post_id = s.nextLine();
						
						String s14 = "insert into post_has_bookmark values ( \'" + post_id + "\', \'" + b_id + "\' )";
						
						psmt = con.prepareStatement(s14);
						psmt.executeUpdate();
					} else if (op == 15) {				// 댓글 삭제
						String rub = s.nextLine();
						
						//삭제할 댓글 id 입력받기
						System.out.println("Input comment ID to delete");
						String comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String checkComment = "select * from comment where user_id = \"" + user_id + "\" and comment_id = \"" + comment_id + "\"";
						rs = stmt.executeQuery(checkComment);
						
						if (rs.next()) { 	// 해당 comment_id가 유저가 작성한게 맞다면
							String deleteComment = "delete from comment where comment_id = \"" + comment_id + "\"";
							
							psmt = con.prepareStatement(deleteComment);
							psmt.execute();
							
							System.out.println("Delete comment!!");
						} else {
							System.out.println("Comment is not exist");
							
							continue;
						}
					} else if (op == 16) {				// 댓글 수정
						String rub = s.nextLine();
						
						// 수정할 댓글 id 입력받기
						System.out.println("Input comment ID to delete");
						
						String comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String checkComment = "select * from comment where user_id = \""+user_id+"\" and comment_id = \"" + comment_id+"\"";
						rs = stmt.executeQuery(checkComment);
						
						String modifiedComment = null;
						Timestamp modified_date = tmstp;
						
						if (rs.next()) {
							System.out.println("Input new context");
							modifiedComment = s.nextLine();
							
							String modifyComment = "update comment set context = \"" + modifiedComment
									+ "\", last_edit_datetime = \"" + modified_date + "\" where comment_id = \"" + comment_id + "\"";
							
							psmt = con.prepareStatement(modifyComment);
							psmt.executeUpdate();
							
							System.out.println("Coment is modified!!!");
						} else {
							System.out.println("Comment is not exist");
							
							continue;
						}
					} else if (op == 17) {				// 게시물 삭제
						String rub = s.nextLine();
						
						// 삭제할 게시물 아이디 입력
						System.out.println("Input post ID to delete");
						String post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String checkPost = "select * from post where post_id =\"" + post_id + "\" and user_id = \"" + user_id + "\""; //게시물이 존재하는 게시물인지 확인 및 본인 계시물일 경우에만 삭제가능
						rs = stmt.executeQuery(checkPost);
						
						if (rs.next()) {
							String deletePost = "delete from post where post_id = \"" + post_id + "\" and user_id = \"" + user_id + "\"";
							
							psmt = con.prepareStatement(deletePost);
							psmt.execute();
							
							System.out.println("Delete Post!!");
						} else {
							System.out.println("Post is not exist!!");
							
							continue;
						}
					} else if (op == 18) {				// 게시물 수정
						String rub = s.nextLine();
						
						//수정할 게시물 아이디 입력
						System.out.println("Input post ID to edit");
						String post_id = s.nextLine();
						
						String modifiedContext;
						Timestamp modified_date = tmstp;
						
						stmt = con.createStatement();
						
						String checkPost = "select * from post where post_id = \"" + post_id + "\" and user_id = \"" + user_id + "\""; //게시물이 존재하는 게시물인지 확인 및 본인 계시물일 경우에만 수정가능
						
						rs = stmt.executeQuery(checkPost);
						
						if (rs.next()) {	// 게시물이 존재한다면
							System.out.println("Input new context to edit");
							modifiedContext = s.nextLine();
							
							String modifyPost = "update post set context = \"" + modifiedContext + "\", last_edit_time = \"" + modified_date + "\" where post_id = \"" + post_id + "\"";
							
							psmt = con.prepareStatement(modifyPost);
							psmt.executeUpdate();
							
							System.out.println("수정되었습니다.");
						} else {
							System.out.println("This post does not exist!!!");
							
							continue;
						}
					} else if (op == 19) {				// 게시물 리트윗
						String rub = s.nextLine();
						
						// 리트윗할 게시물의 post_id받아오기
						System.out.println("Enter the post_id to retweet");
						String origin_post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select post_id from post where post_id = \"" + origin_post_id + "\"";
						rs = stmt.executeQuery(s1);
						
						// 리트윗 할 게시물이 존재하면 게시물 작성
						if (!rs.next()) System.out.println("No posts exist to retweet.");
						else {				// 리트윗 작성
							int post_count = 0;
							
							stmt = con.createStatement();
							
							String s2 = "select max(post_id) from post";
							rs = stmt.executeQuery(s2);
							
							if (rs.next()) {
								String last_post_id = rs.getString(1);
								
								if (last_post_id != null) post_count = Integer.parseInt(last_post_id.substring(1)) + 1;
							}
							
							int number_of_likes = 0, number_of_dislikes = 0;
							String context = null, post_country = null, post_city = null, pin = null, visibility = null;
							Timestamp posted_time = tmstp, modified_date = tmstp;
							
							System.out.println("Enter text, country, city, pin(Y/N), visibility(Y/N)");
							//rub = s.nextLine();
							context = s.nextLine();
							post_country = s.next();
							post_city = s.next();
							pin = s.next();
							visibility = s.next();
							
							String post_id = "p" + post_count;
							post_count++;
								
							// 원래 게시물의 context내용 받아오기
							String origin_context; 		// 원문 저장할 변수
							String selectContext = "select context from post where post_id = \"" + origin_post_id + "\"";
							rs = stmt.executeQuery(selectContext);
							
							if (rs.next()) {
								origin_context = rs.getString(1);
								context = context + "\n\n\n"+ origin_context;
							}
								
							String s3 = "insert into post values ( \'" + post_id + "\', \'" + user_id + "\', \'" + context
									+ "\', \'" + number_of_likes + "\', \'" + number_of_dislikes + "\', \'" + posted_time
									+ "\', \'" + post_country + "\', \'" + post_city + "\', \'" + origin_post_id
									+ "\', \'" + modified_date + "\', \'" + pin + "\', \'" + visibility + "\' )";
								
							psmt = con.prepareStatement(s3);
							psmt.executeUpdate();
						}
					} else if (op == 20) {				// 게시물 좋아요 취소
						String rub = s.nextLine();
						
						System.out.println("Enter the post ID to cancel the like");
						String post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from post_like where user_id = \"" + user_id + "\" and Post_post_id = \"" + post_id+"\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {	// 좋아요가 존재하면
							String s2 = "delete from post_like where user_id = \"" + user_id + "\"and Post_post_id =\"" + post_id+"\"";
							String s3 = "update post set number_of_likes = number_of_likes - 1 where post_id = \"" + post_id + "\"";
							
							psmt = con.prepareStatement(s2);
							psmt.executeUpdate();
						
							int count = stmt.executeUpdate(s3);
						} else {			// 좋아요가 존재하지 않는다면
							System.out.println("No corresponding likes exist. ");
						
							continue;
						}
					} else if (op == 21) { 				// 게시물 싫어요 취소
						String rub = s.nextLine();
					
						System.out.println("Enter the post ID to cancel the dislike");
						String post_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from post_dislike where user_id = \"" + user_id + "\" and Post_post_id = \"" + post_id+"\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) { 	// 싫어요가 존재하면
							String s2 = "delete from post_dislike where user_id = \"" + user_id + "\"and Post_post_id =\"" + post_id+"\"";
							String s3 = "update post set number_of_dislikes = number_of_dislikes - 1 where post_id = \"" + post_id + "\"";
							
							psmt = con.prepareStatement(s2);
							psmt.executeUpdate();
							
							int count = stmt.executeUpdate(s3);
						} else {			// 좋아요가 존재하지 않는다면
							System.out.println("No corresponding dislike exist. ");
							
							continue;
						}
					} else if (op == 22) { 				//댓글 좋아요 취소
						String rub = s.nextLine();
						
						System.out.println("Enter the comment ID to cancel the like");
						String comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from comment_like where user_id = \"" + user_id + "\" and Comment_comment_id = \"" + comment_id+"\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) { 	// 좋아요가 존재하면
							String s2 = "delete from comment_like where user_id = \"" + user_id + "\"and Comment_comment_id =\"" + comment_id+"\"";
							String s3 = "update comment set num_of_likes = num_of_likes - 1 where comment_id = \"" + comment_id+ "\"";
							
							psmt = con.prepareStatement(s2);
							psmt.executeUpdate();
						
							int count = stmt.executeUpdate(s3);
						} else {			// 좋아요가 존재하지 않는다면
							System.out.println("No corresponding likes exist. ");
						
							continue;
						}
					} else if (op == 23) {				// 댓글 싫어요 취소
						String rub = s.nextLine();
						
						System.out.println("Enter the comment ID to cancel the dislike");
						String comment_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select user_id from comment_dislike where user_id = \"" + user_id + "\" and Comment_comment_id = \"" + comment_id+"\"";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) { 	// 싫어요가 존재하면
							String s2 = "delete from comment_dislike where user_id = \"" + user_id + "\"and Comment_comment_id =\"" + comment_id+"\"";
							String s3 = "update comment set num_of_dislikes = num_of_dislikes - 1 where comment_id = \"" + comment_id+ "\"";
							
							psmt = con.prepareStatement(s2);
							psmt.executeUpdate();
							
							int count = stmt.executeUpdate(s3);
						} else {			// 싫어요가 존재하지 않는다면
							System.out.println("No corresponding dislikes exist.");
							
							continue;
						}
					} else if (op == 50) {				// show post IDs
						stmt = con.createStatement();
						
						String s1 = "select * from post";
						rs = stmt.executeQuery(s1);
						
						while (rs.next()) {
							if (rs.getString(12).equals("N")) continue;
							
							String writer_id = rs.getString(2);
							
							if (rs.wasNull()) writer_id = "null";
							
							String s2 = "select * from block where user_user_id = \"" + user_id + "\" and user_user_id1 = \"" + writer_id + "\"";
							ResultSet rs1 = stmt.executeQuery(s2);
							String s3 = "select * from block where user_user_id = \"" + writer_id + "\" and user_user_id1 = \"" + user_id + "\"";
							ResultSet rs2 = stmt.executeQuery(s3);
							boolean blocked = (rs1.next() || rs2.next());
							
							rs1.close();
							rs2.close();
							
							if (blocked) continue;
							
							String post_id = rs.getString(1);
							
							if (rs.wasNull()) post_id = "null";
							
							String post_datetime = rs.getString(10);
							
							if (rs.wasNull()) post_datetime = "null";
							
							System.out.printf(post_id + " by " + writer_id + " written at " + post_datetime);
						}
					} else if (op == 51) {				// show entered id of post
						System.out.println("Enter post ID to see.");
						String post_id = s.next();
						
						stmt = con.createStatement();
						
						String s1 = "select * from post where post_id = \"" + post_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (!rs.next()) {
							System.out.println("No post with entered post id exists! Please try again.");
							
							continue;
						}
						
						String writer_id = rs.getString(2);
						
						if (rs.wasNull()) writer_id = "null";
						
						String s2 = "select * from block where user_user_id = \"" + user_id + "\" and user_user_id1 = \"" + writer_id + "\"";
						ResultSet rs1 = stmt.executeQuery(s2);
						String s3 = "select * from block where user_user_id = \"" + writer_id + "\" and user_user_id1 = \"" + user_id + "\"";
						ResultSet rs2 = stmt.executeQuery(s3);
						boolean blocked = (rs1.next() || rs2.next());
						
						rs1.close();
						rs2.close();
						
						if (blocked) continue;
						
						if (rs.getString(12).equals("N")) {
							String s4 = "select * from follow where follower_id = \"" + user_id + "\" and following_id = \"" + writer_id + "\"";
							ResultSet rs3 = stmt.executeQuery(s4);
							String s5 = "select * from follow where follower_id = \"" + writer_id + "\" and following_id = \"" + user_id + "\"";
							ResultSet rs4 = stmt.executeQuery(s5);
							boolean isAllowed = (rs3.next() || rs4.next());
							
							rs3.close();
							rs4.close();
							
							if (!isAllowed) {
								System.out.println("The writer set this post only shown to followers and followings! Please try again.");
								
								continue;
							}
						}
						String context = rs.getString(3);
						
						if (rs.wasNull()) context = "null";
						
						String posted_time = rs.getString(10);
						
						if (rs.wasNull()) posted_time = "null";
						
						String posted_country = rs.getString(7);
						
						if (rs.wasNull()) posted_country = "null";
						
						String posted_city = rs.getString(8);
						
						if (rs.wasNull()) posted_city = "null";
						
						String num_of_likes = rs.getString(4);
						
						if (rs.wasNull()) num_of_likes = "0";
						
						String num_of_dislikes = rs.getString(5);
						
						if (rs.wasNull()) num_of_dislikes = "0";
						
						System.out.println(context + "\nBy " + writer_id + "Written at " + posted_time + "\nIn " + posted_city + ", " + posted_country + "\n" + num_of_likes + " likes, " + num_of_dislikes + " dislikes");
					} else if (op == 52) {				// change post openness
						System.out.println("Enter post ID to change openness of the post");
						String changing_post_id = s.next();
						
						String s1 = "select * from post where post_id = \"" + changing_post_id + "\"";
						
						rs = stmt.executeQuery(s1);
						
						if (!rs.next()) {
							System.out.println("There is no such post with entered post ID! Please try again.");
						
							continue;
						}
						
						String writer_id = rs.getString(2);
						
						if (rs.wasNull()) writer_id = "null";
						
						if (!user_id.equals(writer_id)) {
							System.out.println("You don't have a permission to edit openness of this post! Only writer can edit.");
							
							continue;
						}
						
						String s2 = "update post set visibility = ? where follower_id = \"" + changing_post_id + "\"";
						
						psmt = con.prepareStatement(s2);
						
						String visibility = rs.getString(12);
						
						if (visibility.equals("Y")) psmt.setString(1, "N");
						else if (visibility.equals("N")) psmt.setString(2, "Y");
						
						psmt.executeUpdate();
						
						System.out.println("Post visibility has successfully changed.");
					} else if (op == 81) {				// block someone
						System.out.println("Enter user ID to block:");
						String blocking_id = s.next();
						
						stmt = con.createStatement();
						
						String s1 = "select * from user where user_id = \"" + blocking_id + "\"";
						
						rs = stmt.executeQuery(s1);
						
						if (!rs.next()) {
							System.out.println("There is no user with entered user ID!");
							
							continue;
						}
						
						String s2 = "select * from block where user_user_id = \"" + user_id + "\" and user_user_id1 = \"" + blocking_id + "\"";
						rs = stmt.executeQuery(s2);
						
						if (rs.next()) {
							System.out.println("You already blocked the user! Enter 1 to unblock user, or any integer to return to menu.");
							int choice = s.nextInt();
							
							if (choice == 1) {
								String s3 = "delete from block where user_user_id = \"" + user_id + "\" and user_user_id1 = \"" + blocking_id + "\"";
								
								psmt = con.prepareStatement(s3);
								psmt.executeUpdate();
							} else continue;
						} else {
							Timestamp block_datetime = tmstp;
							
							String s4 = "insert into block values ( \'" + user_id + "\', \'" + blocking_id + "\', \'" + block_datetime + "\' )";
							
							psmt = con.prepareStatement(s4);
							psmt.executeUpdate();
						}
					} else if (op == 96) {				// show all Q&A question IDs
						stmt = con.createStatement();
						
						String s1 = "select * from question";
						rs = stmt.executeQuery(s1);
						
						while (rs.next()) {
							String question_id = rs.getString(1);
							
							if (rs.wasNull()) question_id = "null";
							
							String writer_id = rs.getString(2);
							
							if (rs.wasNull()) writer_id = "null";
							
							String question_datetime = rs.getString(4);
							
							if (rs.wasNull()) question_datetime = "null";
							
							System.out.printf(question_id + " by " + writer_id + " written at " + question_datetime);
						}
					} else if (op == 97) {				// show context of entered question ID
						System.out.println("Enter question ID to see.");
						String question_id = s.next();
						
						stmt = con.createStatement();
						
						String s1 = "select * from question where question_id = \"" + question_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (!rs.next()) {
							System.out.println("No question post with entered question id exists! Please try again.");
							
							continue;
						}
						
						String writer_id = rs.getString(1);
						
						if (rs.wasNull()) writer_id = "null";
						
						String context = rs.getString(2);
						
						if (rs.wasNull()) context = "null";
						
						String question_datetime = rs.getString(3);
						
						if (rs.wasNull()) question_datetime = "null";
						
						System.out.println(context + "\nBy " + writer_id + "Written at " + question_datetime);
					} else if (op == 98) {				// make a Q&A post
						String rub = s.nextLine();
						
						int question_count = 0;
						
						stmt = con.createStatement();
						
						String s1 = "select max(question_id) from question";
						rs = stmt.executeQuery(s1);
						
						if (rs.next()) {
							String last_question_id = rs.getString(1);
							
							if (last_question_id != null) question_count = Integer.parseInt(last_question_id.substring(1)) + 1;
						}
						
						String context = null;
						Timestamp question_datetime = tmstp;
						
						System.out.println("Enter text you want to ask to Dev:");
						context = s.nextLine();
						
						String question_id = "q" + question_count;
						question_count++;
						
						String s2 = "insert into question values ( \'" + question_id + "\', \'" + user_id + "\', \'" + context + "\', \'"
								+ question_datetime + "\' )";
						
						psmt = con.prepareStatement(s2);
						psmt.executeUpdate();
					} else if (op == 99) {				// make a comment of Q&A post
						String rub = s.nextLine();
						
						System.out.println("Enter a post ID to leave a Q&A answer: ");
						String question_id = s.nextLine();
						
						stmt = con.createStatement();
						
						String s1 = "select question_id from question where question_id = \"" + question_id + "\"";
						rs = stmt.executeQuery(s1);
						
						if (!rs.next()) {
							System.out.println("There is no question with entered question ID!\n");
							
							continue;
						}
						
						int answer_count = 0;
						
						stmt = con.createStatement();
						
						String s2 = "select max(answer_id) from answer";
						rs = stmt.executeQuery(s2);
						
						/*if (rs.next()) {
							System.out.println("There is already an answer! Please try again.");
						
							continue;
						}*/
						String last_answer_id = rs.getString(1);
						
						if (last_answer_id != null) answer_count = Integer.parseInt(last_answer_id.substring(1)) + 1;
						
						String context = null;
						Timestamp answered_time = tmstp, modified_date = tmstp;
						
						System.out.println("Enter answer:");
						//String rub = s.nextLine();
						context = s.nextLine();
						
						//System.out.println(context);
						
						String answer_id = "a" + answer_count;
						answer_count++;
						
						String s3 = "insert into comment values ( \'" + answer_id + "\', \'" + context + "\', \'"
								+ question_id + "\', \'" + answered_time + "\', \'" + modified_date + "\')";
						
						//System.out.println(s2);
						
						psmt = con.prepareStatement(s3);
						psmt.executeUpdate();
					} else if (op == -1) {				// log out
						System.out.println("Logging out.");
						
						/*con.close();
						stmt.close();
						rs.close();
						psmt.close();
						s.close();*/
						
						break;
					} else System.out.println("Unknown input. Please try again!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
