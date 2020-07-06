package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Chatroom;

public class ChatroomDAO {
	Connection con = null;

	public ChatroomDAO() {
		//生成時にドライバ接続、コネクション取得
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/Chatapp?user=root&password=&serverTimezone=UTC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * データベースへの接続を切断するメソッド
	 */
	public void closeConnect() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * idから該当のchatroomsレコードを一つだけ取得
	 * @param chatroomId
	 * @return
	 */
	public Chatroom findOneChatroom(int chatroomId) {
		String findOneRooIdSql = "select * from chatrooms where id = ?";

		PreparedStatement findStm = null;

		try {
			findStm = con.prepareStatement(findOneRooIdSql);// ステートメントオブジェクトを生成
			findStm.setInt(1, chatroomId);
			ResultSet resultOne = findStm.executeQuery();// クエリーを実行して結果セットを取得

			resultOne.next();

			int roomId = resultOne.getInt("id");
			String group_status = resultOne.getString("group_status");
			String group_name = resultOne.getString("group_name");
			int game_id = resultOne.getInt("game_id");
			int host_user_id = resultOne.getInt("host_user_id");

			Chatroom selectedRoom = new Chatroom(roomId, group_status, group_name, game_id, host_user_id);

			System.out.println("ChatroomDAO：該当のチャットルームデータをリターンします");
			return selectedRoom;

		}catch(Exception e) {
			System.out.println("ChatroomDAOでエラー発生");
			e.printStackTrace();
		}
		System.out.println("findOneChatroom()で異常な動作が行われています。該当箇所を確認してください。");

		return null;
	}

	/**
	 * 引数のuserIdから所属している全てのチャットルームデータをリストで返すメソッド。基本的に自分のidに対して使用する。
	 * @param selfId
	 * @return
	 */
	public List<Chatroom> findAllBelongingRoom(int selfId){
		//自分のidから中間テーブルでjoinして所属している全てのチャットルームの情報を取得する。
		String findAllroomSql = "SELECT chatrooms.id, chatrooms.group_status ,chatrooms.group_name, chatrooms.game_id, chatrooms.host_user_id, users_chatrooms.user_id  FROM Chatrooms join users_chatrooms on chatrooms.id = users_chatrooms.chatroom_id  WHERE users_chatrooms.user_id = ?;";

		PreparedStatement findStm = null;

		try {
			List<Chatroom> chatrooms = new ArrayList<>();
			findStm = con.prepareStatement(findAllroomSql);// ステートメントオブジェクトを生成

			findStm.setInt(1, selfId);
			System.out.println(findStm);

			ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得


			while(resultAll.next()) {
				int roomId = resultAll.getInt("id");
				String group_status = resultAll.getString("group_status");
				String group_name = resultAll.getString("group_name");
				int game_id = resultAll.getInt("game_id");
				int host_user_id = resultAll.getInt("host_user_id");

				Chatroom room = new Chatroom(roomId, group_status, group_name, game_id, host_user_id);

				chatrooms.add(room);
			}

			System.out.println("ChatroomDAO：該当するチャットルームを全てリターンします");
			return chatrooms;

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("異常な動作が行われています。該当箇所を確認してください。");

		return null;
	}

	/**
	 * OneToOneステータスのチャットルームの作成、中間テーブルusers_chatroomsの作成、一対一の関係を保存するuser_userの作成全てを行う
	 * @param selfId
	 * @param otherId
	 * @return
	 */
	public boolean addOneToOneChatroom(int selfId, int otherId) {
		String insertSql = "INSERT INTO chatrooms(group_status,group_name, host_user_id) values('OneToOne', '',?)";
		String findLatestRooIdSql = " select * from chatrooms order by id desc limit 1";
		String users_chatroomSql = "INSERT INTO users_chatrooms(user_id, chatroom_id) values(?, ?)";
		String user_userSql = "INSERT user_user(user_id1, user_id2, chatroom_id) VALUES (?, ?, ?);";

		PreparedStatement findStm = null;
		PreparedStatement insert = null;
		PreparedStatement insert2 = null;
		PreparedStatement insert3 = null;

		try {
			//まずチャットルームデータを追加
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setInt(1, selfId);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("OneToOneの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("OneToOneの追加が完了しました。");
			}

			//作成したばかりのチャットルームのidを取得する
			findStm = con.prepareStatement(findLatestRooIdSql);// ステートメントオブジェクトを生成
			ResultSet resultOne = findStm.executeQuery();// クエリーを実行して結果セットを取得
			resultOne.next();
			int roomId = resultOne.getInt("id");

			//users_chatroomsの追加
			insert2 = con.prepareStatement(users_chatroomSql);// ステートメントオブジェクトを生成

			insert2.setInt(1, selfId);
			insert2.setInt(2, roomId);
			int resultNum2 = insert2.executeUpdate();
			if(resultNum2 == 0) {
				System.out.println("users_chatroomsの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum2 == 1) {
				System.out.println("users_chatroomsの追加が完了しました。");
			}

			insert2.setInt(1, otherId);
			resultNum2 = insert2.executeUpdate();
			if(resultNum2 == 0) {
				System.out.println("users_chatroomsの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum2 == 1) {
				System.out.println("users_chatroomsの追加が完了しました。");
			}

			insert3 = con.prepareStatement(user_userSql);
			insert3.setInt(1, selfId);
			insert3.setInt(2, otherId);
			insert3.setInt(3, roomId);
			int resultNum3 = insert3.executeUpdate();
			if(resultNum3 == 0) {
				System.out.println("user_userの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum3 == 1) {
				System.out.println("user_userの追加が完了しました。");
			}

			return true;

		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * グループチャットルームの追加
	 * @param selfId
	 * @param groupName
	 * @return
	 */
	public boolean addGroupChatroom(int selfId,String groupName) {
		String insertSql = "INSERT INTO chatrooms(group_status, group_name, host_user_id) values('group', ?, ?)";

		PreparedStatement insert = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setString(1, groupName);
			insert.setInt(2, selfId);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("グループデータの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("グループデータの追加が完了しました。");
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("addGroupChatroom()で異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}

	/**
	 * group_statusがcommunityのチャットルームを追加する。また、同時に作成したチャットルームのidと自分のidで中間テーブルのレコードを作成する
	 * @param selfId
	 * @param gameId
	 * @param communityName
	 * @return
	 */
	public boolean addCommunity(int selfId, int gameId, String communityName) {
		String insertSql = "INSERT INTO chatrooms(group_status, group_name, game_id, host_user_id) values('gameComunity', ?, ?, ?)";
		String findLatestRooIdSql = " select * from chatrooms order by id desc limit 1";
		String users_chatroomSql = "INSERT INTO users_chatrooms(user_id, chatroom_id) values(?, ?)";

		PreparedStatement insert = null;
		PreparedStatement findStm = null;
		PreparedStatement insert2 = null;

		try {
			insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

			insert.setString(1, communityName);
			insert.setInt(2, gameId);
			insert.setInt(3, selfId);
			int resultNum = insert.executeUpdate();
			if(resultNum == 0) {
				System.out.println("グループデータの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("グループデータの追加が完了しました。");
			}
			//作成したばかりのチャットルームのidを取得する
			findStm = con.prepareStatement(findLatestRooIdSql);// ステートメントオブジェクトを生成
			ResultSet resultOne = findStm.executeQuery();// クエリーを実行して結果セットを取得
			resultOne.next();
			int roomId = resultOne.getInt("id");

			//users_chatroomsの追加
			insert2 = con.prepareStatement(users_chatroomSql);// ステートメントオブジェクトを生成

			insert2.setInt(1, selfId);
			insert2.setInt(2, roomId);
			int resultNum2 = insert2.executeUpdate();
			if(resultNum2 == 0) {
				System.out.println("users_chatroomsの追加が正しく行われませんでした。");
				return false;
			}
			if(resultNum2 == 1) {
				System.out.println("users_chatroomsの追加が完了しました。");
				return true;
			}


		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("addCommunity()で異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}

	/**
	 * 最新のチャットルーム（idが最も大きく、作成したばかりのもの）を取得する
	 * @return
	 */
	public Chatroom fetchLatestChatroom() {
		//最新のレコードをidで降順にして上から一つに限定することで取得
		String findLatestRooIdSql = " select * from chatrooms order by id desc limit 1";

		PreparedStatement findStm = null;

		try {
			findStm = con.prepareStatement(findLatestRooIdSql);// ステートメントオブジェクトを生成

			ResultSet resultOne = findStm.executeQuery();// クエリーを実行して結果セットを取得


			resultOne.next();

			int roomId = resultOne.getInt("id");
			String group_status = resultOne.getString("group_status");
			String group_name = resultOne.getString("group_name");
			int game_id = resultOne.getInt("game_id");
			int host_user_id = resultOne.getInt("host_user_id");

			Chatroom latestRoom = new Chatroom(roomId, group_status, group_name, game_id, host_user_id);



			System.out.println("ChatroomDAO：最新のチャットルームデータをリターンします");
			return latestRoom;

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("fetchLatestChatroom()で異常な動作が行われています。該当箇所を確認してください。");

		return null;
	}

	/**
	 * 中間テーブルusers_chatroomsに追加をし、ユーザーとチャットルームを紐づける処理
	 * @param user_id
	 * @param thisRoomId
	 * @return
	 */
	public boolean roomIn(List<Integer> checkedUsersIdList,int thisRoomId) {
		for(int user_id : checkedUsersIdList) {
			String insertSql = "INSERT INTO users_chatrooms(user_id, chatroom_id) values(?, ?)";

			PreparedStatement insert = null;

			try {
				insert = con.prepareStatement(insertSql);// ステートメントオブジェクトを生成

				insert.setInt(1, user_id);
				insert.setInt(2, thisRoomId);
				int resultNum = insert.executeUpdate();
				if(resultNum == 0) {
					System.out.println("users_chatroomsの追加が正しく行われませんでした。");
					return false;
				}
				if(resultNum == 1) {
					System.out.println("users_chatroomsの追加が完了しました。");
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("users_chatroomsで全ての追加処理が終了しました。");
		return true;
	}


	/**
	 * group_statusがgameComunityであり、game_idを保持しているものの中で、指定のgame_idを持つものを取得
	 * @param gameId
	 * @return
	 */
	public List<Chatroom> findAllCommunity(int gameId){
        //選んだゲームのidからそのゲームの全コミュニティの情報を取得する。
        String findAllCommunitySql = "SELECT * FROM chatrooms WHERE game_id = ?;";
        PreparedStatement findStm = null;
        List<Chatroom> communities = new ArrayList<>();
        try {
            findStm = con.prepareStatement(findAllCommunitySql);// ステートメントオブジェクトを生成
            findStm.setInt(1, gameId);
            ResultSet resultAll = findStm.executeQuery();// クエリーを実行して結果セットを取得
            while(resultAll.next()) {
                int roomId = resultAll.getInt("id");
                String group_status = resultAll.getString("group_status");
                String group_name = resultAll.getString("group_name");
                int game_id = resultAll.getInt("game_id");
                int host_user_id = resultAll.getInt("host_user_id");
                Chatroom community = new Chatroom(roomId, group_status, group_name, game_id, host_user_id);
                communities.add(community);
            }
            System.out.println("findAllCommunity()：該当するチャットルームを全てリターンします");
            return communities;
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("findAllCommunity()で異常な動作が行われています。該当箇所を確認してください。");
        return null;
    }

	/**
	 * idに該当するチャットルームの削除。group、community共通で使用。
	 * @param room_id
	 * @return
	 */
	public boolean deleteRoom(int room_id) {
		String deleteSql = "DELETE FROM chatrooms WHERE id = ?";

		PreparedStatement deleteStm = null;

		try {
			deleteStm = con.prepareStatement(deleteSql);// ステートメントオブジェクトを生成

			deleteStm.setInt(1, room_id);
			int resultNum = deleteStm.executeUpdate();
			if(resultNum == 0) {
				System.out.println("チャットルームの削除が正しく行われませんでした。");
				return false;
			}
			if(resultNum == 1) {
				System.out.println("チャットルームの削除が完了しました。");
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("deleteRoom()で異常な動作が行われています。該当箇所を確認してください。");
		return false;
	}

}
