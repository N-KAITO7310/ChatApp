package model;

import dao.ChatDAO;

/**
 * 最新のチャットを取得する。チャットデータを追加した時に、中間テーブルでその追加したレコードのidを利用するために使用。
 * @author kaitonakamura
 *
 */
public class fetchLatestChatLogic {
	public Chat execute() {
		ChatDAO dao = new ChatDAO();
		Chat latestChat = dao.fetchLatestChat();
		dao.closeConnect();

		if(latestChat == null) {
			System.out.println("最新のチャットデータの取得に失敗");
		}

		return latestChat;

	}
}
