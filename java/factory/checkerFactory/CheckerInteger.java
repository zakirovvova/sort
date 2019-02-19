package factory.checkerFactory;

public class CheckerInteger implements InputChecker {
    @Override
    public boolean isCorrect(Object obj) {
        String string = (String)obj;
        boolean returnValue;
        try {
            Integer integer = Integer.parseInt(string);
            returnValue = true;
        } catch (NumberFormatException e) {
            returnValue = false;
        }
        return returnValue;
    }
}