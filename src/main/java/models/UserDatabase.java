package models;

import models.configuration.EnvironmentConfig;
import providers.ConfigProvider;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static List<User> usersList;
    private static EnvironmentConfig environmentConfig = ConfigProvider.getConfig();

    public UserDatabase() {
        usersList = new ArrayList<>();
        usersList.add(new User.Builder().title(SocialTitle.Mr)
                .firstName(environmentConfig.getExistingUSerFirstName())
                .lastName(environmentConfig.getExistingUserLastName())
                .email(environmentConfig.getExistingUserEmail())
                .password(environmentConfig.getExistingUserPassword())
                .builder());
    }

    public void addUserToDatabase(User user) {
        usersList.add(user);
    }

    public User getExistingUser() {
        return usersList.get(0);
    }
}
