package models;

import base.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HeaderPage;
import pages.LoginPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegisterUserTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(RegisterUserTest.class);

    @Test
    void loginWithNonExistingUser() {
        application.open()
                .clickSignInButton()
                .loginWithNonExistingUser();

        assertThat(new LoginPage(driver).getLoginAlertText(), equalTo("Authentication failed."));
    }

    @Test
    void loginWithAnExistingUser() {
        User alreadyExistingUser = new UserFactory().getAlreadyRegisteredUser();
        application.open()
                .clickSignInButton()
                .loginWithAlreadyDefinedUser(alreadyExistingUser);

        assertThat(new HeaderPage(driver).getLoggerAccountFullName(), equalTo(alreadyExistingUser.getUserFullName()));
    }

    @Test
    void registerNewUser() {
        User newRandomCreatedUser = new UserFactory().getRandomUser();
        application.open()
                .clickSignInButton()
                .goToNewAccountCreation()
                .fillFormWithNewUserData(newRandomCreatedUser)
                .clickSaveNewUSerButton();

        assertThat(new HeaderPage(driver).getLoggerAccountFullName(), equalTo(newRandomCreatedUser.getUserFullName()));
    }
}
