import javax.swing.JOptionPane;

public class EmailChecker {

	public static void main(String[] args) {

		do {
			String email = JOptionPane.showInputDialog("Write your e-mail address");
			
			if (isEmailValid(email)) {
				JOptionPane.showMessageDialog(null, "That is a valid e-mail address");
			} else {
				JOptionPane.showMessageDialog(null, "That is not a valid e-mail address");
			}
		} while (JOptionPane.showConfirmDialog(null, "Another one?", "EmailChecker", JOptionPane.YES_NO_OPTION) == 0);
	}

	private static boolean isEmailValid(String email) {
		
		//this is a quick check to make sure it's even worth breaking down the email
		if (email.length() < 3) {
			return false;
		}
		
		String userName = "";
		int strudelPos = 0;
		String serverName = "";
		int dotPos = 0;
		String extraName = ""; 
		
		//now we will just break down the string we received into parts
		for (int i = 0; i < email.length(); i++) {
			char character = email.charAt(i);
			if (character == '@') {
				//we can also return false immediately if there is more than one strudel @
				if (strudelPos > 0) {
					return false;
				}
				strudelPos = i;
				//we have to continue here to make sure this character doesn't get added to the other Strings
				continue;
			} else if (character == '.') {
				dotPos = i;
				continue;
			}
			
			if (strudelPos == 0) {
				userName = userName + character;
			} else if (dotPos == 0) {
				serverName = serverName + character;
			} else {
				extraName = extraName + character;
			}
			
		}
		//and here is the main check
		//if there was one strudel and at least one point after the strudel, and if the email ends with one of these,
		//then we say it's true. 
		//future implementation will add a better extraName check that will be more inclusive
		if (strudelPos > 0 && dotPos > strudelPos && (extraName.equals("com") || extraName.equals("gov") || 
					 extraName.equals("org") || extraName.equals("coil") || extraName.equals("combr"))) {
			return true;
		}
				
		return false;
	}

}
