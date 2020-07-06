package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ReadLogic;
import model.User;

/**
 * Servlet implementation class WasReadServlet
 */
@WebServlet("/WasReadServlet")
public class WasReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WasReadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//既読ボタンが押されたチャットのidを取得
		String chatIdStr = request.getParameter("chatId");
		int chatId = Integer.parseInt(chatIdStr);
		//そのチャットが属するチャットルームのidも遷移先で利用するために取得
		String roomIdStr = request.getParameter("room_id");
		int roomId = Integer.parseInt(roomIdStr);

		HttpSession session = request.getSession();
		User self = (User) session.getAttribute("self");
		int selfId = self.getId();

		ReadLogic bo = new ReadLogic();
		boolean readResult = bo.execute(chatId, selfId);

		if(readResult) {
			System.out.println("既読機能エラー");
		}

		response.sendRedirect("/ChatApp/ChatroomServlet?room_id=" + roomId);
	}

}
