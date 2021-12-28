package pages;

import base.factory.UserFactory;
import models.SocialTitle;
import models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CreateAccountPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(CreateAccountPage.class);

    @FindBy(css = ".radio-inline")
    private List<WebElement> socialTitleRadioButtons;

    @FindBy(css = "[name='firstname']")
    private WebElement firstNameTextField;

    @FindBy(css = "[name='lastname']")
    private WebElement lastNameTextField;

    @FindBy(css = "[name='email']:not([placeholder])")
    private WebElement emailTextFieldInForm;

    @FindBy(css = "[name='password']")
    private WebElement passwordTextField;

    @FindBy(css = "[type='button']")
    private WebElement showPasswordButton;

    @FindBy(css = "[name='birthday']")
    private WebElement birthdateTextField;

    @FindBy(css = "[type='checkbox']")
    private List<WebElement> checkboxesList;

    @FindBy(css = "[data-link-action='save-customer']")
    private WebElement saveNewUserButton;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    public CreateAccountPage fillFormWithNewUserData(User createdUser) {
        User newRandomUser = new UserFactory().getRandomUser();
        userDatabase.addUserToDatabase(newRandomUser);
        logger.info("Created random user");
        pickCorrectSocialTitleButton(createdUser.getTitle());
        firstNameTextField.sendKeys(createdUser.getFirstName());
        lastNameTextField.sendKeys(createdUser.getLastName());
        passwordTextField.sendKeys(createdUser.getPassword());
        emailTextFieldInForm.sendKeys(createdUser.getEmail());
        checkboxesList.get(1).click();
        checkboxesList.get(3).click();
        return this;
    }

    private void pickCorrectSocialTitleButton(SocialTitle socialTitle) {
        if ((socialTitle.equals(SocialTitle.Mr))) {
            socialTitleRadioButtons.get(0).click();
        } else socialTitleRadioButtons.get(1).click();
    }

    public MainPage clickSaveNewUSerButton() {
        saveNewUserButton.click();
        logger.info("New user form filled with new user");
        return new MainPage(driver);
    }

}
