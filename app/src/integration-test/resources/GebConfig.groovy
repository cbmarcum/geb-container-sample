/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.remote.RemoteWebDriver

// Configuration for container-based Geb testing
// This driver configuration will be used by WebDriverContainerHolder
environments {

    // run via “./gradlew integrationTest” // default browser container
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/firefox/FirefoxDriver.java
    firefox {
        driver = {
            def firefoxOptions = new FirefoxOptions()

            // The remote address will be set by WebDriverContainerHolder via system property
            // webdriver.remote.server before this closure is called
            new RemoteWebDriver(firefoxOptions)
        }
        containerBrowser = 'firefox'
    }

    // run via “./gradlew integrationTest -Dgeb.env=edge”
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/chrome/ChromeDriver.java
    chrome {
        driver = {
            // Chrome preferences to disable password manager and credentials service
            def prefs = [
                    'credentials_enable_service'             : false,
                    'profile.password_manager_enabled'       : false,
                    'profile.password_manager_leak_detection': false
            ]

            def chromeOptions = new ChromeOptions()
            // TO DO: guest would be preferred, but this causes issues with downloads
            // see https://issues.chromium.org/issues/42323769
            // chromeOptions.addArguments('--guest') // do not enable
            chromeOptions.setExperimentalOption('prefs', prefs)

            // The remote address will be set by WebDriverContainerHolder via system property
            // webdriver.remote.server before this closure is called
            new RemoteWebDriver(chromeOptions)
        }
        containerBrowser = 'chrome'
    }

    // run via “./gradlew integrationTest -Dgeb.env=edge”
    // See: https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/edge/EdgeDriver.java
    edge {
        driver = {
            def edgeOptions = new EdgeOptions()

            // The remote address will be set by WebDriverContainerHolder via system property
            // webdriver.remote.server before this closure is called
            new RemoteWebDriver(edgeOptions)
        }
    }
    containerBrowser = 'edge'
}
