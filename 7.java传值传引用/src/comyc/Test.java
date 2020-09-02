package comyc;

public class Test {

    public void changeVal01(int data){
        data = 20;
    }

    public void changeVal01(Person person){
        person.setName("xxx");
    }

    public void changeVal03(String str){
        str = "xxx";
    }

    public void changeVal04(String str){
        str = str + "xxx";
    }

    public static void main(String[] args) {
        Test test = new Test();
        int data = 10;
        test.changeVal01(data);
        System.out.println(data);  //10

        Person person = new Person("abc");
        test.changeVal01(person);
        System.out.println(person.getName());    //xxx

        String str = "abc";
        test.changeVal03(str);  
        System.out.println(str);    //abc

        test.changeVal04(str);
        System.out.println(str);    //abc
    }
}
class Person{
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
