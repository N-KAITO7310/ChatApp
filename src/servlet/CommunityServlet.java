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
import javax.servlet.http.HttpSession;

import model.Chatroom;
import model.CheckSelfInThisRoom;
import model.FetchOneChatroomLogic;
import model.FindUserRelationFromChatroomidLogic;
import model.RoomInLogic;
import model.User;

/**
 * Servlet implementation class CommunityServlet
 */
@WebServlet("/CommunityServlet")
public class CommunityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityServlet() {
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

		CheckSelfInThisRoom bo_check = new  CheckSelfInThisRoom();
		boolean result_thisRoom_In = bo_check.execute(self.getId(), thisRoom.getId());
		if(result_thisRoom_In){
			groupUsers.add(self);
		}

		request.setAttribute("groupUsers", groupUsers);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/community.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		//コミュニティ参加処理
		String thisRoomIdStr = request.getParameter("room_id");
		int thisRoomId = Integer.parseInt(thisRoomIdStr);

		HttpSession session = request.getSession();
		User self = (User)session.getAttribute("self");
		int selfId = self.getId();
		//RoomInLogicの引数の仕様上リストにする必要があるのでリストに追加。＊修正ポイント
		List<User> users = new ArrayList<>();
		users.add(self);

		//自分のidとルームidで、このチャットルームに参加する。
		RoomInLogic bo = new RoomInLogic();
		boolean result = bo.execute(users, thisRoomId);

		if(result) {
			System.out.println("コミュニティ参加処理完了");
		}else {
			System.out.println("コミュニティ参加処理失敗");
		}

//		RequestDispatcher dispatcher = request.getRequestDispatcher("/HomeServlet");
//		dispatcher.forward(request, response);

		request.setAttribute("room_id", thisRoomId);
		doGet(request, response);

	}

}
