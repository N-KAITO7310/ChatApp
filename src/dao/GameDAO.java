package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Game;

public class GameDAO {
	Connection con = null;

	public GameDAO() {
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
	 * 全てのゲーム作品データを返す
	 * @return
	 */
	public List<Game> findAll() {
		List<Game> gameList = new ArrayList<>();

		String sql_gameList_all_column = "SELECT * FROM games";

		PreparedStatement pstmt = null;

        try {
		pstmt = con.prepareStatement(sql_gameList_all_column);// ステートメントオブジェクトを生成
		ResultSet fetchResult = pstmt.executeQuery();// クエリーを実行して結果セットを取得

		while(fetchResult.next()) {
			int id = fetchResult.getInt("id");
			String game_title = fetchResult.getString("game_title");
			String info = fetchResult.getString("info");
			String game_img_url = fetchResult.getString("game_img_url");

			Game game = new Game(id, game_title, info, game_img_url);
			gameList.add(game);
		}
		return gameList;

        } catch (SQLException e) {
            e.printStackTrace();
        //} catch (ClassNotFoundException e) {
          //  e.printStackTrace();
        }
		return null;
	}


	/**
	 * idから特定のゲーム作品データを取得する
	 * @param gameId
	 * @return
	 */
	public Game findGame(int gameId) {
		String sql_game_all_column = "SELECT * FROM games WHERE id = ?";

		PreparedStatement pstmt = null;

	    try {
	    pstmt = con.prepareStatement(sql_game_all_column);// ステートメントオブジェクトを生成
	    pstmt.setInt(1, gameId);
		ResultSet fetchResult = pstmt.executeQuery();// クエリーを実行して結果セットを取得

		while(fetchResult.next()) {
			int gm_id = fetchResult.getInt("id");
			String gm_title = fetchResult.getString("game_title");
			String gm_info = fetchResult.getString("info");
			String gm_img_url = fetchResult.getString("game_img_url");

			Game game = new Game(gm_id, gm_title, gm_info, gm_img_url);
			return game;
		}

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
