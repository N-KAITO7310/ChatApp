package model;

import java.util.List;

import dao.ChatroomDAO;

/**
 * 自分が所属している全てのチャットルームデータを取得するBO
 * @author kaitonakamura
 *
 */
public class ChatroomFindSelfBelongingLogic {
	public List<Chatroom> execute(int selfId){

		ChatroomDAO dao = new ChatroomDAO();
		List<Chatroom> chatrooms = dao.findAllBelongingRoom(selfId);
		dao.closeConnect();

		if(chatrooms.size() == 0 || chatrooms == null) {
			System.out.println("ChatroomFindSelfBelongingLogic：取得したレコードが0またはnullです。");
			return null;
		}

		return chatrooms;

	}
}
