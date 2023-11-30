import java.io.IOException;

public class Calc {

    public void exec(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
    }

}
