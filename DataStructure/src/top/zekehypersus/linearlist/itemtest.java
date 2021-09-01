package top.zekehypersus.linearlist;

public class itemtest {
    public static void main(String[] args) {
        Item[] itemTest=new Item[2];
        itemTest[0]=new Item(1,2);
        itemTest[1]=new Item(2,2);
        System.out.println("第一项:"+"系数："+itemTest[0].coefficient+"  指数:"+itemTest[0].exponent);
        System.out.println("第二项:"+"系数："+itemTest[1].coefficient+"  指数:"+itemTest[1].exponent);
        System.out.println("两项可合并？");
        System.out.println(itemTest[0].sameExp(itemTest[1]));
        itemTest[0].setCoef(4);
        itemTest[0].setExp(5);
        System.out.println("第一项修正后系数为"+itemTest[0].exponent);
        System.out.println("第一项修正后指数为"+itemTest[0].coefficient);
    }
}
