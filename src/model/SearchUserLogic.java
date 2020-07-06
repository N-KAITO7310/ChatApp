package model;

import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;
/**
 * 文字列を引数でとり、名前にその文字列を含むユーザーデータを取得する
 * @author kaitonakamura
 *
 */
public class SearchUserLogic {
	public List<User> execute(String searchWord){
		List<User> searchedUsers = new ArrayList<>();

		UserDAO dao = new UserDAO();

		searchedUsers = dao.searchUser(searchWord);

		dao.closeConnect();

		return searchedUsers;
	}
}
