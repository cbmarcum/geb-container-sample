package net.codebuilders


import geb.driver.CachingDriverFactory
import geb.driver.DriverFactory
import geb.spock.GebSpec
import org.openqa.selenium.NoSuchSessionException
import org.openqa.selenium.WebDriver
import spock.lang.PendingFeature
import spock.util.concurrent.BlockingVariable


class CachingDriverFactorySpec extends GebSpec {

    def "global driver and per-thread driver factories are independent"() {
        given:
        def stubFactory = Stub(DriverFactory)
        def globalFactory = CachingDriverFactory.global(stubFactory, false)
        def perThreadFactory = CachingDriverFactory.perThread(stubFactory, false)
        def globalDriver2 = new BlockingVariable<WebDriver>()
        def perThreadDriver2 = new BlockingVariable<WebDriver>()

        when:
        def globalDriver1 = globalFactory.driver
        Thread.start { globalDriver2.set(globalFactory.driver) }
        def perThreadDriver1 = perThreadFactory.driver
        Thread.start { perThreadDriver2.set(perThreadFactory.driver) }

        then:
        globalDriver1 == globalDriver2.get()
        globalDriver1 != perThreadDriver1
        perThreadDriver1 != perThreadDriver2.get()
    }

    @PendingFeature(reason = 'will be fixed by https://github.com/apache/groovy-geb/pull/289')
    def "first driver should quit after clear cache and quit driver"() {

        given:
        def firstDriver= browser.driver

        when:
        CachingDriverFactory.clearCacheAndQuitDriver()
        browser.driver = firstDriver
        go('https://groovy.apache.org/geb/manual/snapshot/')

        then:
        thrown(NoSuchSessionException)

    }

    def cleanup() {
        CachingDriverFactory.clearCacheAndQuitDriver()
    }

}
