package model;

import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;
/**
 * チャットルームidから自分以外の所属しているメンバー情報を取得する
 * @author kaitonakamura
 *
 */
public class FindUserRelationFromChatroomidLogic {
	public List<User> execute(int roomId, int selfId){

		List<User> friends = new ArrayList<>();

		UserDAO dao = new UserDAO();

		friends =  dao.findFriendsFromChatroomid(roomId, selfId);

		dao.closeConnect();

		if(friends.size() == 0 || friends == null) {
			System.out.println("FindUserRelationFromChatroomidLogic：取得したデータが0かnullです。");
		}

		return friends;
	}
}
