package model;

import dao.UserDAO;

/**
 * ユーザー情報の更新
 * @author kaitonakamura
 *
 */
public class PutUserLogic {
	public boolean execute(int user_id,int option, String introduction, String img_url) {
		UserDAO dao = new UserDAO();

		boolean result = false;


		if(option == 0) {
			System.out.println("自己紹介・画像URLを更新します");
			result = dao.putUserInfo(user_id, introduction, img_url);

		}else if(option == 1) {
			System.out.println("自己紹介を更新します");
			result = dao.putUserIntroduction(user_id, introduction);

		}else if(option == 2) {
			System.out.println("画像URLを更新します");
			result = dao.putUserImgUrl(user_id, img_url);

		}
		dao.closeConnect();

		return result;
	}
}
