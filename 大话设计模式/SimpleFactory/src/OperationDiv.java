
public class OperationDiv extends Operation {

	@Override
	public double GetResult() {
		// TODO Auto-generated method stub
		if(numberB==0) {
			throw new RuntimeException("除数不能为0");
		}
		return numberA/numberB;
	}

}
