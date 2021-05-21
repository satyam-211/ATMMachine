import java.util.Stack;

enum Transactions{
	Withdraw,BalanceInquiry
}

public class ATM {
	
	@SuppressWarnings("rawtypes")
	private Stack<ATMState> atmStates;
	private UserAccount user;
	private Transactions transaction;
	
	ATM(){
		atmStates=new Stack<>();
		atmStates.add(new Ready(false));
	}
	
	public void setUser(UserAccount user) {
		this.user=user;
	}
	
	public UserAccount getUser() {
		return user;
	}
	
	public void setTransaction(Transactions transaction) {
		this.transaction=transaction;
	}
	
	public Transactions getTransaction() {
		return transaction;
	}
	
	@SuppressWarnings("unchecked")
	public void switchToNextState() {
	  ATMState<Boolean> currState=currentState();
	  if(currState.getClass()==Ready.class&&currState.getValue()) {
		  atmStates.add(new CardInserted(false));
	  }else if(currState.getClass()==CardInserted.class&&currState.getValue()) {
		  if(user!=null)
		  atmStates.add(new Transaction(false,user));
	  }else if(currState.getClass()==Transaction.class&&currState.getValue()) {
		  if(user!=null) {
			  if(transaction==Transactions.Withdraw) {
				  atmStates.add(new Withdraw(false,user));
			  }else if(transaction==Transactions.BalanceInquiry) {
				  atmStates.add(new DisplayBalance(false,user));
			  }else {
				  addErrorState("Transaction cancelled");
			  } 
		  }	 
	  }else if(currState.getClass()==Withdraw.class&&currState.getValue()) {
		  if(user!=null)
		  atmStates.add(new DispenseCash(false,user));
	  }else if(currState.getClass()==DispenseCash.class&&currState.getValue()) {
		  reset();
	  }else if(currState.getClass()==DisplayBalance.class) {
		  switchToLastState();
	  }
	  else if(atmStates.peek().getClass()==DisplayErrorMessage.class){
		  switchToLastState();
		  switchToLastState();
	  }
	}
	
	@SuppressWarnings("unchecked")
	public void switchToLastState() {
		ATMState<Boolean> currState=currentState();
		if(currState.getClass()==Ready.class) {
			currState.setValue(false);
		}else {
			atmStates.pop();
			currState=currentState();
			currState.setValue(false);
		}
	}
	
	public void addErrorState(String errorMessage) {
		atmStates.add(new DisplayErrorMessage(errorMessage));
	}
	
	public void reset() {
		atmStates.clear();
		atmStates.add(new Ready(false));
		user=null;
		transaction=null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public ATMState currentState() {
		return atmStates.peek();
	}
}
