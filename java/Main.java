import Manager.Manager;

public class Main {
    public static void main(String[] args) {
        String[] massStr = new String[args.length];
        System.arraycopy(args, 0, massStr, 0, args.length);
        Manager manager = new Manager();
        manager.run(massStr);
    }
}