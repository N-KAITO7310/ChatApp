package security;

import java.util.List;

import dao.NGWordDAO;

public class NGWordChecker {
	public boolean execute(String str) {
		NGWordDAO dao = new NGWordDAO();
		List<String> ngWords = dao.findAllNGWords();
		//select * from ngwords;
		for(String ngWord : ngWords) {
			if(str.contains(ngWord)) {
				return false;
			}
		}
		return true;
	}
}
