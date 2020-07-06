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
import model.ChatroomFindSelfBelongingLogic;
import model.FindUserRelationFromChatroomidLogic;
import model.User;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		User self = (User)session.getAttribute("self");
		int selfId = self.getId();

		String errMsg = "";

		System.out.println("ログインユーザー id：" + selfId + "名前" + self.getNickname());


		//ここで行うのは、中間テーブルを検索して、自分が所属しているchatroomの情報を取得すること(id、status、group・communityの場合はグループ名を取得)
		ChatroomFindSelfBelongingLogic bo_selfroom = new ChatroomFindSelfBelongingLogic();
		List<Chatroom> selfChatrooms = bo_selfroom.execute(selfId);


		//検索結果が０だった場合これ以降の処理でエラーの元になるので、そのための処理
		if(selfChatrooms == null || selfChatrooms.size() == 0) {
			errMsg = "所属チャットルームなし";
			request.setAttribute("errMsg", errMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/home.jsp");
			dispatcher.forward(request, response);
		}

		//groupとcommunityはそのまま情報を元にインスタンスを作ってリストに入れ、リクエストスコープに登録する。
		List<Chatroom> OneToOne = new ArrayList<>();
		List<Chatroom> groups = new ArrayList<>();
		List<Chatroom> communities = new ArrayList<>();
		//各ステータスに応じて仕分け
		for(Chatroom chatroom : selfChatrooms) {
			String group_status = chatroom.getGroup_status();
			System.out.println("グループステータス" + group_status);
			if(group_status.equals("group")) {
				 System.out.print("groupを１つ確認");
				groups.add(chatroom);
			}else if(group_status.equals("gameComunity")) {
				System.out.print("communityを１つ確認");
				communities.add(chatroom);
			}else if(group_status.equals("OneToOne")) {
				System.out.print("１対１の関係を１つ確認");
				OneToOne.add(chatroom);
			}
		}

		request.setAttribute("groups", groups);
		request.setAttribute("communities", communities);


		//OneToOneのものは、roomのidを用いて中間を検索し、２つヒットしたうちの自分ではない方のユーザーをusersから検索し、そのユーザーの情報を取得する

		//OneToOneのヒット件数が０だった場合、この後の処理でエラーが出るのでそのための処理
		if(OneToOne == null || OneToOne.size() == 0) {
			errMsg = "友達なし";
			request.setAttribute("errMsg", errMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/home.jsp");
			dispatcher.forward(request, response);
		}

		//１対１関係にある友人のユーザーデータリスト
		List<User> friends = new ArrayList<>();
		//自分が所属する１対１のチャットルームのid

		//自分が所属する全てのチャットルームから１対１のもののidだけを抽出する
		List<Integer> oneToOneRoomIds = new ArrayList<>();
		for(Chatroom onetoone : OneToOne) {
			oneToOneRoomIds.add(onetoone.getId());
		}

		//３つのテーブルをjoinして、そのチャットルームに所属する自分でない方のユーザーデータを検索する。このロジックはgroup、communityでも使えるようリストで返すようにしている。
		FindUserRelationFromChatroomidLogic bo_FindUsers = new FindUserRelationFromChatroomidLogic();
		//所属しているOneToOneとわかったチャットルームのidでそれぞれ友達ユーザーを検索する
		//正常に動作していればここでは、ユーザーデータが一件しかないはずなので、その一件をリストに改めて取り出して、同一のリストに入れる
		for(int roomId : oneToOneRoomIds) {
			List<User> tempFriendsList = new ArrayList<>();
			tempFriendsList = bo_FindUsers.execute(roomId, selfId);
			User friend = tempFriendsList.get(0);
			friends.add(friend);
		}

		System.out.println("友達人数" + friends.size());

		//取得したfriendsをリクエストスコープに登録する
		request.setAttribute("friends", friends);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/home.jsp");
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
