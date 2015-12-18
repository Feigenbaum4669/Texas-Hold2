

public class TellMeMyWorkingDirectory {
	public static void main(String[] args) {
		System.out.println(new java.io.File("").getAbsolutePath());
		System.out.println(TellMeMyWorkingDirectory.class
				.getClassLoader().getResource("").getPath());
	}
}
