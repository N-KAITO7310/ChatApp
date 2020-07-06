package model;

import dao.UserDAO;

/**
 * idから特定のユーザーを取得する
 * @author kaitonakamura
 *
 */
public class FindOneUserLogic {
	public User execute(int user_id) {
		UserDAO dao = new UserDAO();
		User user = dao.findUser(user_id);
		dao.closeConnect();

		if(user == null) {
			System.out.println("ユーザーが正しく取得できていません");
			return null;
		}

		return user;
	}
}
