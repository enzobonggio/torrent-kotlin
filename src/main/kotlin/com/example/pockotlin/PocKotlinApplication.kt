package com.example.pockotlin

import com.example.pockotlin.config.taskExecutor
import com.example.pockotlin.handler.MovieHandler
import com.example.pockotlin.handler.TorrentHandler
import com.example.pockotlin.repository.MovieRepository
import com.example.pockotlin.repository.TransmissionRepository
import com.example.pockotlin.repository.YifiRepository
import com.example.pockotlin.route.routes
import com.example.pockotlin.service.*
import kotlinx.coroutines.FlowPreview
import org.springframework.boot.WebApplicationType
import org.springframework.boot.logging.LogLevel
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.mongo.reactiveMongodb
import org.springframework.fu.kofu.webflux.webFlux

@FlowPreview
val app = application(WebApplicationType.REACTIVE) {
    beans {
        bean<YifiRepository>()
        bean<TransmissionRepository>()
        bean<MovieRepository>()
        bean<UDPServer>()
        bean { taskExecutor() }
    }

    beans {
        bean<MovieHandler>()
        bean<TorrentHandler>()
        bean(::routes)
    }

    beans {
        bean<MovieService>()
        bean<TransmissionService>()
        bean<YifiService>()
        bean<TorrentService>()
    }

    logging {
        level = LogLevel.INFO
    }

    reactiveMongodb()

    webFlux {
        port = 8080
        codecs {
            string()
            jackson()
        }
    }
}

@FlowPreview
fun main(args: Array<String>) {
    app.run(args)
}
