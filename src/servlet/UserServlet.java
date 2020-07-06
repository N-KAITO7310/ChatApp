package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AddOneToOneChatroomLogic;
import model.CheckIsFriendLogic;
import model.FindOneUserLogic;
import model.PutUserLogic;
import model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String updateErrMsg = "";
		updateErrMsg = (String) request.getAttribute("updateErrMsg");
		if(!(updateErrMsg == null || updateErrMsg.length() == 0)) {
			System.out.println("エラーメッセージなし");
			request.setAttribute("updateErrMsg", updateErrMsg);
		}

		String selectedUserIdStr = request.getParameter("userId");

		int selectedUserId = Integer.parseInt(selectedUserIdStr);
		HttpSession session = request.getSession();
		User selfUser = (User)session.getAttribute("self");
		int selfId = selfUser.getId();



		//ここで処理するのは単に上記で取得したidに該当するユーザーデータの取得とリクエストスコープへの登録
		FindOneUserLogic bo = new FindOneUserLogic();
		User selectedUser = bo.execute(selectedUserId);

		//自分かどうか
		boolean isSelf = false;
		//すでにチャット開始済みで友達かどうか
		boolean isFriend = false;

		//そのユーザーが自分自身か、すでに友達であるか、まだ友達でないかを判別し、その結果をパラメータに含める必要がある
		if(selfId == selectedUser.getId()) {
			isSelf = true;
		}else {
			//ここで、友達であるかどうかを検索する処理が必要になる
			System.out.println("友達かどうかチェックします");
			CheckIsFriendLogic bo_checkIsFriend = new CheckIsFriendLogic();
			isFriend = bo_checkIsFriend.execute(selfId, selectedUserId);

		}

		request.setAttribute("thisUser", selectedUser);
		request.setAttribute("isSelf", isSelf);
		request.setAttribute("isFriend", isFriend);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		User self = (User)session.getAttribute("self");
		int selfId = self.getId();

		//パラメータoptionの値により、自分のユーザー情報を変更する処理と、チャット未開始ユーザー詳細ページからその相手との１対１チャットルームを作成してその相手のユーザーページを表示し直す処理に分ける
		String postOptionStr = request.getParameter("option");
		int postOption = Integer.parseInt(postOptionStr);
		if(postOption == 1) {
			//自ユーザー情報変更処理
			//本来ならdoPutにすべきところだが、正常に動作するかどうか未確認なので、ひとまずPOSTメソッドとする
			//余裕があればあとでPutメソッドでの処理に変更


			//自分のユーザー情報を自己紹介、画像URLのみ更新する処理
			String introduction = request.getParameter("introduction");
			String profile_image_url = request.getParameter("profile_image_url");

			boolean putResult = false;
			PutUserLogic bo = new PutUserLogic();

			if(!(introduction == null || introduction.length() == 0) && !(profile_image_url == null || profile_image_url.length() == 0)) {
				//自己紹介・画像urlどちらも変更する場合
				putResult = bo.execute(selfId, 0, introduction, profile_image_url);
			}else if(!(introduction == null || introduction.length() == 0)) {
				//自己紹介文を変更する場合
				putResult = bo.execute(selfId, 1, introduction, profile_image_url);
			}else if(!(profile_image_url == null || profile_image_url.length() == 0)) {
				//画像urlを変更する場合
				putResult = bo.execute(selfId, 2, introduction, profile_image_url);
			}

			if(putResult) {
				System.out.println("更新完了ok");
				response.sendRedirect("/ChatApp/UserServlet?userId=" + selfId);
			}else {
				System.out.println("更新失敗");
				request.setAttribute("updateErrMsg", "更新に失敗しました。");
				response.sendRedirect("/ChatApp/UserServlet?userId=" + selfId);
			}
		}else if(postOption == 2) {
			//チャット未開始ユーザーとのチャット開始処理（chatroomsの作成、users_chatroomsの作成、user_userの作成）
			String userIdStr = request.getParameter("userId");
			int otherId = Integer.parseInt(userIdStr);

			AddOneToOneChatroomLogic bo = new AddOneToOneChatroomLogic();
			boolean addResult = bo.execute(selfId, otherId);

			if(addResult) {
				System.out.println("チャット開始処理完了");
			}else {
				System.out.println("チャット開始処理が完了できませんでした");
			}

			response.sendRedirect("/ChatApp/UserServlet?userId=" + otherId);
		}


	}

}
