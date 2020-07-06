package security;

public class XSSPrevention {
	public  String escapeHTML(String val) {
		   val = val.replaceAll("&", "&amp;");
		   val = val.replaceAll("<", "&lt;");
		   val = val.replaceAll(">", "&gt;");
		   val = val.replaceAll("\"", "&quot;");
		   val = val.replaceAll("'", "&apos;");
		   return val;
		 }
}
