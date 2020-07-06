package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;
import security.CheckFormString;
import security.XSSPrevention;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
//		dispatcher.forward(request, response);

		response.sendRedirect("/ChatApp/jsp/login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//login.jspから遷移
		//ログインフォームに入力した情報を取得する
		String email = request.getParameter("email");
		String user_password = request.getParameter("user_password");

		//入力チェック処理
		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(email, user_password);

		if(!checkResult) {
			System.out.println("ログイン失敗。ログインフォームへリダイレクトします。");
			response.sendRedirect("/ChatApp/jsp/login.jsp");
		}else {
			//XSS対策
			XSSPrevention xss = new XSSPrevention();
			email = xss.escapeHTML(email);
			user_password = xss.escapeHTML(user_password);

			//上記で取得した情報を元にデータベースで検索して、ここでログイン結果を得る
			LoginLogic bo = new LoginLogic();
			User loginResult = bo.excute(email, user_password);

			if(loginResult == null) {
				System.out.println("ログイン失敗。ログインフォームへリダイレクトします。");
				response.sendRedirect("/ChatApp/jsp/login.jsp");
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("self", loginResult);

				System.out.println("ログイン成功。ログイン情報をセッションに登録し、HomeServletにリダイレクトします");
				response.sendRedirect("/ChatApp/HomeServlet");
			}
		}




	}

}
