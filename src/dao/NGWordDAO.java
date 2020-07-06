package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NGWordDAO {
	Connection con = null;

	public NGWordDAO() {
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
	 * 全てのNGワードデータを取得する
	 * @return
	 */
	public List<String> findAllNGWords() {
        //選んだチャットルームのidからそのチャットルームの全NGワードの情報を取得する。
		String sql_NGWordList_all_column = "SELECT * FROM ng_words";

		PreparedStatement findStm = null;

		try {
            List<String> NGWordList = new ArrayList<>();
            findStm = con.prepareStatement(sql_NGWordList_all_column);// ステートメントオブジェクトを生成
            ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得
            while(resultAll.next()) {
                String ng_word = resultAll.getString("word");
                NGWordList.add(ng_word);
            }
            System.out.println("ChatroomDAO：該当するNGワードを全てリターンします");
            return NGWordList;
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("findAllNGWords()で異常な動作が行われています。該当箇所を確認してください。");
        return null;
    }
}
