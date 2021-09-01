package top.zekehypersus.binarytree;

import java.util.Stack;

import javax.print.attribute.standard.NumberOfDocuments;

import top.zekehypersus.binarytree.MyTree.TreeNode;

/**------------- ----------------**/
/**
 * BiTreeExpression提供静态方法BiTreeExpression.calculate(String)直接计算表达式
 * 非静态方法Calculate内调用insert()对BiTreeExpression的一个实例内的expressionTree执行插入操作构建表达式树
 * 非静态方法inorderTraverse()对非空表达式树进行前序遍历并带括号输出中缀表达式
 */
public class BiTreeExpression {
	MyTree<Integer, Object> expressionTree = new MyTree<>();
	/**-------------自定义异常----------------**/
	static class notEmptyException extends Exception{

		public notEmptyException() {
			// TODO Auto-generated constructor stub
		}
		public notEmptyException(String msg) {
			System.out.println(msg);
		}
	}
	static class notValidException extends Exception{
		public notValidException() {
			// TODO Auto-generated constructor stub
		}
		public notValidException(String msg) {
			System.out.println(msg);
		}
	}
	static class dividedByZero extends Exception{
		public dividedByZero() {
			
		}
		public dividedByZero(String msg) {
			System.out.println(msg);
		}
	}
	
