package model;

import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;

/**
 * 登録済みの全てのユーザー情報を取得する
 * @author kaitonakamura
 *
 */
public class UserAllFetchLogic {
	public List<User> execute(){
		List<User> allUser = new ArrayList<>();
		UserDAO dao = new UserDAO();

		allUser = dao.allUsers();

		dao.closeConnect();

		return allUser;


	}
}
