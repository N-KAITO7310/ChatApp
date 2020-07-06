package model;

import dao.ChatroomDAO;

/**
 * 一対一のチャットルームを追加するBO。
 * @author kaitonakamura
 *
 */
public class AddOneToOneChatroomLogic {
	public boolean execute(int selfId, int otherId) {
		ChatroomDAO dao = new ChatroomDAO();
		boolean result = dao.addOneToOneChatroom(selfId, otherId);
		dao.closeConnect();

		if(result) {
			System.out.println("AddOneToOneChatroomLogic：OneToOne追加完了");
		}else {
			System.out.println("AddOneToOneChatroomLogic：OneToOne追加失敗");
		}

		return result;
	}
}
