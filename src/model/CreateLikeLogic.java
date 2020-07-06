package model;

import dao.LikeDAO;

/**
 * 特定のチャットに対し紐づくいいねカウントのレコードを作成する
 * @author kaitonakamura
 *
 */
public class CreateLikeLogic {
	public boolean execute(int chatId) {
		LikeDAO dao = new LikeDAO();
		boolean result = dao.createLike(chatId);
		dao.closeConnect();

		return result;
	}
}
