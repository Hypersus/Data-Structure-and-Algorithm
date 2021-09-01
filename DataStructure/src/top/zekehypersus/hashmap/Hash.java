package top.zekehypersus.hashmap;

public class Hash {
    //新建哈希表的初始表大小设置为7
    private static final int DEFAULT_INITIAL_CAPACITY=7;
    //装填因子设置为0.75
    private static final float LOAD_FACTOR=0.75f;
    //new一个初始表,表中元素为Node类
    transient Node[] table=new Node[DEFAULT_INITIAL_CAPACITY];
    //size表示当前表大小，也是求哈希值时的除数，设置为素数
    private int size=DEFAULT_INITIAL_CAPACITY;
    //used用于指示当前表中已经存在的记录数，以便后续的判断是否到达装填因子进行再散列
    private int used=0;
    /**
     * @param key
     * @param value
     * @return 插入成功返回true
     * 根据给定的的key和value进行插入操作
     * private修饰该方法,不直接开放给其他类调用
     */
    private boolean insert(int key, int value) {
        if (value>=size||value<0)
            return false;
        else {
            table[value]=new Node(key, value);
            used++;
        }
        if (reHashNeeded())
            reHash();
        return true;
    }
    /**
     * @param key
     * @return 插入成功返回true
     * 只开放根据给定key进行插入的操作,插入操作的入口
     */
    public boolean insert(int key) {
        //给定的key和表中已有的key重合则不执行插入操作
        if (query(key)!=-1) {
            return true;
        }
        int temp=hash(key);
        return insert(key, temp);
    }
    /**
     * @param key
     * @return 哈希值
     * hash(int key)按照所给的key返回哈希值
     * 该hash用于查找对应key应当插入的位置
     */
    private int hash(int key) {
        int temp=key%size;
        //该哈希值位置已经被占用则根据线性探查法改变插入位置
        while (table[temp]!=null) {
            if (temp==size-1)
                temp=0;
            else
                temp++;
        }
        return temp;
    }
    //哈希表按关键词查找
    public int query(int key) {
        int temp=key%size;
        boolean flag=false;
        while(table[temp]!=null){
            if (table[temp].key!=key) {
                if (temp==size-1)
                    temp=0;
                else
                    temp++;
            }
            else {
                flag=true;
                break;
            }
        }
        //flag=true表示表中含有该元素，返回value
        if (flag)
            return temp;
        //找不到则返回-1
        return -1;
    }
    //按照key查找删除
    public boolean remove(int key) {
        //表中不存在该元素则无法删除，返回false
        int temp;
        if ((temp=query(key))==-1)
            return false;
        table[temp]=null;
        used--;
        return true;
    }
    //清除表
    public boolean clear() {
        if (used==0) {
            return true;
        }
        for(int i=0;i<size;i++){
            if (table[i]!=null)
                table[i]=null;
        }
        return true;
    }
    //判断是否需要再散列
    private boolean reHashNeeded() {
        float temp= (float)used/(float)size;
        if (temp>=LOAD_FACTOR) {
            return true;
        }
        return false;
    }
    //再散列实现方法
    private boolean reHash() {
        Node[] temp=table;
        table=new Node[size=findPrime(size*2)];
        used=0;
        for(int i=0;i<temp.length;i++){
            if (temp[i]!=null) {
                insert(temp[i].key);
            }
        }
        return true;
    }
    //寻找大于给定值的最小素数
    private static int findPrime(int num) {
        for(int i=num;;i++){
            if (isPrime(i))
                return i;
        }
    }
    //判定给定的数是否是素数
    private static boolean isPrime(int num) {
        for(int i=2;i<=Math.sqrt(num);i++){
            if (num%i==0) {return false;}
        }
        return true;
    }
}
class Node{
    final int key;
    int value;
    public Node(int key, int value){
        this.key=key;
        this.value=value;
    }
}
