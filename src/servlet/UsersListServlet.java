package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SearchUserLogic;
import model.User;
import model.UserAllFetchLogic;
import security.CheckFormString;
import security.XSSPrevention;

/**
 * Servlet implementation class UsersListServlet
 */
@WebServlet("/UsersListServlet")
public class UsersListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		List<User> allUsers = new ArrayList();
		UserAllFetchLogic bo = new UserAllFetchLogic();

		allUsers = bo.execute();

		if(allUsers == null || allUsers.size() == 0) {
			System.out.println("登録ユーザが正しく取得できていません。確認してください");
			request.setAttribute("allUsers", null);
		}else {
			System.out.println("一件以上のユーザーを取得しました。リクエストスコープに登録します。");
			request.setAttribute("allUsers", allUsers);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/usersList.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String searchWord = request.getParameter("searchWord");

		//入力nullチェック
		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(searchWord);

		if(!checkResult) {
			System.out.println("検索ワードが入力されていません");
			doGet(request, response);

		} else {
		//XSS対策
		XSSPrevention xss = new XSSPrevention();
		searchWord = xss.escapeHTML(searchWord);

		List<User> searchedUsers = new ArrayList<>();

		SearchUserLogic bo = new SearchUserLogic();

		searchedUsers = bo.execute(searchWord);

		if(searchedUsers == null || searchedUsers.size() == 0) {
			System.out.println("検索ユーザの数が正しく取得できていないか、存在しませんでした。");
			response.sendRedirect("/ChatApp/UsersListServlet");
		}else {
			System.out.println("一件以上のユーザーを取得しました。リクエストスコープに登録します。");
			request.setAttribute("allUsers", searchedUsers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/usersList.jsp");
			dispatcher.forward(request, response);
		}
		}
	}

}
