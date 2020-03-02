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
		if (email.length() < 3) {
			return false;
		}
		
		String userName = "";
		int strudelPos = 0;
		String serverName = "";
		int dotPos = 0;
		String extraName = ""; 
		
		for (int i = 0; i < email.length(); i++) {
			char character = email.charAt(i);
			if (character == '@') {
				if (strudelPos > 0) {
					return false;
				}
				strudelPos = i;
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
		if (strudelPos > 0 && dotPos > strudelPos && (extraName.equals("com") || extraName.equals("gov") || extraName.equals("org") 
													|| extraName.equals("coil") || extraName.equals("combr"))) {
			return true;
		}
				
		return false;
	}

}
