package models.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EnvironmentConfig {
    private String ulr;
    private String browser;
    private String wait;
    private String existingUSerFirstName;
    private String existingUserLastName;
    private String existingUserEmail;
    private String ExistingUserPassword;
    private String FailLoginAlertMessage;
    private String onSalePageTitle;
    private String priceDrop;
    private String flag;
}