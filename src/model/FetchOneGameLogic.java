package model;

import dao.GameDAO;
/**
 * idから特定のgamesレコードを取得する
 * @author kaitonakamura
 *
 */
public class FetchOneGameLogic {
	public Game execute(int gameId) {
		GameDAO dao = new GameDAO();

		Game game = new Game();
		game = dao.findGame(gameId);

		dao.closeConnect();

		if(game == null) {
			System.out.println("ゲーム詳細を取得できませんでした");
			return null;
		}

		System.out.println("ゲーム詳細取得成功");
		return game;
	}
}

