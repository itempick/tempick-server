package com.tempick.tempickserver.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cloud.aws")
class AwsS3Properties {
    lateinit var region: String
    lateinit var s3: S3
    lateinit var credentials: Credentials
    var maxFileSize: Long = 53927646 // Default: 100MB

    class S3 {
        lateinit var bucket: String
    }

    class Credentials {
        lateinit var accessKey: String
        lateinit var secretKey: String
    }
}