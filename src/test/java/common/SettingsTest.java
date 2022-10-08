package common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SettingsTest {

    @Test
    @DisplayName("Логирование прошло успешно")
    public void successLog() {
        String logPath = "";
        String userName = "";
        String msg = "";

        Assertions.assertDoesNotThrow(() -> Settings.log(logPath, userName, msg));
    }
}
