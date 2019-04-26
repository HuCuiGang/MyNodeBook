
public class Main {
	public static void main(String[] args) {
		//通过运算对象工厂创建对应运算的对象
		Operation operation =OperateFactory.createOperate("+");
		//输入需要运算的值
		operation.setNumberA(20);
		operation.setNumberB(10);
		//过去运算结果
		System.out.println(operation.GetResult());
	}
}
