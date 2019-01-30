import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class Example {


    @Test
    public void testOne() {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(new Object());
        for (Object ob : list) {
            System.out.println("============");
            list.remove(ob);
        }


        System.out.println(list.size());
    }

    @Test
    public void testTwo() {
        ArrayList<Object> list = new ArrayList();
        list.add(new Object());
        Iterator var2 = list.iterator();

        while (var2.hasNext()) {
            Object ob = var2.next();
            System.out.println("============");
            list.remove(ob);
        }

        System.out.println(list.size());
    }

    /**
     * System.arraycopy
     */
    @Test
    public void testThree(){
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(new Object());

    }

    @Test
    public void testFour(){
        System.out.println(System.currentTimeMillis());
    }

}
