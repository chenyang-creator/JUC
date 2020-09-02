package com.yc.lambda;

/**
 * 要想接口可以使用lambda表达式，接口必须符合注解@FunctionalInterface的规范
 * 即：接口中发方法只能有一个未实现的方法，如果还想扩展其他的方法，必须用default或static修饰并且有默认的实现
 */
@FunctionalInterface  //作用：会对接口进行检查，保证接口中的方法可以使用lambda表达式
interface Foo{
    //add方法可以使用lambda表达式进行方法的实现
    int add(int x,int y);

    //必须要有方法的实现
    default int mul(int x, int y) {
        return x*y;
    }
    default int mul2(int x, int y) {
        return x*y;
    }
    //必须要有方法的实现
    static int div(int x, int y) {
        return x/y;
    }
}

public class Main {
    public static void main(String[] args) {
        Foo foo = (int x,int y) -> {
            return x+y;
        };
        System.out.println(foo.add(1,2));
        System.out.println(foo.mul(1,2));
        System.out.println(Foo.div(2,2));
    }
}
