package model;

import java.util.List;

import dao.WasReadDAO;
/**
 * チャットidとグループメンバーのuser_idを紐づける処理を行うBO。第２引数で複数人のidを受け取る。
 * @author kaitonakamura
 *
 */
public class AddAlready_readsLogic {
	public boolean execute(int latestChatId, List<Integer> groupUserIds) {

		WasReadDAO dao = new WasReadDAO();
		boolean addResult = dao.addWasReadRelation(latestChatId, groupUserIds);
		dao.closeConnect();

		if(!addResult) {
			System.out.println("AddAllready_readsLogicで正しく追加できませんでした。");
		}

		return addResult;
	}
}
