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
import model.ChatroomDeleteLogic;
import model.FetchOneChatroomLogic;
import model.FindUserRelationFromChatroomidLogic;
import model.User;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//選択されたチャットルームの情報を取得してリクエストスコープに登録し、遷移する。
		String selectedRoomIdStr = request.getParameter("room_id");
		int selectedRoomId = Integer.parseInt(selectedRoomIdStr);

		FetchOneChatroomLogic bo = new FetchOneChatroomLogic();
		Chatroom thisRoom = bo.execute(selectedRoomId);
		request.setAttribute("thisRoom", thisRoom);

		//ここで所属する全メンバーを取得する必要がある。*自分も含める必要がある。
		HttpSession session = request.getSession();
		User self = (User) session.getAttribute("self");
		FindUserRelationFromChatroomidLogic bo_member = new FindUserRelationFromChatroomidLogic();
		List<User> groupUsers = bo_member.execute(selectedRoomId, self.getId());
		groupUsers.add(self);
		request.setAttribute("groupUsers", groupUsers);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/group.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//グループ解散処理＊本来ならdoDeleteに書くべき処理
		String room_idStr = request.getParameter("room_id");
		int room_id = Integer.parseInt(room_idStr);

		ChatroomDeleteLogic bo_delete = new ChatroomDeleteLogic();
		boolean result_delete = bo_delete.execute(room_id);

		if(result_delete) {
			System.out.println("グループの削除が完了しました。");
		}else {
			System.out.println("グループの削除に失敗しました。");
		}

		response.sendRedirect("/ChatApp/HomeServlet");

	}

}
