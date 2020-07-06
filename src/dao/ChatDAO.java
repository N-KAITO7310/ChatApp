package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Chat;

public class ChatDAO {

	Connection con = null;

	public ChatDAO() {
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
	 * 引数のroom_idに紐づく全てのチャットデータを取得
	 * @param room_id
	 * @return
	 */
	public List<Chat> fetchAllChats(int room_id){
		String findAllChatsSql = "SELECT * FROM chats JOIN likes ON chats.id = likes.chat_id WHERE chats.room_id = ?";

		PreparedStatement findStm = null;

		try {
			List<Chat> chats = new ArrayList<>();
			findStm = con.prepareStatement(findAllChatsSql);// ステートメントオブジェクトを生成

			findStm.setInt(1, room_id);
			ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得


			while(resultAll.next()) {
				int id = resultAll.getInt("id");
				int user_id = resultAll.getInt("user_id");
				int roomId = resultAll.getInt("room_id");
				String comment = resultAll.getString("comment");
				Date date = resultAll.getDate("post_time");
				Time time =  resultAll.getTime("post_time");
				int like_count = resultAll.getInt("like_count");

				Chat chat = new Chat(id, user_id, roomId, comment, date, time, like_count);

				chats.add(chat);
			}

			System.out.println("ChatDAO：該当するチャットルームのチャットを全てリターンします");
			return chats;

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("fetchAllChatsで異常な動作が行われています。該当箇所を確認してください。");

		return null;
	}

	/**
	 * 引数を元にチャットデータを追加
	 * @param user_id
	 * @param room_id
	 * @param comment
	 * @return
	 */
	public boolean addChat(int user_id, int room_id, String comment) {
		String insertSql = "INSERT INTO chats(user_id, room_id, comment) values(?, ?, ?)";

		PreparedStatement insert = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setInt(1, user_id);
			insert.setInt(2, room_id);
			insert.setString(3, comment);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("chatの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("chatの追加が完了しました。");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 最新のチャットデータを取得。中間テーブル作成時に他のユーザーと紐づけるために使用。
	 * @return
	 */
	public Chat fetchLatestChat() {
		//最新のレコードをidで降順にして上から一つに限定することで取得
		String findLatestRooIdSql = " select * from chats order by id desc limit 1";

		PreparedStatement findStm = null;

		try {
			findStm = con.prepareStatement(findLatestRooIdSql);// ステートメントオブジェクトを生成

			ResultSet resultOne = findStm.executeQuery();// クエリーを実行して結果セットを取得


			resultOne.next();

			int chatId = resultOne.getInt("id");
			int user_id = resultOne.getInt("user_id");
			int room_id = resultOne.getInt("room_id");
			String comment = resultOne.getString("comment");
			Date date = resultOne.getDate("post_time");
			Time time =  resultOne.getTime("post_time");

			Chat chat = new Chat(chatId, user_id, room_id, comment, date, time);


			System.out.println("ChatroomDAO：最新のチャットデータをリターンします");
			return chat;

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("fetchLatestChat()で異常な動作が行われています。該当箇所を確認してください。");

		return null;
	}
}
