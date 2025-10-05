// Configuration for browser Geb testing
// This driver configuration will be used by Geb

environments {

    // run via “./gradlew test” // default browser container
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/firefox/FirefoxDriver.java
    firefox {
        driver = 'firefox'
    }

    // run via “./gradlew test -Dgeb.env=chrome”
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/chrome/ChromeDriver.java
    chrome {
        driver = 'chrome'
    }

    // run via “./gradlew test -Dgeb.env=edge”
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/edge/EdgeDriver.java
    edge {
        driver = 'edge'
    }
}
