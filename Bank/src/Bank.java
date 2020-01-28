import java.util.ArrayList;
import java.util.List;

public class Bank {
	private String name, owner;
	private double totalMoney;
	private boolean isInsured;
	List <Client> clientList = new ArrayList<Client>();
	
	public Bank(String name, String owner, boolean isInsured) {
		this.name = name;
		this.owner = owner;
		this.isInsured = isInsured;
		this.clientList = new ArrayList<Client>();
		this.totalMoney = 0;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getTotalMoney() {
		return updateTotalMoney();
	}

	public double updateTotalMoney() {
		
		this.totalMoney = 0;
		
		for (int i = 0; i < this.getClientList().size(); i++) 
		{
			this.totalMoney += this.clientList.get(i).getAccountBalance();
		}
		return totalMoney;
	}

	public boolean isInsured() {
		return isInsured;
	}

	public void setInsured(boolean isInsured) {
		this.isInsured = isInsured;
	}
	
	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}


		


	public String getState() {
		
		String not = "";
		
		if (this.isInsured == false) {
			not = "not ";
		}
		
		String safe = String.format("%10.2f", this.getTotalMoney());
		
		String state = ("\n" + this.getName() + "\nOwned by "
				+ this.getOwner() + "\nThe bank is " + not + "insured"
				+ "\nThere is currently " + safe
				+ " $ available in the safe" + "\n");
				
		return state;
	}


	
}
