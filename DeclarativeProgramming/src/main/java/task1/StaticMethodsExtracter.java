package task1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticMethodsExtracter {
	public void extract(String code) {
		Pattern p = Pattern.compile("static(\\s+\\b\\w+\\b)+\\s+\\b(\\w+)\\b\\s*\\([^\\)]*\\)\\s*\\n");
		Matcher m = p.matcher(code);
		while (m.find()) {
			System.out.println(m.group(2));
		}
	}

	public static void main(String[] args) {
		StaticMethodsExtracter p = new StaticMethodsExtracter();
		p.extract("public static void final function(int a b c d)\n "
				+ "int main abc static field in my static void main()\n void maina()\n"
				+ "public          static             int            myFunction       (int a)            \n"
				+ "static final int ID=5\n"
				+ "public static Dummy getter()\n"
				+ "{"
				+ "}"
				+ "private static dummy get(){\n"
				+ "static int test(int aaaa)\n"
				+ "public static int test2() { return someMethod(12) \n + otherMetohd(44); }");
		
	}
}
