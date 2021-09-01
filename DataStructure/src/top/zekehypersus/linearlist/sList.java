package top.zekehypersus.linearlist;

public class sList {
    //单链表的头结点，不存放数据
    public sNode head= new sNode();

    /**
     * @return int
     * 返回表长(不包括头结点)
     */
    public int getlength() {
        sNode rover=this.head;
        int length=0;
        while (rover.getNext()!=null) {
            length++;
            rover=rover.getNext();
        }
        return length;
    }

    /**
     * @param aNode
     * @param insItem
     * @return 新结点的指针
     * 此方法作用为在给定的sNode后面插入给定数据的新结点
     */
    public sNode insert(sNode aNode, Item insItem){
        sNode newNode= new sNode();
        newNode.setNode(insItem);
        newNode.setNxt(aNode.getNext());
        aNode.setNxt(newNode);
        return newNode;
    }

    /**
     * @param oneItem
     * @return sNode为对应item应该插入的结点(按照指数降序)，若指数相同即不插入则返回null
     * 不插入时返回null，具体合并的结点位置另外寻找
     * 不插入的时候能不能直接返回合并的结点？？
     */
    public sNode insertNode(Item oneItem) {
        sNode rover=this.head;
        //若为空表则直接添加到头结点之后
        if (rover.getNext()==null) {
            return rover;
        }
        else {
            //指向头结点后的第一个结点
            rover=rover.getNext();
            //说明表中只有一个有效结点
            if(rover.getNext()==null){
                //说明待插入数据应该插入在头结点之后
                if (oneItem.exponent>rover.getNode().exponent) {
                    return this.head;
                }
                else if(oneItem.exponent==rover.getNode().exponent){
                    return null;
                }
                else {
                    return rover;
                }
            }
            else {
                if(oneItem.exponent>this.head.getNext().getNode().exponent){
                    return this.head;
                }
                while (!(rover.getNode().exponent>=oneItem.exponent&&rover.getNext().getNode().exponent<=oneItem.exponent)) {
                    rover=rover.getNext();
                    if (rover.getNext()==null) {
                        return rover;
                    }
                }
                if (rover.getNode().exponent==oneItem.exponent||rover.getNode().exponent==oneItem.exponent) {
                    return null;
                }
                else {
                    return rover;
                }

            }
        }

    }

    /**
     * @param items
     * 给定item数组进行插入操作
     * 插入操作用到了insertNode方法来判断插入位置以及是否进行插入操作
     */
    public void insert(Item[] items) {
        if (items.length==0) {
            return;
        }
        for(Item i:items){
            sNode temp;
            temp=this.insertNode(i);
            if (temp!=null) {
                this.insert(temp,i);
            }
            else {
                sNode mergeNode=query(i.exponent);
                if (mergeNode!=null) {
                    mergeNode.getNode().coefficient+=i.coefficient;
                }
            }
        }
    }
    public void insertNotOrder(Item[] items) {
        if (items.length==0) {
            return;
        }
        for (Item i:items) {
            //表为空只有头结点时直接插入
            if (findLast()==head) {
                insert(head, i);
            }
            //否则先判断是否合并同类项,若不合并则找到尾结点再插入
            else {
                sNode temp=query(i.exponent);
                //插入时合并同类项
                if (temp!=null) {
                    temp.Node.coefficient+=i.coefficient;
                }
                else {
                    insert(findLast(), i);
                }
            }
        }
    }
    /**
     * @param oneItem
     * insert方法重载,插入一个数据时的特殊情况
     */
    public void insert(Item oneItem) {
        Item[] items=new Item[1];
        items[0]=oneItem;
        this.insert(items);
    }
    public void showAll() {
        sNode rover=this.head;
        if (rover.getNext()!=null) {
            rover=rover.getNext();
            System.out.print(rover.getNode().coefficient+"x"+rover.getNode().exponent);
            while (rover.getNext()!=null) {
                rover=rover.getNext();
                System.out.print("+"+rover.getNode().coefficient+"x"+rover.getNode().exponent);
            }

        }
        System.out.println();


    }
    /**
     * @param aNode
     * @return 删除成功返回1，否则返回0
     * 已知要删除的结点地址再进行删除
     */
    public int remove(sNode aNode) {
        sNode rover;
        if (this.head.getNext()==null|| aNode==null) {
            return 0;
        }
        else if (this.head==aNode) {
            this.head=aNode.getNext();
        }
        else {
            rover=this.head;
            while (rover!=null&&rover.getNext()!=aNode)
                rover=rover.getNext();
            if(rover==null)
                return 0;
            else
                rover.setNxt(aNode.getNext());

        }
        return 1;
    }
    /**
     * @param aItem
     * @return
     * 利用写好的remove和query对remove进行重载
     */
    public int remove(Item aItem) {
        sNode temp=this.query(aItem);
        return this.remove(temp);
    }

