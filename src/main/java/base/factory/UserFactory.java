package base.factory;

import com.github.javafaker.Faker;
import models.SocialTitle;
import models.user.User;
import models.configuration.EnvironmentConfig;
import providers.ConfigProvider;

import java.util.Random;

public class UserFactory {
    private Faker faker = new Faker();
    private EnvironmentConfig environmentConfig = ConfigProvider.getConfig();

    public User getAlreadyRegisteredUser() {
        return new User.Builder()
                .title(SocialTitle.Mr)
                .firstName(environmentConfig.getExistingUSerFirstName())
                .lastName(environmentConfig.getExistingUserLastName())
                .email(environmentConfig.getExistingUserEmail())
                .password(environmentConfig.getExistingUserPassword())
                .builder();
    }

    public User getRandomUser() {
        return new User.Builder()
                .title(SocialTitle.values()[new Random().nextInt(SocialTitle.values().length)])
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password(true))
                .builder();
    }
}
