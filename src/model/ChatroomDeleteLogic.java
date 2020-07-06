package model;

import dao.ChatroomDAO;

/**
 * チャットルームを削除するBO。ホストユーザーの解散ボタンで利用する。紐づいているデータも全て消える。
 * @author kaitonakamura
 *
 */
public class ChatroomDeleteLogic {
	public boolean execute(int room_id) {
		ChatroomDAO dao = new ChatroomDAO();
		boolean result = dao.deleteRoom(room_id);
		dao.closeConnect();

		return result;
	}
}
