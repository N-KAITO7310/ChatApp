package model;

import dao.UserDAO;
/**
 * 自分のidと、相手のidから、所属している１対１関係のチャットルームを取得する
 * @author kaitonakamura
 *
 */
public class FindRoomIdfromFriendIdLogic {
	public int execute(int selfId, int friendId) {
		UserDAO dao = new UserDAO();
		int roomId = dao.findRoomIdfromFriendId(selfId, friendId);
		dao.closeConnect();

		return roomId;
	}

}
