package model;

import dao.ChatroomDAO;

/**
 * チャットルームを追加するBO
 * @author kaitonakamura
 *
 */
public class ChatroomAddLogic {
	public boolean execute(int selfId,String groupName) {

		ChatroomDAO dao = new ChatroomDAO();
		boolean result = dao.addGroupChatroom(selfId, groupName);
		dao.closeConnect();

		if(result) {
			return result;
		}

		return false;
	}
}
