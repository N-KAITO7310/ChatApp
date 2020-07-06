package model;

import java.util.ArrayList;
import java.util.List;

import dao.GameDAO;
/**
 * 全てのgamesレコードを取得する
 * @author kaitonakamura
 *
 */
public class FetchAllGamesLogic {
	public List<Game> execute() {
		GameDAO dao = new GameDAO();

		List<Game> gameList = new ArrayList<>();
		gameList = dao.findAll();

		dao.closeConnect();

		if(gameList == null) {
			System.out.println("ゲーム一覧を取得できませんでした");
			return null;
		}

		System.out.println("ゲーム一覧取得成功");
		return gameList;
	}
}
