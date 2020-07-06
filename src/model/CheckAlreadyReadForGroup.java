package model;

import dao.WasReadDAO;
/**
 * グループのチャットルームで、特定のチャット投稿者以外のユーザーにどれだけ読まれているかをチェックするBO
 * @author kaitonakamura
 *
 */
public class CheckAlreadyReadForGroup {
	public int execute(int chatId) {
		WasReadDAO dao = new WasReadDAO();
		int alreadyReadCount = dao.checkAlreadyReadForGroup(chatId);
		dao.closeConnect();

		if(alreadyReadCount == 0) {
			System.out.println("CheckAlreadyReadForGroup；既読関係が取得できていないか、既読０です");
		}

		return alreadyReadCount;
	}
}
