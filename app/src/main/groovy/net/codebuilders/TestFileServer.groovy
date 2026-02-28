package net.codebuilders

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.SimpleFileServer
import com.sun.net.httpserver.SimpleFileServer.OutputLevel

import java.nio.file.Path
import java.nio.file.Paths

class TestFileServer {

    static HttpServer server

    TestFileServer() {}

    void start() {
        start(8080)
    }

    void start(int port) {
        println "Starting TestFileServer on port $port..."
        URL staticDirUrl = getClass().getResource("/webroot")

        // Convert the URL to a URI and then to a Path
        Path staticDirPath = Paths.get(staticDirUrl.toURI())
        def addr = new InetSocketAddress(port)

        // Use JDK's built-in SimpleFileServer to serve the static content
        server = SimpleFileServer.createFileServer(addr, staticDirPath, OutputLevel.INFO)
        server.start()
        println "TestFileServer started on port 8080"
    }

    void stop() {
        stop(0)
    }



    void stop(int delay) {
        println "Stopping TestFileServer..."
        if (server) {
            server.stop(delay)
            println "TestFileServer stopped"
        } else {
            println "TestFileServer not found!"
        }
    }

    static void main(String[] args) {
        int port = 8080
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0])
            } catch (NumberFormatException e) {
                println "Invalid port number: ${args[0]}. Using default port 8080."
            }
        }

        def fileServer = new TestFileServer()
        fileServer.start(port)

        // Add shutdown hook to gracefully stop the server
        Runtime.runtime.addShutdownHook(new Thread({
            fileServer.stop(0)
        }))

        // Keep the server running
        println "Press Ctrl+C to stop the server."
        Thread.currentThread().join()
    }

}
