package model;

import dao.ChatDAO;

/**
 * チャットを追加するBO。
 * @author kaitonakamura
 *
 */
public class ChatAddLogic {
	public boolean execute(int self_id,int thisChatroomId,String comment) {

		ChatDAO dao = new ChatDAO();
		boolean result = dao.addChat(self_id, thisChatroomId, comment);
		dao.closeConnect();

		if(!result) {
			System.out.println("チャット追加処理が正しく動作していません");
		}


		return result;
	}
}
