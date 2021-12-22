package providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import models.configuration.Configuration;
import models.configuration.Environment;
import models.configuration.EnvironmentConfig;
import models.configuration.EnvironmentName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ConfigProvider {
    static Logger logger = LoggerFactory.getLogger(ConfigProvider.class);
    private final static String YMLConfigPath = "src/main/resources/environments.yaml";
    private EnvironmentConfig config;
    private static ConfigProvider instance;

    private ConfigProvider() {
        try {
            config = readConfig().getEnvironment(getEnvName());
        } catch (Exception e) {
            tryGetConfigFromFlagValues();
        }
    }

    private void tryGetConfigFromFlagValues() {
        try {
            logger.info("Trying read from flag");
            config = readConfig().getEnvironment(getEnvNameWithYFlag());
        } catch (Exception e) {
            logger.error("Wrong environment value typed");
            e.printStackTrace();
        }
    }

    private EnvironmentName getEnvNameWithYFlag() throws Exception {
        Environment environment = readConfig().getEnvironment();
        if (environment.getTest().getFlag().equals("Y")) {
            logger.info("Read environment from flag value");
            return EnvironmentName.TEST;
        } else if (environment.getIntegrate().getFlag().equals("Y")) {
            logger.info("Read environment from flag value");
            return EnvironmentName.INTEGRATE;
        } else if (environment.getStag().getFlag().equals("Y")) {
            logger.info("Read environment from flag value");
            return EnvironmentName.STAG;
        } else {
            return null;
        }
    }

    public static ConfigProvider getInstance() {
        if (instance == null) {
            instance = new ConfigProvider();
        }
        return instance;
    }

    public static EnvironmentConfig getConfig() {
        return getInstance().config;
    }

    private static EnvironmentName getEnvName() {
        logger.info("Get values from : " + System.getProperty("Env_Val") + " environment");
        return EnvironmentName.valueOf(System.getProperty("Env_Val"));
    }

    private static Configuration readConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(YMLConfigPath), Configuration.class);
    }
}
