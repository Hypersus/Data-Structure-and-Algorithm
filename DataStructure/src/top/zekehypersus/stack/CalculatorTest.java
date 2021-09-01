package top.zekehypersus.stack;

public class CalculatorTest {
    public static void main(String[] args) {
        //小数运算测试
        Calculator calculator = new Calculator("4*2+(8+(6+4)*8)/(4+40)=");
        Calculator calculator2 = new Calculator("4.5*2+(8+(6+4)*8)/(4+40)=");
        Calculator calculator3 = new Calculator("4.55*2+(8+(6+4)*8)/(4+40)=");
        Calculator calculator4 = new Calculator("6/(4-2)+3*2=");
        //取余运算测试
        Calculator calculator5 = new Calculator("5%2+(8-8)/(2+3)=");
        //阶乘测试
        Calculator calculator6 = new Calculator("3+5!+(8-8)/(2+3)=");
        //isValid测试
        Calculator calculator7 = new Calculator("4*2+(8-8)/(2+3)");
        Calculator calculator8 = new Calculator("!4*2+(8-8)/(2+3)=");
        Calculator calculator9 = new Calculator(".4*2+(8-8)/(2+3)=");
        Calculator calculator10 = new Calculator("4*2.+.(8-8)/(2+3)=");
        Calculator calculator11 = new Calculator("4.5.5*2+(8+(6+4)*8)/(4+40)=");
        Calculator calculator12 = new Calculator("4.5.5*2+(8+/(6+4)*8)/(4+40)=");
        Calculator calculator13 = new Calculator("+5+6*7=");
        //测试负数运算
        Calculator calculator14 = new Calculator("(-1.5)*7=");
        Calculator calculator15 = new Calculator("-1.5*7=");
        Calculator calculator16 = new Calculator("(-1.5+7)*7=");
        Calculator calculator17 = new Calculator("((-1.5)+7)*7=");
        System.out.println(calculator.expression+calculator.operate());
        System.out.println(calculator2.expression+calculator2.operate());
        System.out.println(calculator3.expression+calculator3.operate());
        System.out.println(calculator4.expression+calculator4.operate());
        System.out.println(calculator5.expression+calculator5.operate());
        System.out.println(calculator6.expression+calculator6.operate());
        System.out.println(calculator7.expression+calculator7.operate());
        System.out.println(calculator8.expression+calculator8.operate());
        System.out.println(calculator9.expression+calculator9.operate());
        System.out.println(calculator10.expression+calculator10.operate());
        System.out.println(calculator11.expression+calculator11.operate());
        System.out.println(calculator12.expression+calculator12.operate());
        System.out.println(calculator13.expression+calculator13.operate());
        System.out.println(calculator14.expression+calculator14.operate());
        System.out.println(calculator15.expression+calculator15.operate());
        System.out.println(calculator16.expression+calculator16.operate());
        System.out.println(calculator17.expression+calculator17.operate());
    }
}
