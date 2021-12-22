package pages;

import models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(LoginPage.class);

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
    }

    public CreateAccountPage goToNewAccountCreation() {
        clickOnElement(createNewAccountButton);
        return new CreateAccountPage(driver);
    }

    public HeaderPage loginWithNonExistingUser() {
        sendKeysToElement(emailTextField, "Nonexisting@email.com");
        sendKeysToElement(passwordTextField, "randomPassword1");
        clickOnElement(sigInButton);
        logger.info("Logged with nonexistent user");
        return new HeaderPage(driver);
    }

    public String getLoginAlertText() {
        return loginAlertInfo.getText();
    }

    public HeaderPage loginWithAlreadyDefinedUser(User existingUser) {
        sendKeysToElement(emailTextField, existingUser.getEmail());
        sendKeysToElement(passwordTextField, existingUser.getPassword());
        clickOnElement(sigInButton);
        logger.info("Logged with already existing user");
        return new HeaderPage(driver);
    }
}
