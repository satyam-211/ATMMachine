
public class UserAccount {
	
	private double accountBalance;
	private int pin;
	
	UserAccount(int pin){
		accountBalance=0;
		this.pin=pin;
	}
	
	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public void withdraw(double amount) throws InsufficientBalanceException {
		if(amount>accountBalance) {
			throw new InsufficientBalanceException();
		}else {
			accountBalance-=amount;
		}
	}

	public double getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(double accountBalance) {
		this.accountBalance=accountBalance;
	}

}
