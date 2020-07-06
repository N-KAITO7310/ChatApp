package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FetchAllGamesLogic;
import model.Game;

/**
 * Servlet implementation class GameListServlet
 */
@WebServlet("/GameListServlet")
public class GameListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//home.jspから遷移
		//上記で取得した情報を元にデータベースで検索して、ここでログイン結果を得る
		FetchAllGamesLogic bo = new FetchAllGamesLogic();
		List<Game> fetchAllGamesResult = bo.execute();


//		if(fetchAllGamesResult == null) {
//			System.out.println("ゲーム一覧がないので、ホーム画面に戻ります。");
//			response.sendRedirect("/ChatApp/jsp/home.jsp");
//		}
		//ゲーム一覧がある時はgameList.jspへ移動
		System.out.println("ゲーム一覧画面を表示");

		request.setAttribute("gameList", fetchAllGamesResult);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/gameList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
