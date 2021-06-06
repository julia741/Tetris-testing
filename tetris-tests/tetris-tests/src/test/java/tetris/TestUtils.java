
package tetris;

public class TestUtils {

    public static Throwable thrownException(Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            return t;
        }
        throw new AssertionError("expected to throw an exception, but did not");
    }
}
