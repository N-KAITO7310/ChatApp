package model;

import dao.ChatroomDAO;

/**
 * idから特定のチャットルームのデータを取得する
 * @author kaitonakamura
 *
 */
public class FetchOneChatroomLogic {
	public Chatroom execute(int chatroomId) {

		ChatroomDAO dao = new ChatroomDAO();
		Chatroom room = dao.findOneChatroom(chatroomId);
		dao.closeConnect();

		if(room == null) {
			System.out.println("該当のchatroom情報を取得できませんでした。");
			return null;
		}

		return room;
	}
}
