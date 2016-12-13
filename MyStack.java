/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author conzty01
 */
import java.util.ArrayList;
import java.util.Iterator;

public class MyStack<T> implements Iterable<T>{

    private ArrayList<T> lst;

    public MyStack() {
        lst = new ArrayList();
    }
    
    @Override
    public Iterator<T> iterator() {
        return lst.iterator();
    }

    public void push(T o) {
        lst.add(o);
    }

    public T pop() {
        return lst.remove(lst.size() - 1);
    }
    
    public void swap() {
        T t1 = this.pop();
        T t2 = this.pop();
        this.push(t1);
        this.push(t2);
    }

    public boolean isEmpty() {
        return (lst.size() == 0);
    }

    public int stackLen() {
        return lst.size();
    }

    public String toString() {
        String str = "";

        for (int i = 0; i < lst.size(); i++) {
            str += lst.get(i).toString() + "\n";
        }
        return str;
    }
}
