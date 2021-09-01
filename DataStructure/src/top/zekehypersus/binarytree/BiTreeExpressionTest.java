package top.zekehypersus.binarytree;


public class BiTreeExpressionTest {
	public static void main(String[] args) {
		BiTreeExpression.calculate("4*2+(8-8)/(2+3)");
		BiTreeExpression.calculate("!4*2+(8-8)/(2+3)=");
		BiTreeExpression.calculate(".4*2+(8-8)/(2+3)=");
		BiTreeExpression.calculate("4*2.+.(8-8)/(2+3)=");
		BiTreeExpression.calculate("4.5.5*2+(8+(6+4)*8)/(4+40)=");
		BiTreeExpression.calculate("4.5.5*2+(8+/(6+4)*8)/(4+40)=");
		BiTreeExpression.calculate("+5+6*7=");
		System.out.println("--------------------------");
		BiTreeExpression.calculate("4*2+(8+(6+4)*8)/(4+40)=");
		BiTreeExpression.calculate("4.5*2+(8+(6+4)*8)/(4+40)=");
		BiTreeExpression.calculate("4.55*2+(8+(6+4)*8)/(4+40)=");
		BiTreeExpression.calculate("6/(4-2)+3*2=");
		//取余运算测试
		BiTreeExpression.calculate("5%2+(8-8)/(2+3)=");
		BiTreeExpression.calculate("5%2+(8=-8)/(2+3)=");
		//阶乘测试
		BiTreeExpression.calculate("3+5!+(8-8)/(2+3)=");
		BiTreeExpression.calculate("(-1.5)*7=");
		BiTreeExpression.calculate("-1.5*7=");
		BiTreeExpression.calculate("(-1.5+7)*7=");
		BiTreeExpression.calculate("(1.5-)*7=");
		BiTreeExpression.calculate("((-1.5)+7)*7=");
		System.out.println("-------------------------------");
		BiTreeExpression.calculate("tan(1.5)+25=");
		BiTreeExpression.calculate("tan(3.39)+25=");
		BiTreeExpression.calculate("tsin(1.5a)+25=");
		BiTreeExpression.calculate("sin(5.5)/25=");
		BiTreeExpression.calculate("25/sin(2.5)=");
		System.out.println("-------------------------------------");
		BiTreeExpression.calculate("sin(5.5)/cos(5.5)=");
		BiTreeExpression.calculate("tan(5.5)=");
		BiTreeExpression.calculate("7/(2-1)+6*5=");
		BiTreeExpression.calculate("7/(-67)+6*5=");
		BiTreeExpression.calculate("45.55*2+(8+(6+4)*8)/(4+40)=");
		BiTreeExpression aBiTreeExpression =new BiTreeExpression();
		aBiTreeExpression.Calculate("45.55*2+(8+(6+4)*8)/(4+40)=");
		aBiTreeExpression.inorderTraverse();
	}
}
