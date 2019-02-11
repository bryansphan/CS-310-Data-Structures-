public class p4Driver {
	public static void main(String [] args) {
		PhoneBook BP = new PhoneBook(new Integer(10000));
		
		BP.load("p4_data.txt");
		BP.addEntry(new PhoneNumber("408-340-3720"), "Phan, Bryan");
		BP.printNames();
		System.out.println("Inserting new value");
		BP.addEntry(new PhoneNumber("434-132-5543"), "White, Drew");
		BP.printNames();
		System.out.println("Removing Phan, Bryan");
		BP.deleteEntry(new PhoneNumber("408-340-3720"));
		BP.deleteEntry(BP.nameLookup("White, Drew"));
		BP.printNames();
		
	}

}