    /**
     * @param aItem
     * @param mod
     * @return 修改成功返回1，失败返回0
     * 给定结点的数据域进行修改
     */
    public int modify(Item aItem, Item mod) {
        sNode temp;
        temp=this.query(aItem);
        if(temp!=null){
            temp.getNode().copy(mod);
            return 1;
        }
        return 0;
    }
    /**
     * @param aNode
     * @param mod
     * @return 修改成功返回1，失败返回0
     * 给定结点地址调用数据域修改函数进行修改
     */
    public int modify(sNode aNode, Item mod) {
        return this.modify(aNode.getNode(), mod);
    }
    /**
     * @param queItem
     * @return 根据给定数据域进行查询
     */
    public sNode query(Item queItem) {
        sNode rover=this.head;
        //定义中间变量rover初始化为头结点
        //若rover.getNext()不为null说明表中还有元素
        while (rover.getNext()!=null){
            //rover当前所指结点的数据域和所给数据域不等则修改至下一节点
            if(rover.getNode().same(queItem)){
                return rover;
            }
            else{
                rover=rover.getNext();
            }
        }
        //判断表中最后一个结点
        if(rover.getNode().same(queItem)){
            return rover;
        }
        else
            return null;
    }
    /**
     * @param exp
     * @return 根据给定的指数exp进行查找
     */
    public sNode query(int exp){
        sNode rover=this.head;
        if (rover.getNext()==null) {
            System.out.println("空表");
            return null;
        }
        else{
            rover=rover.getNext();
            while (rover.getNode().exponent!=exp&&rover.getNext()!=null) {
                rover=rover.getNext();
            }
            if(rover.getNode().exponent==exp){
                return rover;
            }
            else {
                return null;
            }
        }
    }
    /**
     * @param coef
     * @return 根据给定的系数进行查找
     * 多用于相减操作中系数为0的结点的删除操作
     */
    public sNode query(float coef) {
        sNode rover=this.head;
        if (rover.getNext()==null) {
            System.out.println("空表");
            return null;
        }
        else{
            rover=rover.getNext();
            while (rover.getNode().coefficient!=coef&&rover.getNext()!=null) {
                rover=rover.getNext();
            }
            if(rover.getNode().coefficient==coef){
                return rover;
            }
            else {
                return null;
            }
        }
    }
    public sNode findLast() {
        sNode rover=this.head;
        while (rover.next!=null) {
            rover=rover.next;
        }
        return rover;
    }
    public void sort() {
        //链表长度
        int temp=getlength();
        if (temp>=2) {
            for(int i=1;i<temp;i++){
                //指向第一个有数据的结点
                sNode hover=this.head.next;
                sNode before=this.head;
                for(int j=1; j<=temp-i;j++){
                    if (hover.next.getNode().exponent>hover.getNode().exponent) {
                        before.next=hover.next;
                        hover.next=before.next.next;
                        before.next.next=hover;
                    }
                    else {
                        hover=hover.next;
                    }
                    before=before.next;
                }
            }
        }
    }
    /**
     * 节点类
     * 数据域命名为Node，指针域命名为next
     * 属性私有，提供get和set方法获取和修改属性
     */
    static class sNode{
        private Item Node=new Item();//数据域
        private sNode next=null;//指针域
        public Item getNode() {
            return this.Node;
        }
        public sNode getNext() {
            return this.next;
        }
        public void setNode(Item oneitem) {
            this.Node.setCoef(oneitem.coefficient);
            this.Node.setExp(oneitem.exponent);
        }
        public void setNode(float coef,int exp) {
            this.Node.setCoef(coef);
            this.Node.setExp(exp);
        }
        public void setNode(sNode oneNode) {
            this.setNode(oneNode.Node);
            this.next=oneNode;
        }
        public void setNxt(sNode oneNode) {
            this.next=oneNode;
        }
        public sNode () {

        }
        public sNode(Item oneitem){
            this.setNode(oneitem);
        }
        public sNode(float coef, int exp) {
            this.setNode(coef, exp);
        }
    }
}
