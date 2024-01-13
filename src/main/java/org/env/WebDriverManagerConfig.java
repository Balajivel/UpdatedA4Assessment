package org.env;
import java.net.MalformedURLException;
import java.net.URL;

import org.base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;




public class WebDriverManagerConfig extends BaseClass{

    private static final String LOCAL_CONFIG = "local.properties";
    private static final String SAUCELABS_CONFIG = "saucelabs.properties";

    public static WebDriver getDriver() {

        String browser = "chrome";
        
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", browser);
            caps.setCapability("username", "oauth-balajiv095-b01b2");
            caps.setCapability("accessKey", "9c306646-c3e3-4312-8eb5-5a7ace844d50");
            
            try {
                return new RemoteWebDriver(new URL("https://ondemand.saucelabs.com:443/wd/hub"), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
    
}