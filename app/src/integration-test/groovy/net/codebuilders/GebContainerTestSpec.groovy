package net.codebuilders

import grails.plugin.geb.ContainerGebConfiguration
import grails.plugin.geb.ContainerGebSpec

/**
 * See https://docs.grails.org/latest/guide/testing.html#functionalTesting and https://www.gebish.org/manual/current/
 * for more instructions on how to write functional tests with Grails and Geb.
 */
@ContainerGebConfiguration(reporting = true)
class GebContainerTestSpec extends ContainerGebSpec {

    static Process serverProcess
    static int serverPort = 8080

    def setupSpec() {
        startServerInSeparateJVM()
        waitForServerToBeReady()
    }

    private static void startServerInSeparateJVM() {
        println "Starting TestFileServer in separate JVM on port $serverPort..."

        // Get the classpath from the current JVM
        String classpath = System.getProperty("java.class.path")
        String javaHome = System.getProperty("java.home")
        String javaBin = "$javaHome/bin/java"

        // Build the command to start TestFileServer in a separate JVM
        List<String> command = [
            javaBin,
            "-cp",
            classpath,
            "net.codebuilders.TestFileServer",
            serverPort.toString()
        ]

        ProcessBuilder processBuilder = new ProcessBuilder(command)
        processBuilder.inheritIO() // Redirect output to current process

        try {
            serverProcess = processBuilder.start()
            println "TestFileServer process started with PID: ${serverProcess.pid()}"
        } catch (IOException e) {
            throw new RuntimeException("Failed to start TestFileServer in separate JVM", e)
        }
    }

    private static void waitForServerToBeReady() {
        println "Waiting for TestFileServer to be ready..."
        int maxRetries = 30
        int retryDelayMs = 1000

        for (int i = 0; i < maxRetries; i++) {
            try {
                URL url = new URL("http://localhost:$serverPort/")
                HttpURLConnection connection = (HttpURLConnection) url.openConnection()
                connection.requestMethod = "GET"
                connection.connectTimeout = 1000
                connection.readTimeout = 1000

                int responseCode = connection.responseCode
                connection.disconnect()

                if (responseCode >= 200 && responseCode < 500) {
                    println "TestFileServer is ready!"
                    return
                }
            } catch (Exception e) {
                // Server not ready yet, continue waiting
            }

            Thread.sleep(retryDelayMs)
        }

        throw new RuntimeException("TestFileServer failed to start within ${maxRetries * retryDelayMs / 1000} seconds")
    }

    def "the home page title is correct"() {
        when: "go to localhost"
        go "/"

        then: "the page title should be correct"
        title == "Hello Geb"
    }

    def "the home page heading is correct"() {
        when: "go to localhost"
        go "/"

        then: "the welcome header should be displayed"
        $("h1").text() == "Welcome to the Geb/Spock Test"
    }

    def cleanup() {
        // give the vnc container time to copy the video
        sleep(1000)
    }

    def cleanupSpec() {
        stopServerProcess()
    }

    private static void stopServerProcess() {
        if (serverProcess != null && serverProcess.alive) {
            println "Stopping TestFileServer process (PID: ${serverProcess.pid()})..."
            serverProcess.destroy()

            // Wait for graceful shutdown
            try {
                boolean exited = serverProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)
                if (!exited) {
                    println "Server did not stop gracefully, forcing termination..."
                    serverProcess.destroyForcibly()
                    serverProcess.waitFor()
                }
                println "TestFileServer process stopped"
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt()
                serverProcess.destroyForcibly()
            }
        }
    }

}
