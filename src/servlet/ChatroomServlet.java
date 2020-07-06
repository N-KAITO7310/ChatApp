package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AddAlready_readsLogic;
import model.Chat;
import model.ChatAddLogic;
import model.Chatroom;
import model.CreateLikeLogic;
import model.FetchAllChatBelongingToThisRoomLogic;
import model.FetchOneChatroomLogic;
import model.FindUserRelationFromChatroomidLogic;
import model.User;
import model.fetchLatestChatLogic;
import security.CheckFormString;
import security.NGWordChecker;
import security.XSSPrevention;

/**
 * Servlet implementation class ChatroomServlet
 */
@WebServlet("/ChatroomServlet")
public class ChatroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatroomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String errMsg = (String)request.getAttribute("errMsg");
		if(!(errMsg == null || errMsg.equals("") || errMsg.length() == 0)) {
			request.setAttribute("errMsg", errMsg);
		}
		String thisChatroomIdStr = request.getParameter("room_id");
		int thisChatroomId = Integer.parseInt(thisChatroomIdStr);

		FetchOneChatroomLogic bo_room = new FetchOneChatroomLogic();
		Chatroom thisRoom = bo_room.execute(thisChatroomId);
		//ここから上記のchatroomのidをもつchatを全て日時の昇順取得する
		FetchAllChatBelongingToThisRoomLogic bo_chat = new FetchAllChatBelongingToThisRoomLogic();
		List<Chat> chats = new LinkedList<>();
		chats = bo_chat.execute(thisChatroomId);

		request.setAttribute("thisRoom", thisRoom);
		request.setAttribute("chats", chats);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/chatroom.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String errMsg =  "";
		//チャットの投稿処理
		//投稿先のチャットルームidを取得するため、chatroom.jspk内で、<input type="hidden" name="chatroomId" value=<% chatroom.id %>>のように情報を埋め込んでおくこと。
		String thisChatroomIdStr = request.getParameter("chatroomId");
		int thisChatroomId = Integer.parseInt(thisChatroomIdStr);

		//ログイン中の自分のユーザーデータとそのid
		HttpSession session = request.getSession();
		User self = (User)session.getAttribute("self");
		int self_id = self.getId();

		//入力されたコメント
		String comment = request.getParameter("comment");
		//入力nullチェック
		CheckFormString bo_check = new CheckFormString();
		boolean checkResult = bo_check.execute(comment);

		if(!checkResult) {
			System.out.println("CheckFormStringでfalse。投稿前の状態のチャットルームへリダイレクトします。");
			errMsg = "未入力です。";
			response.sendRedirect("/ChatApp/ChatroomServlet?room_id="+thisChatroomId+"&errMsg="+errMsg);

		} else {
			//XSS対策
			XSSPrevention xss = new XSSPrevention();
			comment = xss.escapeHTML(comment);

			//NGWordチェック
			NGWordChecker bo_ngWord = new NGWordChecker();
			boolean result_ngWord = bo_ngWord.execute(comment);

			if(!result_ngWord) {
				System.out.println("NGWordCheckerでfalse。投稿前の状態のチャットルームへリダイレクトします。");
				errMsg = "NGワードが含まれています。";
				response.sendRedirect("/ChatApp/ChatroomServlet?room_id="+thisChatroomId+"&errMsg="+errMsg);
			}else {
				//データベースへチャットを追加
				ChatAddLogic bo = new ChatAddLogic();
				boolean addResult = bo.execute(self_id,thisChatroomId, comment);

				if(!addResult) {
					System.out.println("チャット追加ができませんでした");
					errMsg += "チャット追加ができませんでした。";
				}

				//チャットルームに所属している自分以外のユーザーidと、追加したチャットのidを用いて、already_reads中間テーブルを追加する
				//追加したチャットのidを取得
				fetchLatestChatLogic bo_latest = new fetchLatestChatLogic();
				Chat latestChat = bo_latest.execute();
				int latestChatId = latestChat.getId();

				//チャットを追加したグループに所属するユーザーのidを取得
				FindUserRelationFromChatroomidLogic bo_fetchUsers = new FindUserRelationFromChatroomidLogic();
				List<User> groupUsersList = bo_fetchUsers.execute(thisChatroomId, self_id);
				List<Integer> groupUserIds = new ArrayList<>();
				for(User user : groupUsersList) {
					groupUserIds.add(user.getId());
				}

				//既読関係の追加
				AddAlready_readsLogic bo_read = new AddAlready_readsLogic();
				boolean result_addRead = bo_read.execute(latestChatId, groupUserIds);

				if(result_addRead) {
					System.out.println("既読中間テーブルが追加できませんでした");
					errMsg += "グループユーザーの既読関係を構成できませんでした。";
				}

				//追加されたチャットのidを用いてlikesテーブルにレコードを追加
				CreateLikeLogic bo_like = new CreateLikeLogic();
				boolean result_like = bo_like.execute(latestChatId);

				if(result_like) {
					System.out.println("いいねテーブルに追加できませんでした");
					errMsg += "いいねを追加できませんでした。";
				}

				//getで表示し直す
				response.sendRedirect("/ChatApp/ChatroomServlet?room_id="+thisChatroomId+"&errMsg="+errMsg);
			}
		}
	}
}
