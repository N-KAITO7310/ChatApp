package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AddLikeCountLogic;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeServlet() {
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

		//クリックされたチャットのidを取得
		String chatIdStr = request.getParameter("chatId");
		int chatId = Integer.parseInt(chatIdStr);
		//そのチャットが属するチャットルームのidも遷移先で利用するために取得
		String roomIdStr = request.getParameter("room_id");
		int roomId = Integer.parseInt(roomIdStr);

		//クリックされたチャットのidに紐づいたlikesテーブルのカウントを+1する
		AddLikeCountLogic bo = new AddLikeCountLogic();
		boolean result = bo.execute(chatId);

		String errMsg =  "";
		if(!result) {
			System.out.println("いいねのカウントアップができませんでした");
			errMsg += "「いいね」できませんでした。";
			request.setAttribute("errMsg", errMsg);
		}

		response.sendRedirect("/ChatApp/ChatroomServlet?room_id=" + roomId);
	}

}
