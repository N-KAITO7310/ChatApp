package security;

public class CheckFormString {
	public boolean execute(String...Strings) {
		for(String str : Strings) {
			//一つずつチェック
			if ((str == null) || (str.equals("")) || (str.length() == 0)) {
				return false;
			}
		} return true;
	}
}
