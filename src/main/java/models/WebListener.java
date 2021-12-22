package models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebListener extends AbstractWebDriverEventListener {
    private Logger logger = LoggerFactory.getLogger(WebListener.class);

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>EventListenerAfterClick<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>EventListenerBeforeClick<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>EventListenerBeforeNavigate<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>EventListenerAfterNavigate<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
