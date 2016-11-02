package com.xiyuan.acm.factory;

/**
 * Created by xiyuan_fengyu on 2016/11/2.
 */
interface Shape {
    void draw();
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println(
                " ---- \n" +
                "|    |\n" +
                " ----");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println(
                " ---- \n" +
                "|    |\n" +
                "|    |\n" +
                " ----");
    }
}

class Triangle implements Shape {
    @Override
    public void draw() {
        System.out.println(
                "  /\\\n" +
                " /  \\\n" +
                "/____\\");
    }
}

public class ShapeFactory {
    /**
     * @param shapeType a string
     * @return Get object of type Shape
     */
    public Shape getShape(String shapeType) {
        if (shapeType.equals("Rectangle")) {
            return new Rectangle();
        }
        else if (shapeType.equals("Square")) {
            return new Square();
        }
        else if (shapeType.equals("Triangle")) {
            return new Triangle();
        }
        return null;
    }
}
