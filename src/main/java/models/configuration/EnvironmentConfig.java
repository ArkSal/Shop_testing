package models.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EnvironmentConfig {
    private String ulr;
    private String browser;
    private String wait;
    private String existingUSerFirstName;
    private String existingUserLastName;
    private String existingUserEmail;
    private String ExistingUserPassword;
    private String flag;

    public String getUlr() {
        return ulr;
    }

    public String getBrowser() {
        return browser;
    }

    public String getWait() {
        return wait;
    }

    public String getExistingUserEmail() {
        return existingUserEmail;
    }

    public String getExistingUserPassword() {
        return ExistingUserPassword;
    }

    public String getExistingUSerFirstName() {
        return existingUSerFirstName;
    }

    public String getExistingUserLastName() {
        return existingUserLastName;
    }

    public String getFlag() {
        return flag;
    }
}