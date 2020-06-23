
class Task{
    public void task1(){
        System.out.println("task 1");
    }
    public void task2(){
        System.out.println("task 2");
    }
}

public class MultiFrame {
    public static void main(String[] args) {
        Task task = new Task();
        task.task1();
        task.task2();
    }
}
