package models;

import java.time.LocalDate;

public class User {
    private SocialTitle title;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthdayDate;

    public static class Builder {
        private SocialTitle title;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private LocalDate birthdayDate;

        public Builder title(SocialTitle title) {
            this.title = title;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder birthday(LocalDate birthday) {
            this.birthdayDate = birthday;
            return this;
        }

        public User builder() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.title = builder.title;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.birthdayDate = builder.birthdayDate;
    }

    public SocialTitle getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public String getUserFullName() {
        return firstName + " " + lastName;
    }
}
