package model;

import java.util.ArrayList;
import java.util.List;

import dao.ChatroomDAO;

/**
 * 中間テーブルusers_chatroomsにレコードを追加する。第一引数に所属することになったユーザーのリストをとる。
 * ユーザーidとチャットルームidを紐づけて、所属情報を追加する。
 * @author kaitonakamura
 *
 */
public class RoomInLogic {
	public boolean execute(List<User> checkedUsers, int thisRoomId) {
		List<Integer> checkedUsersIdList = new ArrayList<>();

		if(checkedUsers == null || checkedUsers.size() == 0) {
			return false;
		}

		for(User user : checkedUsers) {
			checkedUsersIdList.add(user.getId());
		}


		ChatroomDAO dao = new ChatroomDAO();
		boolean result = dao.roomIn(checkedUsersIdList, thisRoomId);
		dao.closeConnect();

		if(result) {
			return result;
		}

		return false;
	}
}
