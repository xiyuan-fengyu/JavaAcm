package com.xiyuan.acm.factory;

/**
 * http://www.lintcode.com/zh-cn/problem/toy-factory/
 * Created by xiyuan_fengyu on 2016/11/2.
 */
interface Toy {
    void talk();
}

class Dog implements Toy {
    @Override
    public void talk() {
        System.out.println("Wow");
    }
}

class Cat implements Toy {
    @Override
    public void talk() {
        System.out.println("Meow");
    }
}

public class ToyFactory {
    /**
     * @param type a string
     * @return Get object of the type
     */
    public Toy getToy(String type) {
        if (type.equals("Dog")) {
            return new Dog();
        }
        else if (type.equals("Cat")) {
            return new Cat();
        }
        else return null;
    }
}
