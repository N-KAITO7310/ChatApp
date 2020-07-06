package model;

import java.util.ArrayList;
import java.util.List;

import dao.ChatroomDAO;

/**
 * gameIdに紐づくコミュニティチャットルームのデータを全て取得する
 * @author kaitonakamura
 *
 */
public class FetchAllCommunityLogic {
	public List<Chatroom> execute(int gameId) {
		ChatroomDAO dao = new ChatroomDAO();

		List<Chatroom> communityList = new ArrayList<>();
		communityList = dao.findAllCommunity(gameId);

		dao.closeConnect();

		if(communityList == null) {
			System.out.println("コミュニティ一覧を取得できませんでした");
			return null;
		}

		System.out.println("コミュニティ一覧取得成功");
		return communityList;
	}
}
