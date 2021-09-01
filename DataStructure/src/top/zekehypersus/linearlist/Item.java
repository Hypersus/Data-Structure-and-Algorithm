package top.zekehypersus.linearlist;

public class Item {
    public float coefficient;
    public int exponent;

    /**
     * @param exponent
     * @return void
     * 该函数作用为设置某项的指数
     */
    public void setExp(int exponent) {
        this.exponent=exponent;
    }
    /**
     * @param coef
     * @return void
     * 该函数作用为设置某项的系数
     */
    public void setCoef(float coef){
        this.coefficient=coef;
    }
    /**
     * @param item2
     * 复制item的内容
     */
    public void copy(Item item2) {
        this.coefficient=item2.coefficient;
        this.exponent=item2.exponent;
    }
    /**
     * 默认构造方法
     */
    public Item() {

    }
    /**
     * @param coef
     * @param exp
     * 构造方法重载
     */
    public Item(float coef, int exp){
        this.coefficient=coef;
        this.exponent=exp;
    }
    /**
     * @param item2
     * @return boolean
     * 判断两项指数是否相同，相同则返回true
     */
    public boolean sameExp(Item item2){
        return this.exponent == item2.exponent;
    }
    /**
     * @param item2
     * @return boolean
     * 若两个item对象的两个属性相同则返回true
     * 否则返回false
     */
    public boolean same(Item item2) {
        return this.exponent == item2.exponent && this.coefficient == item2.coefficient;
    }
}
