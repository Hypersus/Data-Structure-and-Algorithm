package top.zekehypersus.linearlist;

public class sListTest {
    public static void main(String[] args) {
        sList list = new sList();
        Item[] Items=new Item[6];
        Items[0]=new Item(1,2);
        Items[1]=new Item(4,2);
        Items[2]=new Item(5,4);
        Items[3]=new Item(8,3);
        Items[4]=new Item(9,5);
        Items[5]=new Item(10,9);
        list.insert(Items);
        System.out.println("插入操作测试开始");
        list.showAll();
        System.out.println("插入操作测试结束");
        System.out.println("-----------------------");
        System.out.println("删除操作测试开始");
        int i=list.remove(Items[3]);
        System.out.println("1表示删除成功,0表示删除失败:"+i);
        System.out.println("删除后的表为:");
        list.showAll();
        System.out.println("删除操作测试结束");
        System.out.println("-----------------------");
        System.out.println("修改操作测试开始");
        Item mod=new Item(11,8);
        list.modify(Items[4],mod);
        System.out.println("修改后的表为:");
        list.showAll();
        System.out.println("修改操作测试结束");
        System.out.println("-----------------------");
        System.out.println("查询操作测试开始");
        System.out.println("第二项对应地址为");
        System.out.println(list.query(Items[5]));
        float q=10;
        System.out.println(list.query(q));
        System.out.println("查询操作测试结束");
    }
}
