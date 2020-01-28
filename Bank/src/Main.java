import javax.swing.JOptionPane;



public class Main {
	
	public final static String TITLE = "Bank Maker 1.0";
	
	public static void main(String[] args) {
		
		//Bank bank = openNewBank();
		Bank bank = new Bank("Banco do Brasil","Jorge Joao Saad", true); 
		
		do {
			if (JOptionPane.showConfirmDialog(null, "Hello, would you like to open an account with " + 
							bank.getName() + "?", TITLE, JOptionPane.YES_NO_OPTION) == 0) 
			{
				bank.clientList.add(openNewAccount(bank.getName()));
				//bank.clientList.add(new Client("Name","Last","0230123","Street",34,5000));
				
				
				System.out.println(bank.getName() + " - TOTAL MONEY AVAILABLE: " + bank.getTotalMoney() + "0 $");
				
			}
			
		} while (JOptionPane.showConfirmDialog(null, "Another one?", TITLE ,JOptionPane.YES_NO_OPTION) == 0);
		
		

	}
	
	
	
	
	
	
	
	
	private static Client openNewAccount(String bankName) {
		
		Client client = new Client();
		
		client.setFirstName(JOptionPane.showInputDialog(null, "Ok, great! What is your first name?", TITLE, 3));
		
		client.setLastName(JOptionPane.showInputDialog(null, "And what is your last name?", TITLE, 3));
		
		client.setAge(Integer.parseInt(JOptionPane.showInputDialog(null, "How old are you?", TITLE, 3)));
		
		client.setAddress(JOptionPane.showInputDialog(null, "What is your address?", TITLE, 3));
		
		client.setiD(JOptionPane.showInputDialog(null, "And what is your ID number?", TITLE, 3));
		
		//JOptionPane.showMessageDialog(null, "Now the big question...", TITLE, 3);
		
		client.setAccountBalance(Integer.parseInt
				(JOptionPane.showInputDialog(null, "How much money do you want to give us?", TITLE, 3)));
		
		JOptionPane.showMessageDialog(null, 
				"Alright, now we just need a second to process your information...", TITLE, 1);
		
		JOptionPane.showMessageDialog(null, 
				"Congratulations, " + client.getFirstName() + "!" +
				"\nYou are the newest client of " + bankName +
				"\n" + "\n" + client.getInfo() ,TITLE, 1);
		
		JOptionPane.showMessageDialog(null, "In order to access your account information and perform transactions safely," +
				"\nYou will be given a secret code. The code is for your personal use only!" + 
				"\n" + 
				"\n----------------------------------------------------------------------------------------------------" + 
				"\n                                   ACCOUNT NUMBER : " + client.getAccountNumber() +
				"\n 				             	                              SECRET CODE : " + client.getSecretCode() +
				"\n----------------------------------------------------------------------------------------------------" +
				"\n ","Bank Maker", 2);
		
		return client;
	}








	public static Bank openNewBank() {
		
		Bank bank = new Bank("New Bank", "John Doe", false);
		
		bank.setOwner(JOptionPane.showInputDialog(null, "Let's first create the bank. What is the name of the owner?", TITLE, 3));
		
		bank.setName(JOptionPane.showInputDialog(null, 
				"And what should we call the bank?", TITLE, 3));
		
		if (JOptionPane.showConfirmDialog(null, "Do you want insurance against robbers?", 
				TITLE, JOptionPane.YES_NO_OPTION) == 0) {
			bank.setInsured(true);
		}
		
			
		JOptionPane.showMessageDialog(null, 
				"Alright, we just need a minute to process the paperwork...", 
				TITLE, 0);
		
		JOptionPane.showMessageDialog(null, 
				"Here it is, the bank creation statement: " 
				+ "\n" + bank.getState() ,TITLE, 2);
		
		return bank;
}
}
