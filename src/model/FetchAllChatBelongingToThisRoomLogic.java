package model;

import java.util.ArrayList;
import java.util.List;

import dao.ChatDAO;
/**
 * 特定のチャットルームidに紐づいているチャットデータを全て取得する
 * @author kaitonakamura
 *
 */
public class FetchAllChatBelongingToThisRoomLogic {
	public List<Chat> execute(int room_id){
		ChatDAO dao = new ChatDAO();
		List<Chat> chats = new ArrayList<>();
		chats = dao.fetchAllChats(room_id);
		dao.closeConnect();

		if(chats == null || chats.size() == 0) {
			System.out.println("チャットがないか、取得できませんでした。");
			return null;
		}

		return chats;
	}
}
