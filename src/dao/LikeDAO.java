package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LikeDAO {
	Connection con = null;

	public LikeDAO() {
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
	 * チャットidからそれに紐づくいいねレコードを保存する。
	 * @param chatId
	 * @return
	 */
	public boolean createLike(int chatId) {
		String insertSql = "INSERT INTO likes(like_count,chat_id) values(0, ?)";

		PreparedStatement insert = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setInt(1, chatId);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("likesデータの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("likesデータの追加が完了しました。");
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	//チャットidからそのチャットのいいねカウントをインクリメントする
	public boolean addLikeCount(int chatId) {
		String insertSql = "UPDATE likes SET like_count = like_count + 1 WHERE chat_id = ?";

		PreparedStatement update = null;

		try {
			update = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			update.setInt(1, chatId);
			int resultNum = update.executeUpdate();
			if(resultNum == 0) {
				System.out.println("likesデータの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("likesデータの追加が完了しました。");
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;

	}
}
