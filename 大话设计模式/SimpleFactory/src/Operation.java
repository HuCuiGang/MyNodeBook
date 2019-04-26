/**
 * 所有运算对象的父类
 * @author hcg
 *
 */
public abstract class Operation {
	public double numberA;
	public double numberB;
	
	public abstract double GetResult();

	public double getNumberA() {
		return numberA;
	}

	public void setNumberA(double numberA) {
		this.numberA = numberA;
	}

	public double getNumberB() {
		return numberB;
	}

	public void setNumberB(double numberB) {
		this.numberB = numberB;
	}
	
	
}
