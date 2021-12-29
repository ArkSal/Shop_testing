package pages;

import lombok.Getter;
import models.user.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class LoginPage extends BasePage {
    private Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private HeaderPage headerPage;

    @FindBy(css = "[name='email']:not([placeholder])")
    private WebElement emailTextField;

    @FindBy(css = "[name='password']")
    private WebElement passwordTextField;

    @FindBy(css = ".input-group-btn")
    private WebElement showPasswordButton;

    @FindBy(css = "[data-link-action='sign-in']")
    private WebElement sigInButton;

    @FindBy(css = ".no-account")
    private WebElement createNewAccountButton;

    @FindBy(css = ".alert-danger")
    private WebElement loginAlertInfo;

    public LoginPage(WebDriver driver) {
        super(driver);
        headerPage = new HeaderPage(driver);
    }

    public CreateAccountPage goToNewAccountCreation() {
        clickOnElement(createNewAccountButton);
        return new CreateAccountPage(driver);
    }

    public LoginPage loginWithNonExistingUser() {
        sendKeysToElement(emailTextField, "Nonexisting@email.com");
        sendKeysToElement(passwordTextField, "randomPassword1");
        clickOnElement(sigInButton);
        logger.info("Logged with nonexistent user");
        return this;
    }

    public String getLoginAlertText() {
        return loginAlertInfo.getText();
    }

    public LoginPage loginWithAlreadyDefinedUser(User existingUser) {
        sendKeysToElement(emailTextField, existingUser.getEmail());
        sendKeysToElement(passwordTextField, existingUser.getPassword());
        clickOnElement(sigInButton);
        logger.info("Logged with already existing user");
        return new LoginPage(driver);
    }
}
