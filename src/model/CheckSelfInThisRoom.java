package model;

import dao.UserDAO;

/**
 * 自分がそのチャットルームに所属しているかどうかをチェックするBO。コミュニティ参加の際に表示を切り替えるために利用。
 * @author kaitonakamura
 *
 */
public class CheckSelfInThisRoom {
	public boolean execute(int selfId, int room_id) {
		UserDAO dao = new UserDAO();
		boolean result = dao.checkSelfInThisRoom(selfId, room_id);
		dao.closeConnect();

		if(result) {
			System.out.println("CheckSelfInThisRoom正常動作");
		}else {
			System.out.println("CheckSelfInThisRoomでfalse");
		}

		return result;
	}
}
