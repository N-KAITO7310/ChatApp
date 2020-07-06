package model;

import dao.ChatroomDAO;
/**
 * 最新のチャットルームレコードを取得する
 * @author kaitonakamura
 *
 */
public class FetchLatestChatroomLogic {
	public Chatroom execute() {

		ChatroomDAO dao = new ChatroomDAO();
		Chatroom latestChatroom = dao.fetchLatestChatroom();
		dao.closeConnect();

		if(latestChatroom == null) {
			return null;
		}

		return latestChatroom;
	}
}
