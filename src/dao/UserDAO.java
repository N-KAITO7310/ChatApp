package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {
	Connection con = null;

	public UserDAO() {
		//生成時にドライバ接続、コネクション取得
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/Chatapp?user=root&password=&serverTimezone=UTC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * データベースへの接続を切断するメソッド
	 */
	public void closeConnect() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *　ユーザーの登録
	 * @return
	 */
	public boolean insertUser(String user_name,String email,String user_password,String nickname,String date_of_birth,String sex,String introduction,String profile_image_url) {
		String insertSql = "INSERT INTO users(user_name, email, user_password, nickname, date_of_birth, sex, introduction, profile_image_url) values(?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement insert = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setString(1, user_name);
			insert.setString(2, email);
			insert.setString(3, user_password);
			insert.setString(4, nickname);
			insert.setString(5, date_of_birth);
			insert.setString(6, sex);
			insert.setString(7, introduction);
			insert.setString(8, profile_image_url);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("ユーザーデータの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("ユーザーデータの追加が完了しました。");
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("insertUser()で異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}

	/**
	 * ログイン処理
	 * @param email
	 * @param user_password
	 * @return
	 */
	public User login(String email, String user_password) {
		String sql_select_for_login = "SELECT id, user_name, email, user_password, nickname, date_of_birth, sex, introduction, profile_image_url FROM users WHERE email = ? AND user_password = ?";

		PreparedStatement login = null;

        try {
		login = con.prepareStatement(sql_select_for_login);// ステートメントオブジェクトを生成
		login.setString(1, email);
		login.setString(2, user_password);
		System.out.println("ログイン確認用出力" + login);
		ResultSet loginResult = login.executeQuery();// クエリーを実行して結果セットを取得


		loginResult.next();


		int id = loginResult.getInt("ID");
		String user_name = loginResult.getString("user_name");
		String mail = loginResult.getString("email");
		String pass = loginResult.getString("user_password");
		String nickname = loginResult.getString("nickname");
		String date_of_birth = loginResult.getString("date_of_birth");
		String sex = loginResult.getString("sex");
		String introduction = loginResult.getString("introduction");
		String profile_image_url = loginResult.getString("profile_image_url");

		User user = new User(id, user_name, mail, pass, nickname, date_of_birth, sex, introduction, profile_image_url);

		System.out.println("ログイン情報:" + user_name + "：" + mail + "：" + pass);
		return user;


        } catch (SQLException e) {
            e.printStackTrace();
        //} catch (ClassNotFoundException e) {
          //  e.printStackTrace();
        }
		return null;
	}

	/**
	 * roomId、selfIdで指定のチャットルームに紐づいている自分以外の全てのユーザーのデータを取得する
	 * @param roomId
	 * @param selfId
	 * @return
	 */
	public List<User> findFriendsFromChatroomid(int roomId,int selfId){
		String findFriendsSql = "select users.id, users.user_name, users.email, users.user_password, users.nickname, users.date_of_birth, users.sex, users.introduction, users.profile_image_url from users join users_chatrooms on users.id = users_chatrooms.user_id join chatrooms on users_chatrooms.chatroom_id  = chatrooms.id  where chatrooms.id = ? and users.id <> ?";
		PreparedStatement findFriendsStm = null;

		try {
			List<User> friends = new ArrayList<>();
			findFriendsStm = con.prepareStatement(findFriendsSql);// ステートメントオブジェクトを生成

			findFriendsStm.setInt(1, roomId);
			findFriendsStm.setInt(2, selfId);
			ResultSet resultAll = findFriendsStm.executeQuery();// クエリーを実行して結果セットを取得


			while(resultAll.next()) {
				int id = resultAll.getInt("ID");
				String user_name = resultAll.getString("user_name");
				String mail = resultAll.getString("email");
				String pass = resultAll.getString("user_password");
				String nickname = resultAll.getString("nickname");
				String date_of_birth = resultAll.getString("date_of_birth");
				String sex = resultAll.getString("sex");
	 			String introduction = resultAll.getString("introduction");
				String profile_image_url = resultAll.getString("profile_image_url");

				User user = new User(id, user_name, mail, pass, nickname, date_of_birth, sex, introduction, profile_image_url);


				friends.add(user);
			}

			System.out.println("UserDAO：ログインユーザの全てのfriendsをリターンします");
			return friends;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return null;

	}

	/**
	 * 登録ユーザー全てを返す
	 * @return
	 */
	public List<User> allUsers(){
		String allUsersSql = "SELECT * FROM users";
		PreparedStatement allUserStm = null;

		try {
			List<User> users = new ArrayList<>();
			allUserStm = con.prepareStatement(allUsersSql);// ステートメントオブジェクトを生成
			ResultSet resultAll = allUserStm.executeQuery();// クエリーを実行して結果セットを取得


			while(resultAll.next()) {
				int id = resultAll.getInt("ID");
				String user_name = resultAll.getString("user_name");
				String mail = resultAll.getString("email");
				String pass = resultAll.getString("user_password");
				String nickname = resultAll.getString("nickname");
				String date_of_birth = resultAll.getString("date_of_birth");
				String sex = resultAll.getString("sex");
	 			String introduction = resultAll.getString("introduction");
				String profile_image_url = resultAll.getString("profile_image_url");

				User user = new User(id, user_name, mail, pass, nickname, date_of_birth, sex, introduction, profile_image_url);


				users.add(user);
			}

			System.out.println("UserDAO：全ての登録ユーザをリターンします");
			return users;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return null;

	}

	/**
	 * 名前キーワードからユーザーを検索して取得
	 * @param word
	 * @return
	 */
	public List<User> searchUser(String word){
		//検索ワードを%をつけて挿入する（しない場合setStringがうまく動作しなかったため）
		StringBuilder builder = new StringBuilder();
		builder.append("%");
		builder.append(word);
		builder.append("%");
		String intoSql = builder.toString();
		String searchUserSql = "SELECT * FROM users WHERE user_name LIKE ?";
		PreparedStatement searchUserStm = null;


		try {
			List<User> users = new ArrayList<>();
			searchUserStm = con.prepareStatement(searchUserSql);// ステートメントオブジェクトを生成
			searchUserStm.setString(1, intoSql);

			ResultSet resultAll = searchUserStm.executeQuery();// クエリーを実行して結果セットを取得

			while(resultAll.next()) {
				int id = resultAll.getInt("ID");
				String user_name = resultAll.getString("user_name");
				String mail = resultAll.getString("email");
				String pass = resultAll.getString("user_password");
				String nickname = resultAll.getString("nickname");
				String date_of_birth = resultAll.getString("date_of_birth");
				String sex = resultAll.getString("sex");
	 			String introduction = resultAll.getString("introduction");
				String profile_image_url = resultAll.getString("profile_image_url");

				User user = new User(id, user_name, mail, pass, nickname, date_of_birth, sex, introduction, profile_image_url);

				users.add(user);
			}

			System.out.println("UserDAO：検索結果usersをリターンします");
			return users;

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("searchUserで異常な例外発生。");
		}
		return null;
	}
	//名前が上のサーチと紛らわしいので、あとで変更の可能性あり
	/**
	 * ユーザーidから検索
	 * @param user_id
	 * @return
	 */
	public User findUser(int user_id) {
		String searchUserSql = "SELECT * FROM users WHERE id = ?";
		PreparedStatement searchUserStm = null;

		try {
			User user = new User();
			searchUserStm = con.prepareStatement(searchUserSql);// ステートメントオブジェクトを生成

			searchUserStm.setInt(1, user_id);
			ResultSet resultAll = searchUserStm.executeQuery();// クエリーを実行して結果セットを取得

			while(resultAll.next()) {
				int id = resultAll.getInt("ID");
				String user_name = resultAll.getString("user_name");
				String mail = resultAll.getString("email");
				String pass = resultAll.getString("user_password");
				String nickname = resultAll.getString("nickname");
				String date_of_birth = resultAll.getString("date_of_birth");
				String sex = resultAll.getString("sex");
	 			String introduction = resultAll.getString("introduction");
				String profile_image_url = resultAll.getString("profile_image_url");

				user = new User(id, user_name, mail, pass, nickname, date_of_birth, sex, introduction, profile_image_url);
			}

			System.out.println("UserDAO：対象のuserをリターンします");
			return user;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return null;
	}

	/**
	 * 自分のid、相手のidから中間テーブルuser_userを利用して、友達関係にあるかどうかをチェックするメソッド
	 * @param selfId
	 * @param selectedUserId
	 * @return
	 */
	public boolean checkIsFriend(int selfId, int selectedUserId) {
		String checkIsFrinedSql = "SELECT * FROM user_user where (user_id1 = ? AND user_id2 = ?) OR (user_id2 = ? AND user_id1 = ?)";
		PreparedStatement isFriendStm = null;

		try {
			boolean isFriend = false;

			isFriendStm = con.prepareStatement(checkIsFrinedSql);// ステートメントオブジェクトを生成

			isFriendStm.setInt(1, selfId);
			isFriendStm.setInt(2, selectedUserId);
			isFriendStm.setInt(3, selfId);
			isFriendStm.setInt(4, selectedUserId);
			ResultSet result = isFriendStm.executeQuery();// クエリーを実行して結果セットを取得

			while(result.next()) {
				isFriend = true;
			}

			return isFriend;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");

		return false;
	}

	/**
	 * 自分のid、相手のidから、user_userテーブルを利用して相手との一対一のチャットルームのidを取得する
	 * @param selfId
	 * @param friendId
	 * @return
	 */
	public int findRoomIdfromFriendId(int selfId, int friendId) {
		String findSql = "SELECT * FROM user_user where (user_id1 = ? AND user_id2 = ?) OR (user_id2 = ? AND user_id1 = ?);";
		PreparedStatement Stm = null;

		try {
			Stm = con.prepareStatement(findSql);// ステートメントオブジェクトを生成

			Stm.setInt(1, selfId);
			Stm.setInt(2, friendId);
			Stm.setInt(3, selfId);
			Stm.setInt(4, friendId);
			ResultSet result = Stm.executeQuery();// クエリーを実行して結果セットを取得

			int roomId = 0;

			while(result.next()) {
				roomId = result.getInt("chatroom_id");
			}


			return roomId;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");

		return 0;
	}

	/**
	 * マイアカウントの情報を自己紹介、プロフ画像urlだけ更新する
	 * @param selfId
	 * @param introduction
	 * @param img_url
	 * @return
	 */
	public boolean putUserInfo(int selfId,String introduction, String img_url) {
		String checkIsFrinedSql = "UPDATE users SET introduction = ?, profile_image_url = ? where id = ?;";
		PreparedStatement updateStm = null;

		try {
			boolean result = false;

			updateStm = con.prepareStatement(checkIsFrinedSql);// ステートメントオブジェクトを生成

			updateStm.setString(1, introduction);
			updateStm.setString(2, img_url);
			updateStm.setInt(3, selfId);

			int resultNum = updateStm.executeUpdate();// クエリーを実行して結果セットを取得

			if(resultNum == 1) {
				result = true;
			}else {
				result = false;
			}

			System.out.println("UserDAO：アップデート処理を行いました。結果：" + resultNum);
			return result;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}
	/**
	 * マイアカウント情報の自己紹介文のみ変更する
	 * @param selfId
	 * @param introduction
	 * @return
	 */
	public boolean putUserIntroduction(int selfId,String introduction) {
		String putIntroductionSql = "UPDATE users SET introduction = ? where id = ?;";
		PreparedStatement updateStm = null;

		try {
			boolean result = false;

			updateStm = con.prepareStatement(putIntroductionSql);// ステートメントオブジェクトを生成

			updateStm.setString(1, introduction);
			updateStm.setInt(2, selfId);

			int resultNum = updateStm.executeUpdate();// クエリーを実行して結果セットを取得

			if(resultNum == 1) {
				result = true;
			}else {
				result = false;
			}

			System.out.println("UserDAO：アップデート処理を行いました。結果：" + resultNum);
			return result;

		}catch(Exception e) {
			e.printStackTrace();
		}


		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}
	/**
	 * マイアカウント情報のプロフ画像urlのみ変更する
	 * @param selfId
	 * @param img_url
	 * @return
	 */
	public boolean putUserImgUrl(int selfId, String img_url) {
		String updateImgUrlSql = "UPDATE users SET profile_image_url = ? where id = ?;";
		PreparedStatement updateStm = null;

		try {
			boolean result = false;

			updateStm = con.prepareStatement(updateImgUrlSql);// ステートメントオブジェクトを生成

			updateStm.setString(1, img_url);
			updateStm.setInt(2, selfId);

			int resultNum = updateStm.executeUpdate();// クエリーを実行して結果セットを取得

			if(resultNum == 1) {
				result = true;
			}else {
				result = false;
			}

			System.out.println("UserDAO：アップデート処理を行いました。結果：" + resultNum);
			return result;

		}catch(Exception e) {
			e.printStackTrace();
		}


		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}


	/**
	 * 自分のid、チャットルームidから自分がそのチャットルームに所属しているかを判別するメソッド
	 * @param selfId
	 * @param room_id
	 * @return
	 */
	public boolean checkSelfInThisRoom(int selfId, int room_id) {
		String checkSql = "SELECT * FROM users_chatrooms WHERE user_id = ? AND chatroom_id = ?";
		PreparedStatement checkStm = null;

		try {
			boolean result = false;

			checkStm = con.prepareStatement(checkSql);// ステートメントオブジェクトを生成

			checkStm.setInt(1, selfId);
			checkStm.setInt(2, room_id);

			ResultSet resultAll = checkStm.executeQuery();// クエリーを実行して結果セットを取得


			while(resultAll.next()) {
				result = true;
			}
			return result;

		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("UserDAOで異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}
}
