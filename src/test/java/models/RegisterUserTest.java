package models;

import base.factory.UserFactory;
import models.user.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegisterUserTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(RegisterUserTest.class);

    @Test
    void loginWithNonExistingUser() {
            String alertMessage =
                    application.open()
                            .getHeaderPage()
                            .clickSignInButton()
                            .loginWithNonExistingUser()
                            .getLoginAlertText();

            assertThat(alertMessage, equalTo(environmentConfig.getFailLoginAlertMessage()));
        }

    @Test
    void loginWithAnExistingUser() {
        User alreadyExistingUser = new UserFactory().getAlreadyRegisteredUser();

        String loggedAccountName =
        application.open()
                .getHeaderPage()
                .clickSignInButton()
                .loginWithAlreadyDefinedUser(alreadyExistingUser)
                .getHeaderPage()
                .getLoggerAccountFullName();

        assertThat(loggedAccountName, equalTo(alreadyExistingUser.getUserFullName()));
    }

    @Test
    void registerNewUser() {
        User newRandomCreatedUser = new UserFactory().getRandomUser();

        String createdAccountName =
        application.open()
                .getHeaderPage()
                .clickSignInButton()
                .goToNewAccountCreation()
                .fillFormWithNewUserData(newRandomCreatedUser)
                .clickSaveNewUSerButton()
                        .getHeaderPage()
                        .getLoggerAccountFullName();

        assertThat(createdAccountName, equalTo(newRandomCreatedUser.getUserFullName()));

    }
}
