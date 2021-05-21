
public abstract class ATMState<T> {

	private T value;

	ATMState(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public abstract void reset();

}

class Ready extends ATMState<Boolean> {

	Ready(Boolean enteredCard) {
		super(enteredCard);
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class CardInserted extends ATMState<Boolean> {

	CardInserted(Boolean enteredPin) {
		super(enteredPin);
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class Transaction extends ATMState<Boolean> {
	private UserAccount user;

	Transaction(Boolean enteredTransaction,UserAccount user) {
		super(enteredTransaction);
		this.user = user;
	}

	public UserAccount getUser() {
		return user;
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class Withdraw extends ATMState<Boolean> {
	private UserAccount user;

	Withdraw(Boolean enteredAmount,UserAccount user) {
		super(enteredAmount);
		this.user = user;
	}

	public UserAccount getUser() {
		return user;
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class DispenseCash extends ATMState<Boolean> {
	private UserAccount user;

	DispenseCash(Boolean collectedMoney,UserAccount user) {
		super(collectedMoney);
		this.user = user;
	}

	public UserAccount getUser() {
		return user;
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class DisplayBalance extends ATMState<Boolean> {
	private UserAccount user;

	DisplayBalance(Boolean selected,UserAccount user) {
		super(selected);
		this.user = user;
	}

	public UserAccount getUser() {
		return user;
	}

	@Override
	public void reset() {
		this.setValue(false);
	}
}

class DisplayErrorMessage extends ATMState<String> {

	DisplayErrorMessage(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public void reset() {
		this.setValue("");
	}
}
