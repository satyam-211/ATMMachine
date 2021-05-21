import java.util.ArrayList;
import java.util.List;

public class Bank {
	
	private List<UserAccount> accounts;
	
	Bank(){
		accounts=new ArrayList<>();
	}
	
	public void addAccount(UserAccount account) {
		accounts.add(account);
	}
	
	public void addRawData() {
		UserAccount account1=new UserAccount(1234);
		UserAccount account2=new UserAccount(5678);
		UserAccount account3=new UserAccount(9101112);
		account1.setAccountBalance(1000);
		account2.setAccountBalance(10000);
		account3.setAccountBalance(45778);
		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
	}
	
	public UserAccount verifyPin(int pin) {
		for(UserAccount account:accounts) {
			if(account.getPin()==pin) {
				return account;
			}
		}
		return null;
	}

}
