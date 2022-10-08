package server;

import common.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServerTests {

    @Test
    @DisplayName("Порт задан корректно")
    public void  correctPort() {
        int expectedPort = 16346;
        int real = Integer.parseInt(Settings.getResource("port"));

        Assertions.assertEquals(expectedPort, real);
    }
}
