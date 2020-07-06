package model;

import dao.WasReadDAO;

/**
 * 特定のユーザーが特定のチャットを既読しているかどうかをチェックするためのメソッド
 * @author kaitonakamura
 *
 */
public class CheckAlreadyRead {
	public boolean execute(int userId, int chatId) {
		WasReadDAO dao = new WasReadDAO();
		boolean result = dao.checkAlreadyRead(userId, chatId);

		return result;
	}
}
