package top.zekehypersus.linearlist;
import top.zekehypersus.linearlist.sList.sNode;
public class Polynomial {
    public sList polyList=new sList();
    /**
     * 默认构造方法
     */
    public Polynomial(){

    }
    /**
     * @param items
     * 多项式构造方法,用传入item数组的方式构造，存储在mylist链表中
     */
    public Polynomial(Item[] items){
        this.polyList.insert(items);
    }
    public Polynomial(Item[] items, boolean noOrder) {
        if (noOrder) {
            this.polyList.insertNotOrder(items);
        }
    }
    /**
     * @param orginp
     * 将orginp的内容复制到this对象中
     */
    public void copy(Polynomial orginp) {
        if(this.polyList.getlength()==0&&orginp.polyList.getlength()!=0){
            sNode originRover=orginp.polyList.head.getNext();
            while (originRover!=null) {
                this.polyList.insert(originRover.getNode());
                originRover=originRover.getNext();
            }
        }
    }
    /**
     * this对象的数据域中所有系数取负
     */
    public void reverse() {
        if (this.polyList.getlength()!=0) {
            sNode rover=this.polyList.head.getNext();
            while (rover!=null) {
                float temp=rover.getNode().coefficient*(-1);
                rover.getNode().coefficient=temp;
                rover=rover.getNext();
            }
        }
    }
    /**
     * @param orginp
     * 得到与orginp的系数全部为相反数的polynomial
     * 用于减法操作
     */
    public void recopy(Polynomial orginp) {
        this.copy(orginp);
        this.reverse();
    }
    /**
     * @param p1
     * @param p2
     * 两个多项式相加,结果存放在第一个多项式的链表中
     */
    public void add(Polynomial p1,Polynomial p2) {
        sNode rover=p2.polyList.head;
        while (rover.getNext()!=null) {
            rover=rover.getNext();
            sNode p1rover=p1.polyList.head.getNext();
            for(int i=0;i<p1.polyList.getlength();i++){
                if(p1rover.getNode().exponent==rover.getNode().exponent){
                    p1rover.getNode().coefficient+=rover.getNode().coefficient;
                    p2.polyList.remove(rover);
                    break;
                }
                else {
                    p1rover=p1rover.getNext();
                }
            }

        }
        Item[] items=new Item[p2.polyList.getlength()];
        sNode p2rover=p2.polyList.head.getNext();
        for(int j=0;j<p2.polyList.getlength();j++){
            items[j]=p2rover.getNode();
            p2rover=p2rover.getNext();
        }
        p1.polyList.insert(items);
    }
    /**
     * @param p1
     * @param p2
     * @return 多项式相乘结果作为该函数返回值
     */
    public void subtract(Polynomial p1,Polynomial p2) {
        Polynomial p2p = new Polynomial();
        p2p.recopy(p2);
        this.add(p1, p2p);
        float q=0;
        while (p1.polyList.query(q)!=null) {
            p1.polyList.remove(p1.polyList.query(q));
        }
    }
    public Polynomial multi(Polynomial p1,Polynomial p2) {
        int sum=p1.polyList.getlength()*p2.polyList.getlength();
        Item[] items=new Item[sum];
        sNode p1rover=p1.polyList.head.getNext();
        int cnt=0;
        for(int i=0;i<p1.polyList.getlength();i++){
            sNode p2rover=p2.polyList.head.getNext();
            for(int j=0;j<p2.polyList.getlength();j++){
                items[cnt]=new Item(p1rover.getNode().coefficient*p2rover.getNode().coefficient
                        ,p1rover.getNode().exponent+p2rover.getNode().exponent);
                cnt++;
                p2rover=p2rover.getNext();
            }
            p1rover=p1rover.getNext();
        }
        Polynomial res=new Polynomial(items);
        return res;
    }
}

