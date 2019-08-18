package com.example.pockotlin.service

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.task.TaskExecutor
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.*

class UDPServer(val taskExecutor: TaskExecutor) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(UDPServer::class.java)

    private val socket = DatagramSocket(4445)
    private var running: AtomicBoolean = AtomicBoolean(false)
    private val buf = ByteArray(256)

    @ExperimentalStdlibApi
    override fun run(vararg args: String?) {

        GlobalScope.launch {
            while (true) receivePacket()
        }
    }

    @ExperimentalStdlibApi
    fun receivePacket() {
        var packet = DatagramPacket(buf, buf.size)
        try {
            socket.receive(packet)
        } catch (e: IOException) {
            log.error("Error while receiving packet", e)
            return;
        }

        val address = packet.address
        val port = packet.port
        packet = DatagramPacket(buf, buf.size, address, port)
        val received = packet.data.decodeToString().trim { it <= ' ' }
        log.info("Message received \"{}\" from {}:{}", received, address, port)

        if (received == "end") {
            running.set(false)
            return;
        }
        try {
            socket.send(packet)
        } catch (e: IOException) {
            log.error("Error while sending packet", e)
        }
    }


}