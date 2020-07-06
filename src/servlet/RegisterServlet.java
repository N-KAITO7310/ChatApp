package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.RegisterLogic;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//ここでは単に登録画面へと遷移
		response.sendRedirect("/ChatApp/jsp/register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//ユーザー登録確認画面からの遷移
		//ここでは一応セッションスコープで受け取る、POSTでリクエストスコープで受け取れるならその方がいいのであとで要チェック
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("notYetUser");

		//ここからuserを使ってデータベース登録処理を行う
		RegisterLogic bo = new RegisterLogic();
		boolean result = bo.execute(user);

		String dispatcherUrl = "";

		if(result) {
			//登録処理が完了したら、セッションスコープの登録用ユーザーデータを削除する
			session.removeAttribute("notYetUser");
			dispatcherUrl = "/jsp/login.jsp";

			RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherUrl);
			dispatcher.forward(request, response);
		}else {
			//登録失敗の場合の処理はあとでまた考えるが、ひとまずトップへリダイレクト
			System.out.println("登録失敗");
			response.sendRedirect("/jsp/index.jsp");
		}

	}

}
