import java.util.Scanner;

public class Driver {
	
	private static Bank bank;
	private static ATM atm;
	private static Scanner s;

	public static void main(String[] args) {
	   s=new Scanner(System.in);
       atm=new ATM();
       bank=new Bank();
       bank.addRawData();
       System.out.println("Welcome");
       while(true) {
    	   showMenu();
       }
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void showMenu() {
		ATMState currentState=atm.currentState();
		if(currentState.getClass()==Ready.class) {
			System.out.println("Enter your card (Press any key to enter)");
			s.next();
		    currentState.setValue(true);
		}else if(currentState.getClass()==CardInserted.class) {
			UserAccount user=getUser();
			if(user!=null) {
				atm.setUser(user);
			}else {
				System.out.println("Sorry, your account is freezed.");
				atm.reset();
			}
			currentState.setValue(true);
		}else if(currentState.getClass()==Transaction.class) {
			Transactions transaction=getTransaction();
		    currentState.setValue(true);
			atm.setTransaction(transaction);
		}else if(currentState.getClass()==Withdraw.class) {
			try {
				withDraw(atm.getUser());
			} catch (InsufficientBalanceException e) {
				atm.addErrorState("Insufficient Balance");
				return;
			}
			currentState.setValue(true);
		}else if(currentState.getClass()==DispenseCash.class) {
			System.out.println(" Please collect your money (Press any key to collect) ");
			s.next();
			currentState.setValue(true);
		}else if(currentState.getClass()==DisplayBalance.class) {
			System.out.println("Balance : "+atm.getUser().getAccountBalance());
			System.out.println("Press any key");
			s.next();
			currentState.setValue(true);
		}else if(currentState.getClass()==DisplayErrorMessage.class) {
			DisplayErrorMessage state=(DisplayErrorMessage) currentState;
			System.out.println(state.getValue());
		}
		atm.switchToNextState();
	}

	private static void withDraw(UserAccount user) throws InsufficientBalanceException {
		System.out.println("Enter amount to withdraw");
		double amount=s.nextDouble();
		try {
			user.withdraw(amount);
			System.out.println("Amount debited :"+amount);
			System.out.println("Balance left :"+user.getAccountBalance());
		}catch(InsufficientBalanceException e) {
			throw new InsufficientBalanceException();
		}
	}

	private static Transactions getTransaction() {
		System.out.println(" Please select a mode of transaction ");
		String str=" 1 Withdraw \n 2 Balance inquiry \n Press any other key to cancel";
		System.out.println(str);
		String input=s.next();
		try {
			int choice=Integer.valueOf(input);
			switch(choice) {
			case 1:return Transactions.Withdraw;
			case 2:return Transactions.BalanceInquiry;
			default: return null;
			}
		}catch(Exception e) {
			
		}
		return null;
	}

	private static UserAccount getUser() {
		int tries=1;
		while(tries!=4) {
			System.out.println("Enter pin");
			int pin=s.nextInt();
			UserAccount user=bank.verifyPin(pin);
			if(user!=null) {
				return user;
			}else {
				System.out.println("Invalid Pin. "+(3-tries)+" more tries left");
				tries++;
			}
		}
		return null;
	}

	

}
