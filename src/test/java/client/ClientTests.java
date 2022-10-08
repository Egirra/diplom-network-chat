package client;

import common.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClientTests {

    @Test
    @DisplayName("Порт задан корректно")
    public void correctPort() {
        int expectedPort = 16346;
        int real = Integer.parseInt(Settings.getResource("port"));

        Assertions.assertEquals(expectedPort, real);
    }

    @Test
    @DisplayName("Хост задан корректно")
    public void correctHost() {
        String expectedHost = "127.0.0.1";
        String real = Settings.getResource("host");

        Assertions.assertEquals(expectedHost, real);
    }
}
