
class Initialise {
	public static void main(String [ ] args)
	{
		DBConnect();
		createMenu();
	}

	public static void createMenu() {
		System.out.println("*************** Course Management System ***************\n");
		System.out.println("1.   Login");
		System.out.println("3.   Exit\n");
		System.out.println("**********************************************************");
	}
}
