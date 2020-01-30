import java.util.Random;

public class Client {
	private String firstName, lastName, iD, address;
	private int age, accountNumber, secretCode;
	private float accountBalance;
	
	
	public Client() {
		
		this.secretCode = getRandomValue(10000);
		this.accountNumber = getRandomValue(10000000);
		
	}
	
	public Client(String firstName, String lastName, String iD, String address, int age, float accountBalance) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.iD = iD;
		this.address = address;
		this.age = age;
		this.accountBalance = accountBalance;
		this.secretCode = getRandomValue(10000);
		this.accountNumber = getRandomValue(10000000);
		
	}
	
	public int getRandomValue(int size) {
		Random rand = new Random();
		int value = rand.nextInt(size / 10 * 9) + (size / 10);
		return value;
	}
	
	public String getInfo() {
		String money = String.format("%6.2f", getAccountBalance());
		
		String info = ("ACCOUNT NUMBER: " + getAccountNumber() +
						"\nNAME: " + getFirstName() + " " + getLastName() + 
						"\nAGE: " + getAge() + "   ID: " + getiD() +
						"\nADDRESS: " + getAddress() + 
						"\nACCOUNT BALANCE: " + money + " $");
				
		return info;
	}

	public void makeDeposit(int value) {
		float balance = getAccountBalance() + value;
		setAccountBalance(balance);
	}
	
	public void makeWithdrawal(int value) {
		float balance = getAccountBalance() - value;
		setAccountBalance(balance);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public int getSecretCode() {
		return secretCode;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	
}
