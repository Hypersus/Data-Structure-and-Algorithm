package top.zekehypersus.stack;
import java.util.Stack;
public class Calculator {
    String expression=null;
    public Calculator () {

    }
    public Calculator (String expression) {
        this();
        this.expression=expression;
    }
    /**
     * @param op
     * @return
     * 下列列出的两个算符之间一定要有数字,用于判断算符位置是否合法
     */
    boolean isOperation(char op){
        if (op=='+'||op=='-'||op=='*'||op=='/'||op=='%') {
            return true;
        }
        return false;
    }
    /**
     * @return
     * 判断输入表达式是否合法
     */
    boolean isValid(){
        boolean hasNum=false;
        boolean preDecimal=false;
        boolean negtiveValid=false;
        boolean moreThanNegtive=false;
        int bracket=0;
        int equal=0;
        int equalPosition=0;
        int len = expression.length();
        for (int i = 0; i < len; i++) {
            //判断括号是否匹配
            if (expression.charAt(i)=='(') {
                bracket++;
            }
            if(expression.charAt(i)==')'){
                if (negtiveValid) {
                    if (moreThanNegtive) {
                        return false;
                    }
                    else {
                        negtiveValid=false;
                        moreThanNegtive=false;
                    }
                }
                if (bracket>0) {
                    bracket--;
                }
                else
                    return false;
            }
            if (expression.charAt(i)=='!') {
                //阶乘号在表达式开头
                if (i==0) {
                    return false;
                }
                //阶乘号前必须是数字
                else {
                    if (!isNum(expression.charAt(i-1))) {
                        return false;
                    }
                }
            }
            //判断等号数量,得到最后一个等号的位置
            if (expression.charAt(i)=='=') {
                equal++;
                equalPosition=i;
            }
            //hasNum判断两个运算符之间有没有数字
            if (isNum(expression.charAt(i))) {
                hasNum=true;
            }
            if (expression.charAt(i)=='.') {
                //小数点在第一位非法
                if (i==0) {
                    return false;
                }
                //flag用于判断.前后是否均是数字
                boolean flag=isNum(expression.charAt(i-1))&&isNum(expression.charAt(i+1));
                //若.前后都是数字不成立则说明表达式一定非法
                if (!flag) {
                    return false;
                }
                else {
                    //该小数点前没有别的小数点
                    if (!preDecimal) {
                        preDecimal=true;
                    }
                    //该小数点前已经有别的小数点
                    //两个小数点之间至少有一个算符
                    //为了排除数字.数字.数字的情况
                    else {
                        int j=i-1;
                        boolean valid=false;
                        //从该小数点往前遍历,若无合法算符则返回错误
                        while (expression.charAt(j)!='.') {
                            if (isOperation(expression.charAt(j))) {
                                valid=true;
                            }
                            j--;
                        }
                        if (!valid) {
                            return false;
                        }
                    }
                }
            }
            //通过hasNum判断两个运算符之间是否有数字
            if (isOperation(expression.charAt(i))) {
                if (i!=0&&expression.charAt(i)=='-'&&expression.charAt(i-1)=='(') {
                    if (isNum(expression.charAt(i+1))) {
                        negtiveValid=true;
                        continue;
                    }
                }
                if (negtiveValid) {
                    moreThanNegtive=true;
                }
                if (hasNum) {
                    hasNum=false;
                }
                else {
                    return false;
                }
            }
            //
            if (isOperation(expression.charAt(i))&&expression.charAt(i)!='%') {

            }
        }
        if (bracket==0 && equal==1 && equalPosition==len-1)
            return true;
        else
            return false;
    }
    /**
     * @param op
     * @return
     * 各类算符分配优先级
     */
    int table(char op){
        switch (op) {
            case '+':
            case '-':
                return 12;
            case '*':
            case '/':
            case '%':
                return 14;
            case '!':
                return 15;
            case ';':
            case '=':
            case ')':
                return 1;
        }
        return -1;
    }
    /**
     * @param nChar
     * @return
     * 判断当前char是否是数字
     */
    boolean isNum(char nChar){
        if (nChar>='0'&&nChar<='9') {
            return true;
        }
        return false;
    }
    /**
     * @param point
     * @return
     * 该方法的作用是处理表达式，point指向当前处理的char
     * 遇到的问题：
     * 在方法体中怎么改变实参的值：
     * （1）创建只有一个元素的数组，通过数组的索引按地址修改
     *
     */
    double operate(Integer[] point){
        double temp,val1,val2;
        char opin,op;
        boolean noBreak=false;
        Stack nStack=new Stack();//算子栈
        Stack oStack=new Stack();//算符栈
        oStack.push(';');//算符栈中先放置一个最低优先级的算符
        //表达式以'='结尾
        while (expression.charAt(point[0])!='='){
            char nChar=expression.charAt(point[0]);
            //遇到括号就迭代,优先计算括号内的内容
            if (nChar=='(') {
                point[0]++;
                //若扫描到负数则不迭代
                if (expression.charAt(point[0])=='-') {
                    point[0]++;
                    noBreak=true;
                    continue;
                }
                temp=operate(point);
                nStack.push(temp);
            }
            else if(nChar==')'){
                point[0]++;
                if (!noBreak) {
                    break;
                }
                noBreak=false;
            }
            else {
                //遇到数字或括号内负数则入栈
                if(isNum(nChar)){
                    double negtive=1;
                    if (point[0]!=0&&expression.charAt(point[0]-1)=='-') {
                        negtive=-1;
                    }
                    temp=0;
                    do{
                        temp=temp*10+(nChar-'0');
                        point[0]++;
                        nChar=expression.charAt(point[0]);
                    }while(isNum(nChar));
                    if (nChar=='.') {
                        point[0]++;
                        nChar=expression.charAt(point[0]);
                        double decimal=0;
                        int count=0;
                        //这里能否优化？---------------------------
                        do {
                            decimal=decimal*10+nChar-'0';
                            point[0]++;
                            count++;
                            nChar=expression.charAt(point[0]);
                        } while (isNum(nChar));
                        for (int i = 0; i < count; i++) {
                            decimal=decimal*0.1;
                        }
                        //---------------------------------
                        temp=temp+decimal;
                        temp=temp*negtive;
                    }
                    nStack.push(temp);
                }
                //说明当前所指表达式位置上的元素是运算符
                //opin指向算符栈中已经有的算符
                else {
                    op=nChar;
                    opin=(char)oStack.pop();
                    //栈外算符优先级大于栈内算符则入栈,优先级高的置于栈顶
                    if(table(op)>table(opin)){
                        //阶乘最优先计算
                        if (op=='!') {
                            val1=(double)nStack.pop();
                            temp=Calculate(val1,op);
                            nStack.push(temp);
                            oStack.push(opin);
                            point[0]++;
                        }
                        else {
                            point[0]++;
                            oStack.push(opin);
                            oStack.push(op);
                        }
                    }
                    //栈外算符优先级低于栈内算符
                    //弹出栈内算符和两个算子进行计算
                    //计算结果压回算子栈
                    //原栈内算符丢弃,压入原栈外算符
                    else {
                        val2=(double)nStack.pop();
                        val1=(double)nStack.pop();
                        temp=Calculate(val1, val2, opin);
                        nStack.push(temp);
                        oStack.push(op);
                        point[0]++;
                    }
                }
            }
        }
        //扫描结束后
        //从算子栈和算符栈分别弹出算子算符计算得到最终结果
        opin=(char)oStack.pop();
        do{
            val2=(double)nStack.pop();
            val1=(double)nStack.pop();
            temp=Calculate(val1, val2, opin);
            nStack.push(temp);
            opin=(char)oStack.pop();
        }while(opin!=';');
        temp=(double)nStack.pop();
        return temp;
    }

    /**
     * @return
     * 计算表达式的入口
     * 先判断格式是否正确
     * 正确则执行计算
     */
    double operate(){
        if (!isValid()) {
            System.out.println("输入格式错误");
            return -1;
        }
        Integer[] point = new Integer[1];
        point[0]=0;
        return operate(point);
    }

    /**
     * @param val1
     * @param val2
     * @param op
     * @return
     * 该方法用于双目运算符的计算
     */
    double Calculate(double val1,double val2,char op){
        switch (op) {
            case '+':
                return val1+val2;
            case '-':
                return val1-val2;
            case '*':
                return val1*val2;
            case '/':
                return val1/val2;
            case '%':
                int int1=(int)val1;
                int int2=(int)val2;
                return (double) (int1%int2);
        }
        return -1;
    }

    /**
     * @param val1
     * @param op
     * @return
     * 该方法用于单目运算符的计算
     * 这里仅用于计算阶乘
     */
    double Calculate(double val1,char op){
        if (op=='!') {
            int temp=1;
            for ( int i = (int) val1; i >0 ; i--) {
                temp*=i;
            }
            return (double) temp;
        }
        else {
            System.out.println("错误");
            return -1;
        }

    }
}
