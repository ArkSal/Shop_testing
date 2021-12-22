package models.configuration;

public class Configuration {
    private Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    public EnvironmentConfig getEnvironment(EnvironmentName environmentName) throws Exception {
        switch (environmentName) {
            case INTEGRATE:
                return environment.getIntegrate();
            case TEST:
                return environment.getTest();
            case STAG:
                return environment.getStag();
            default:
                throw new Exception();
        }
    }
}
