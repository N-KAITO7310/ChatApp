package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WasReadDAO {
	Connection con = null;

	public WasReadDAO() {
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
	 * 追加したばかりのチャットと、それが投稿されたグループユーザーとの既読関係を追加する
	 * @param latestChatId
	 * @param groupUserIds
	 * @return
	 */
	public boolean addWasReadRelation(int latestChatId, List<Integer> groupUserIds) {
		for(int user_id : groupUserIds) {
			String insertSql = "INSERT INTO already_reads(user_id, chat_id, was_read) values(?, ?, 0)";

			PreparedStatement insert = null;

			try {
				insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

				insert.setInt(1, user_id);
				insert.setInt(2, latestChatId);
				int resultNum = insert.executeUpdate();
				if(resultNum == 0) {
					System.out.println("既読関係の追加が正しく行われませんでした。");
					return false;
				}
				if(resultNum == 1) {
					System.out.println("既読関係の追加が完了しました。");
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * 既読処理
	 * @param chat_id
	 * @param user_id
	 * @return
	 */
	public boolean read(int chat_id, int user_id) {
		String insertSql = "UPDATE already_reads SET was_read = 1 WHERE chat_id = ? AND user_id = ?";

		PreparedStatement insert = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setInt(1, chat_id);
			insert.setInt(2, user_id);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("既読関係の追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("既読関係の追加が完了しました。");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

	return true;
	}
	/**
	 * 特定のユーザーと特定のチャットが既読済みかどうかを判定するメソッド
	 * @param userId
	 * @param chatId
	 * @return
	 */
	public boolean checkAlreadyRead(int userId,int chatId) {
		String findSql = "SELECT * FROM already_reads WHERE user_id = ? AND chat_id = ?";

		PreparedStatement findStm = null;

		try {
			findStm = con.prepareStatement(findSql);// ステートメントオブジェクトを生成

			findStm.setInt(1, userId);
			findStm.setInt(2, chatId);
			ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得
			System.out.println(findStm);


			while(resultAll.next()) {
				int read_status = resultAll.getInt("was_read");
				System.out.println(read_status);

				if(read_status == 1) {
					return true;
				}else {
					return false;
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}

			System.out.println("checkAlreadyReadで異常な動作。");
			return false;
		}

	/**
	 * 特定のチャットidをもち、既読フラグが１のものを全て取得する（グループチャットルームで既読数を表示するために使用）
	 * @param chatId
	 * @return
	 */
	public int checkAlreadyReadForGroup(int chatId){
		String findSql = "SELECT * FROM already_reads WHERE chat_id = ? AND was_read = 1";

		PreparedStatement findStm = null;

		try {
			findStm = con.prepareStatement(findSql);// ステートメントオブジェクトを生成

			findStm.setInt(1, chatId);
			ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得
			System.out.println(findStm);

			int readCount = 0;

			while(resultAll.next()) {
				readCount++;
			}

			return readCount;
			}catch(Exception e) {
				e.printStackTrace();
			}

			System.out.println("checkAlreadyReadForGroupで異常な動作。");
			return 0;
		}

}
