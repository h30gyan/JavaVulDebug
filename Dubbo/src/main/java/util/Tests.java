package util;

import java.io.IOException;
import java.io.Serializable;

public class Tests implements Serializable {
    private String name;

    public Tests(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        try {
            Runtime.getRuntime().exec(this.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Test{" +
                "name='" + name + '\'' +
                '}';
    }
}