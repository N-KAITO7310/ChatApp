package model;

import dao.WasReadDAO;
/**
 * 既読処理
 * @author kaitonakamura
 *
 */
public class ReadLogic {
	public boolean execute(int chat_id, int user_id) {
		WasReadDAO dao = new WasReadDAO();
		boolean result = dao.read(chat_id, user_id);
		dao.closeConnect();

		return result;
	}
}
