package model;

import dao.UserDAO;

/**
 * ログイン処理
 * @author kaitonakamura
 *
 */
public class LoginLogic {
	public User excute(String email, String user_password) {
		UserDAO dao = new UserDAO();

		User loginUser = dao.login(email, user_password);
		dao.closeConnect();

		if(loginUser == null) {
			System.out.println("ログインロジック内でnullが返されています");
			return null;
		}

		System.out.println("ログインロジック成功");
		return loginUser;
	}
}
