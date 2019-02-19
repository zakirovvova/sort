package factory.checkerFactory;

public class CheckerString implements InputChecker {
    @Override
    public boolean isCorrect(Object obj) {
        String string = (String)obj;
        return !string.contains(" ");
    }
}