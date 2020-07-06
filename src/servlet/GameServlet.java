package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Chatroom;
import model.CommunityAddLogic;
import model.FetchAllCommunityLogic;
import model.FetchOneGameLogic;
import model.Game;
import model.User;
import security.CheckFormString;
import security.XSSPrevention;

/**
 * Servlet implementation class GameListServlet
 */
@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//home.jspから遷移

		String gameIdStr = request.getParameter("gameId");
		int gameId = Integer.parseInt(gameIdStr);
		//上記で取得した情報を元にデータベースで検索して、ここでログイン結果を得る
		FetchOneGameLogic bo1 = new FetchOneGameLogic();
		Game fetchOneGameResult = bo1.execute(gameId);

		FetchAllCommunityLogic bo2 = new FetchAllCommunityLogic();
		List<Chatroom> fetchAllCommunityResult = bo2.execute(fetchOneGameResult.getId());

		String dispatcherUrl = "";

		if(fetchAllCommunityResult == null || fetchAllCommunityResult.size() == 0) {
			System.out.println("ゲームが登録されてません。");
		}
		//ゲームidを元にチャットルームテーブルから該当のデータを取得する
		System.out.println("コミュニティを表示");

		request.setAttribute("game", fetchOneGameResult);
		request.setAttribute("community", fetchAllCommunityResult);


		//ゲーム情報がある時はgame.jspへ移動
		System.out.println("ゲーム詳細画面を表示");
		dispatcherUrl = "/jsp/game.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherUrl);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//ゲームタイトルに紐づいたコミュニティの新規作成処理
		HttpSession session = request.getSession();
		User self = (User) session.getAttribute("self");
		int selfId = self.getId();

		String gameIdStr = request.getParameter("gameId");
		int gameId = Integer.parseInt(gameIdStr);

		String communityName = request.getParameter("communityName");

		//入力nullチェック
		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(communityName);

		if(!checkResult) {
			System.out.println("コミュニティ名が入力されていません");
			doGet(request, response);

		} else {
			//XSS対策
			XSSPrevention xss = new XSSPrevention();
			communityName = xss.escapeHTML(communityName);

			//以下でコミュニティネームと自分のid、gameのidでコミュニティステータスのchatroomを作成する。
			CommunityAddLogic bo = new CommunityAddLogic();
			boolean result = bo.execute(selfId, gameId, communityName);

			if(result) {
				System.out.println("コミュニティ追加が正常に動作しました。");
			}else {
				System.out.println("コミュニティ追加が正常に動作しませんでした。");
			}


			doGet(request, response);
		}
	}

}
