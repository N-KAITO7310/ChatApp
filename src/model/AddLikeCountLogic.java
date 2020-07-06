package model;

import dao.LikeDAO;
/**
 * 特定のチャットのいいねをプラス１するBO。
 * @author kaitonakamura
 *
 */
public class AddLikeCountLogic {
	public boolean execute(int chatId) {

		LikeDAO dao = new LikeDAO();
		boolean result = dao.addLikeCount(chatId);
		dao.closeConnect();

		return result;
	}
}
