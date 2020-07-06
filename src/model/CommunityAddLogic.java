package model;

import dao.ChatroomDAO;
/**
 * group_statusがコミュニティのチャットルームを作成する。
 * @author kaitonakamura
 *
 */
public class CommunityAddLogic {
	public boolean execute(int selfId, int gameId, String communityName) {
		ChatroomDAO  dao = new ChatroomDAO();
		boolean result = dao.addCommunity(selfId, gameId, communityName);
		dao.closeConnect();

		return result;

	}
}
