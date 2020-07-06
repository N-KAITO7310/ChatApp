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
import model.ChatroomAddLogic;
import model.FetchLatestChatroomLogic;
import model.FindOneUserLogic;
import model.RoomInLogic;
import model.User;
import model.UserAllFetchLogic;
import security.CheckFormString;
import security.XSSPrevention;

/**
 * Servlet implementation class GroupCreateServlet
 */
@WebServlet("/GroupCreateServlet")
public class GroupCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String createGroupErrMsg = request.getParameter("createGrouppErrMsg");
		if(!(createGroupErrMsg == null || createGroupErrMsg.length() == 0)) {
			request.setAttribute("createGrouppErrMsg", createGroupErrMsg);
		}

		//グループ作成画面への遷移までの処理。UsersListの処理と全く同じ
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

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/groupCreate.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//チェックされたユーザーのidを取得
		String[] checkedUserIdsStr = request.getParameterValues("group_users");
		//入力されたグループ名を取得
		String groupName = request.getParameter("group_name");

		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(groupName);

		if(!checkResult) {
			System.out.println("グループ名が入力されていません");
			doGet(request, response);

		} else {
			//グループユーザーが選択されているかどうか
			if(checkedUserIdsStr.length == 0 || checkedUserIdsStr == null) {
				request.setAttribute("createGrouppErrMsg", "ユーザーが選択されていません。ユーザー欄にチェックをいれてください。");
				doGet(request, response);
			}
			//XSS対策
			XSSPrevention xss = new XSSPrevention();
			groupName = xss.escapeHTML(groupName);

			//該当idのユーザーデータを取得
			List<User> checkedUsers = new ArrayList<>();
			for(String idStr : checkedUserIdsStr) {
				int id = Integer.parseInt(idStr);
				FindOneUserLogic bo = new FindOneUserLogic();

				//ここでチェックされたユーザーを一人ずつ取得していく
				User checkedUser = bo.execute(id);
				checkedUsers.add(checkedUser);
			}

			//取得したグループ名を元に、チャットルームを作成。
			HttpSession session = request.getSession();
			User self = (User)session.getAttribute("self");
			int selfId = self.getId();
			ChatroomAddLogic bo_createLoom = new ChatroomAddLogic();
			boolean createChatroomResult = bo_createLoom.execute(selfId,groupName);

			//最新のチャットルームのidを取得
			FetchLatestChatroomLogic bo_fetchRoom = new FetchLatestChatroomLogic();
			Chatroom latestChatroom = bo_fetchRoom.execute();
			int thisRoomId = latestChatroom.getId();

			//チャットルームのidと上記で取得した選択ユーザーのidを用いて中間テーブルを作成。この時自分も所属することになるので、自分のユーザーデータも追加する。
			checkedUsers.add(self);
			RoomInLogic bo_roomIn = new RoomInLogic();
			boolean create_users_chatroomsResult = bo_roomIn.execute(checkedUsers, thisRoomId);

			//グループ追加処理、中間テーブル追加処理が正しく動作したかの確認
			if(createChatroomResult && create_users_chatroomsResult) {
				System.out.println("グループ追加処理が正しく動作しました。");
			}

			//リクエストスコープにここで作成したチャットルームのデータと、ユーザーのデータを登録
			request.setAttribute("groupUsers", checkedUsers);
			request.setAttribute("thisRoom", latestChatroom);


			//グループ詳細画面へ遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/group.jsp");
			dispatcher.forward(request, response);
		}
	}

}
