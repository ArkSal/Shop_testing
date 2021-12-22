package models.configuration;

public class Environment {
    private EnvironmentConfig integrate;
    private EnvironmentConfig test;
    private EnvironmentConfig stag;

    public EnvironmentConfig getIntegrate() {
        return integrate;
    }

    public EnvironmentConfig getTest() {
        return test;
    }

    public EnvironmentConfig getStag() {
        return stag;
    }
}