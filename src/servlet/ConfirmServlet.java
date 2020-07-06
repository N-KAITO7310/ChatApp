package servlet;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import security.CheckFormString;
import security.XSSPrevention;

/**
 * Servlet implementation class ConfirmServlet
 */
@WebServlet("/ConfirmServlet")
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//登録画面からの遷移
		//入力情報の取得
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
		String user_password = request.getParameter("user_password");
		String nickname = request.getParameter("nickname");

		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		StringBuilder builder = new StringBuilder();
		builder.append(year + "/");
		builder.append(month + "/");
		builder.append(day);
		String date_of_birth = builder.toString();

		String sex = request.getParameter("sex");
		String introduction = request.getParameter("introduction");
		String profile_image_url = request.getParameter("profile_image_url");

		//ファイルの絶対パス化
		Path path = Paths.get(profile_image_url);
        //ファイルパスを取得する
        String str = path.toAbsolutePath().toString();
        profile_image_url = str;

		//未入力チェック＊required属性があるので、未対応の場合のための処理
		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(user_name, email, user_password, nickname, date_of_birth, sex);

		if(!checkResult) {
			System.out.println("登録失敗。登録フォームへリダイレクトします。");
			response.sendRedirect("/jsp/register.jsp");
		}else {
			//XSS対策
			XSSPrevention xss = new XSSPrevention();
			user_name = xss.escapeHTML(user_name);
			email = xss.escapeHTML(email);
			user_password = xss.escapeHTML(user_password);
			nickname = xss.escapeHTML(nickname);
			date_of_birth = xss.escapeHTML(date_of_birth);


			//確認画面での表示のためユーザー情報を作成し、セッションに登録＊データベースにはまだ登録しない
			User user = new User(user_name,email,user_password,nickname,date_of_birth,sex,introduction,profile_image_url);

			request.setAttribute("notYetUser", user);

			//confirm.jsp内でリクエストスコープから取得して表示するのはいいが、confirm.jspからPOSTでRegisterServletに
			//遷移した場合、jsp内でリクエストスコープに登録した場合それを受け取れるのか
			//わからないので、セッションスコープも使えるよう記述しておく
			HttpSession session = request.getSession();
			session.setAttribute("notYetUser", user);

			//確認画面へ遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/confirm.jsp");
			dispatcher.forward(request, response);
		}
	}

}
