package model;

import dao.UserDAO;
/**
 * ユーザー登録処理
 * @author kaitonakamura
 *
 */
public class RegisterLogic {
	public boolean execute(User user) {
		String user_name = user.getUser_name();
		String email = user.getEmail();
		String user_password = user.getUser_password();
		String nickname = user.getNickname();
		String date_of_birth = user.getDate_of_birth();
		String sex = user.getSex();
		String introduction = user.getIntroduction();
		String profile_image_url = user.getProfile_image_url();

		UserDAO dao = new UserDAO();
		boolean result = dao.insertUser(user_name, email, user_password, nickname, date_of_birth, sex, introduction, profile_image_url);
		dao.closeConnect();


		return result;
	}
}
