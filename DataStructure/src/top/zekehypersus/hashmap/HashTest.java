package top.zekehypersus.hashmap;
import java.util.HashMap;
public class HashTest {
    public static void main(String[] args) {
        Hash hash=new Hash();
        HashMap<Integer,Integer> hashOri=new HashMap<>();
        long startInsertTime=System.currentTimeMillis();
        for (int i = 0; i < 4000000; i++) {
            int temp=(int)(Math.random()*1000000);
            hash.insert(temp);
        }
        long endInsertTime=System.currentTimeMillis();
        long startHashTime=System.currentTimeMillis();
        for (int i = 0; i < 4000000; i++) {
            int temp=(int)(Math.random()*1000000);
            hashOri.put(temp, ((Integer)temp).hashCode());
        }
        long endHashTime=System.currentTimeMillis();
        long startQueryTime=System.currentTimeMillis();
        System.out.println(hash.query(2552));
        long endQueryTime=System.currentTimeMillis();
        long startQueryHashTime=System.currentTimeMillis();
        System.out.println(hashOri.get(58911));
        long endQueryHashTime=System.currentTimeMillis();
        System.out.println("实现的哈希表插入5,000,000条数据用时"+(endInsertTime-startInsertTime)+"ms");
        System.out.println("java内置哈希表插入5,000,000条数据用时"+(endHashTime-startHashTime)+"ms");
        System.out.println("Hash查询数据用时"+(endQueryTime-startQueryTime)+"ms");
        System.out.println("HashMap查询数据用时"+(endQueryHashTime-startQueryHashTime)+"ms");
    }
}
