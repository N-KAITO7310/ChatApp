package model;

import dao.UserDAO;

/**
 * 自分と第二引数のユーザーが友達関係（OneToOneのchatroomを持つ）かどうかをチェックするBO
 * @author kaitonakamura
 *
 */
public class CheckIsFriendLogic {
	public boolean execute(int selfId, int selectedUserId) {
		UserDAO dao = new UserDAO();

		boolean result = dao.checkIsFriend(selfId, selectedUserId);

		dao.closeConnect();

		if(result) {
			System.out.println("CheckIsFriendLogic：対象ユーザーとのrelation結果を返します");
		}else {
			System.out.println("CheckIsFriendLogic：対象ユーザーとのrelation結果を取得できていません");
		}

		return result;
	}
}