	/**
	 * @param eString
	 * 静态方法计算表达式,直接给出结果
	 */
	public static void calculate(String eString){
		BiTreeExpression expression = new BiTreeExpression();
		try {
			expression.insert(eString);
			System.out.println(expression.innerCalculate());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * @param eString
	 * 非静态方法计算表达式
	 */
	public void Calculate(String eString){
		if (expressionTree.isEmpty()) {
			try {
				this.insert(eString);
				System.out.println(this.innerCalculate());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {
			System.out.println(this.innerCalculate());
		}
	}
	/**
	 * @throws notEmptyException
	 * @throws notValidException
	 * insert()方法对调用该方法的BiTreeExpression执行插入操作,抛出异常notEmptyException,notValidException
	 */
	private boolean insert(String eString) throws notEmptyException,notValidException{
		String opString;
		////对非空树进行插入操作则抛出notEmptyException
		if (expressionTree.isEmpty()) {
			//表达式不合法抛出notValidException
			if (!isValid(eString)) {
				throw new notValidException("Given expression is not Valid, insert failed");
			}
			//表达式合法才执行插入操作,调用changeString对表达式中的合法函数先进行数值计算
			opString=changeString(eString);
			System.out.println(opString);
			//得到后缀表达式,小数和两位数以上的数两边用空格标记,以便后续计算
			opString=toPostFix(opString);
			//for循环控制插入操作
			boolean flag=false;
			boolean doDecimal=false;
			double num=0;
			double decimalTemp=1;
			//标志位表示此时num中的数字已经是想要的数字
			boolean numOK=false;
			Stack<MyTree<Integer, Object>> treeStack=new Stack<>();
			for (int i = 0; i < opString.length(); i++) {
				if (numOK) {
					MyTree<Integer, Object> aTree=new MyTree<>();
					aTree.init(num);
					treeStack.push(aTree);
					numOK=false;
					num=0;
				}
				if (opString.charAt(i)==' ') {
					if (!flag) {
						flag=true;
					}
					else {
						flag=false;
						decimalTemp=1;
						doDecimal=false;
						numOK=true;
					}
				}
				else if(opString.charAt(i)=='.'){
					doDecimal=true;
				}
				else if (isNum(opString.charAt(i))) {
					//flag为True表示接下来处理小数或者两位及以上的整数
					if (flag) {
						if (doDecimal) {
							decimalTemp*=0.1;
							num+=decimalTemp*(int)(opString.charAt(i)-'0');
						}
						else {
							num*=10;
							num+=(int)(opString.charAt(i)-'0');
						}
					}
					else {
						num=(int)(opString.charAt(i)-'0');
						numOK=true;
					}
				}
				else {
					if (isOperation(opString.charAt(i))){
						//单目运算符
						if (opString.charAt(i)=='!') {
							MyTree<Integer, Object> aTree=new MyTree<>();
							aTree.init(opString.charAt(i));
							aTree.insert(aTree.getRoot(), treeStack.pop().getRoot().value, true);
							treeStack.push(aTree);
						}
						//双目运算符
						else {
							MyTree<Integer, Object> aTree=new MyTree<>();
							aTree.init(opString.charAt(i));
							aTree.insert(aTree.getRoot(), treeStack.pop().getRoot(), false);
							aTree.insert(aTree.getRoot(), treeStack.pop().getRoot(), true);
							treeStack.push(aTree);
						}
					}
				}
			}
			expressionTree=treeStack.pop();
			return true;
		}
		//插入时二叉树不为空则抛出notEmptyException
		throw new notEmptyException("Given BiTree is not empty, insert failed");
	}
	
	/**
	 * 前序遍历打印表达式,其中函数部分已经转换为具体的数值
	 */
	public void inorderTraverse() {
		if (expressionTree.isEmpty()) {
			System.out.println("����!");
		}
		else {
			//从根节点开始遍历
			this.inorderTraverse(expressionTree.getRoot(),false,false);
		}
	}
	/**
	 * @param node
	 * @param left
	 * @param right
	 * @return
	 * left和right用于沟通以node为根节点的子树前序遍历打印时是否需要在两边加上括号
	 * 当node和node.left或node.right均为操作符时
	 * 通过比较node的icp和node.left或node.right的isp确定是否有括号
	 */
	private boolean inorderTraverse(TreeNode node,boolean left,boolean right){
		boolean leftFlag=false;
		boolean rightFlag=false;
		if (node!=null) {
			if (node.left!=null) {
				boolean leftIsOp=!(node.left.value instanceof Double);
				if (leftIsOp) {
					if (icp((char)node.value)>isp((char)node.left.value)) {
						leftFlag=true;
						rightFlag=true;
					}
				}
			}
			if (left) {
				System.out.print('(');
			}
			if (inorderTraverse(node.left,leftFlag,rightFlag)) {
				System.out.print(node.value);
				if (node.right!=null) {
					boolean rightIsOp=!(node.right.value instanceof Double);
					if (rightIsOp) {
						if (icp((char)node.value)>isp((char)node.right.value)) {
							leftFlag=true;
							rightFlag=true;
						}
					}
					else {
						leftFlag=false;
						rightFlag=false;
					}
				}
				if (inorderTraverse(node.right,leftFlag,rightFlag)) {
					if (right) {
						System.out.print(')');
					}
					return true;
				}
				return false;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * @return 
	 * 从根结点开始计算表达式
	 */
	private double innerCalculate() {
		double[] rst=new double[5];
		try {
			innerCalculate(expressionTree.getRoot(),rst);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rst[0];
	}
	
	/**
	 * @throws dividedByZero
	 * 递归计算表达式
	 */
	private boolean innerCalculate(TreeNode node, double[] rst) throws dividedByZero{
		double[] left={0};
		double[] right={0};
		if (node==null) {
			rst[0]=0;
			return true;
		}
		if (node.left==null&&node.right==null) {
			rst[0]=(double) node.value;
			return true;
		}
		else {
			if (innerCalculate(node.left, left)) {
				if (innerCalculate(node.right, right)) {
					switch ((char)node.value) {
					case '+':
						rst[0]=left[0]+right[0];
						break;
					case '-':
						rst[0]=left[0]-right[0];
						break;
					case '*':
						rst[0]=left[0]*right[0];
						break;
					case '/':
						if (right[0]==0) {
							throw new dividedByZero("Divided by zero");
						}
						else {
							rst[0]=left[0]/right[0];
							break;
						}
					case '^':
						rst[0]=Math.pow(left[0], right[0]);
						break;
					case '%':
						rst[0]=(double)(int)left[0]%(int)right[0];
						break;
					case '!':
						rst[0]=(double)factorial((int)left[0]);
						break;
					default:
						return false;
					}
				}
				return true;
			}
			return false;
		}
		
	}
	
	/**
	 * @return
	 * 阶乘计算函数
	 */
	private int factorial(int num) {
		int res=1;
		for(int i=1; i<=num; i++){
			res*=i;
		}
		return res;
	}
	
	/**
	 * @return
	 * 表达式合法性校验
	 */
	private static boolean isValid(String eString) {
		StringBuilder sBuilder=new StringBuilder(eString);
		boolean hasNum=false;
		boolean preDecimal=false;
		boolean negativeValid=false;
		boolean moreThanNegative=false;
		boolean funFlag=false;
		int bracket=0;
		int equal=0;
		int len = sBuilder.length();
		StringBuilder temp=null;
		for (int i = 0; i < len; i++) {
			//判断括号是否匹配
			if (sBuilder.charAt(i)=='(') {
				bracket++;
				continue;
			}
			//funFlag为true时说明此时在函数名内
			if (funFlag) {
				if (sBuilder.charAt(i+1)==')') {
					if (!isNum(sBuilder.charAt(i))) {
						return false;
					}
					funFlag=false;
				}
				if (!isNum(sBuilder.charAt(i))&&sBuilder.charAt(i)!='.') {
					return false;
				}
			}
			if(sBuilder.charAt(i)==')'){
				if (negativeValid) {
					if (moreThanNegative) {
						return false;						
					}
					else {
						negativeValid=false;
						moreThanNegative=false;
					}
				}
				if (bracket>0) {
					bracket--;
					continue;
				}
				else 
					return false;
			}
			if (sBuilder.charAt(i)=='!') {
				//阶乘号在表达式开头
				if (i==0) {
					return false;
				}
				//阶乘号前必须是数字
				else {
					if (!isNum(sBuilder.charAt(i-1))) {
						return false;
					}
					//阶乘号前的操作数是小数则返回false
					if (preDecimal) {
						return false;
					}
				}
				continue;
			}
			//等号若不在表达式末尾则不合法
			if (sBuilder.charAt(i)=='=') {
				equal++;
				if (i!=len-1) {
					return false;
				}
			}
			//hasNum判断两个运算符之间有没有数字
			if (isNum(sBuilder.charAt(i))) {
				hasNum=true;
				continue;
			}
			if (sBuilder.charAt(i)=='.') {
				//小数点在第一位非法
				if (i==0) {
					return false;
				}
				//flag用于判断.前后是否均是数字
				boolean flag=isNum(sBuilder.charAt(i-1))&&isNum(sBuilder.charAt(i+1));
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
						while (sBuilder.charAt(j)!='.') {
							if (isOperation(sBuilder.charAt(j))) {
								valid=true;
							}
							j--;
						}
						if (!valid) {
							return false;
						}
					}
				}
				continue;
			}
			//通过hasNum判断两个运算符之间是否有数字
			//isOperation中其实包含了'('和')'但是因为在前面对'('和')'已经单独拎出来判断了
			//并且那两个判断里都包含了continue或者return
			//因此这里的isOperation若成立则只能是除括号外的其他操作符
			if (isOperation(sBuilder.charAt(i))) {
				//单独判断(-num)
				if (i!=0&&sBuilder.charAt(i)=='-'&&sBuilder.charAt(i-1)=='(') {
					if (isNum(sBuilder.charAt(i+1))) {
						negativeValid=true;
						continue;
					} 
				}
				//
				if (negativeValid) {
					moreThanNegative=true;
				}
				if (hasNum) {
					hasNum=false;
				}
				else {
					return false;
				}
				continue;
			}
			if(isLetter(sBuilder.charAt(i))){
				//字母串的开头
				if (temp==null) {
					temp=new StringBuilder();
					temp.append(sBuilder.charAt(i));
				}
				//扫描到的字母是字母串中最后一个时
				else if (!isLetter((sBuilder.charAt(i+1)))) {
					temp.append(sBuilder.charAt(i));
					//至此拿到了连续的字母串中所有字母,执行判断是否是合法的函数名
					if (validFun(temp)) {
						//函数名合法则检查接下来的格式是否合法
						if (sBuilder.charAt(i+1)!='(') {
							return false;
						}
						//最后一个字母后面跟着的是左括号则立标志位
						//funFlag只在合法函数名的括号内置为true
						funFlag=true;
						temp=null;
					}
					//函数名不合法直接返回false
					else {
						return false;
					}
				}
				//扫描的当前字母的下一个char还是字母时
				else {
					temp.append(sBuilder.charAt(i));
				}
			}
		}
		//括号匹配
		return bracket == 0 && equal == 1;
	}
	private static boolean isNum(char nChar) {
		return nChar >= '0' && nChar <= '9';
	}
	private static boolean isOperation(char op) {
		//这里有没有啥简便的写法啊?
		return op == '+' || op == '-' || op == '*' || op == '/' || op == '%' || op == '^' || op == '!' || op == '(' || op == ')';
	}
	private static boolean isLetter(char nChar) {
		return nChar >= 'a' && nChar <= 'z';
	}
	/**
	 * @return
	 * 判断的字符串是否是合法函数名,仅支持exp,sin,cos,tan,ctan
	 * 比较两个字符串是否相等时要用equals不能用==
	 */
	private static boolean validFun(StringBuilder sBuilder) {
		String temp1=sBuilder.toString();
		return temp1.equals("exp") || temp1.equals("sin") || temp1.equals("cos") || temp1.equals("tan") || temp1.equals("ctan");
	}
	/**
	 * @return
	 * 若eString中有合法函数则将该合法函数对应的字符串替换成对应的函数值
	 */
	private static String changeString(String eString) {
		boolean flag=false;
		int point=0;
		//builder用于存放修改后的string值
		StringBuilder builder=new StringBuilder();
		for(int i=0; i<eString.length();){
			//以下的if块中的内容为将函数表达式替换为对应的函数值，i在if块中被修改
			if (isLetter(eString.charAt(i))) {
				double temp=0;
				//记住函数第一个字母出现的位置
				point=i;
				flag=true;
				StringBuilder sBuilder=new StringBuilder();
				while (eString.charAt(i)!='(') {
					sBuilder.append(eString.charAt(i));
					i++;
				}
				//至此获得了String类型的函数名
				String fun=sBuilder.toString();
				//此时i指向'('
				double num=0;
				//使得i指向左括号后的第一个数字
				i++;
				//decimalTemp结合decimalTimes获得当前所指数字的小数位数
				double decimalTemp=1;
				//decimalFlag用于标志是否有小数出现
				boolean decimalFlag=false;
				//该while循环用于控制获得函数()内的数字值，放在num中
				while (eString.charAt(i)!=')') {
					if (eString.charAt(i)=='.') {
						decimalFlag=true;
						i++;
						continue;
					}
					if (!decimalFlag) {
						num=num*10+(int)(eString.charAt(i)-'0');
					}
					else {
						decimalTemp*=0.1;
						num+=(double)((eString.charAt(i)-'0')*decimalTemp);
					}
					i++;
				}
				//此时i指向')'
				switch (fun) {
					case "sin" -> temp = Math.sin(num);
					case "cos" -> temp = Math.cos(num);
					case "tan" -> temp = Math.tan(num);
					case "exp" -> temp = Math.exp(num);
					case "ctan" -> temp = (Math.cos(num)) / (Math.sin(num));
				}
				i++;
				String string;
				if (temp<0) {
					string="("+temp+")";
				}
				else {
					string=""+temp;
				}
				builder.append(string);
				continue;
			}
			//没有遇到函数时直接复制
			builder.append(eString.charAt(i));
			i++;
		}
		return builder.toString();
	}
	/**
	 * @return
	 *中缀表达式转后缀表达式
	 */
	private static String toPostFix(String eString) {
		StringBuilder rString = new StringBuilder();
		double num;
		Stack<Character> operand = new Stack<>();
		operand.push('#');
		//firstMul标志位用于在两位及两位以上的数两边加上空格以标记,方便后续计算
		boolean firstMul=false;
		for(int i = 0 ; i<eString.length() ; i++){
			if (isNum(eString.charAt(i))||eString.charAt(i)=='.') {
				if ((isNum(eString.charAt(i+1))||eString.charAt(i+1)=='.')&&!firstMul) {
					rString.append(' ');
					firstMul=true;
				}
				rString.append(eString.charAt(i));
			}
			else if (isOperation(eString.charAt(i))) {
				if (eString.charAt(i)=='('&&eString.charAt(i+1)=='-') {
					rString.append('0');
				}
				if (firstMul) {
					rString.append(' ');
					firstMul=false;
				}
				if (icp(eString.charAt(i))>isp(operand.peek())) {
					operand.push(eString.charAt(i));
				}
				else {
					//相等的情况只可能是左右括号相遇
					if (icp(eString.charAt(i))==isp(operand.peek())) {
						operand.pop();
						continue;
					}
					//icp(eString.charAt(i))<isp(operand.peek())
					i--;
					rString.append(operand.pop());
				}
			}
			else if (eString.charAt(i)=='=') {
				if (firstMul) {
					rString.append(' ');
					firstMul=false;
				}
				while (operand.peek()!='#') {
					rString.append(operand.pop());
				}
				rString.append('=');
			}
		}
		return rString.toString();
	}
	/**
	 * @return 栈外操作符的优先级
	 */
	private static int icp(Character nchar) {
		return switch (nchar) {
			case '#' -> 0;
			case '(' -> 10;
			case '*', '/', '%' -> 4;
			case '^' -> 6;
			case '!' -> 8;
			case '+', '-' -> 2;
			case ')' -> 1;
			default -> -1;
		};
	}
	private static int isp(Character nchar) {
		return switch (nchar) {
			case '#' -> 0;
			case '(' -> 1;
			case '*', '/', '%' -> 5;
			case '!' -> 9;
			case '^' -> 7;
			case '+', '-' -> 3;
			case ')' -> 10;
			default -> -1;
		};
	}
}
