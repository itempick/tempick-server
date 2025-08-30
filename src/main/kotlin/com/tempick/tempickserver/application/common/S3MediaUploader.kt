package com.tempick.tempickserver.application.common

import com.tempick.tempickserver.api.rest.common.dto.response.MediaResponse
import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.configuration.properties.AwsS3Properties
import com.tempick.tempickserver.domain.entitiy.MediaFile
import com.tempick.tempickserver.domain.repository.MediaRepository
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class S3MediaUploader(
    private val awsS3Properties: AwsS3Properties,
    private val mediaRepository: MediaRepository,
) {

    private val s3Client: S3Client by lazy {
        S3Client.builder()
            .region(Region.of(awsS3Properties.region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        awsS3Properties.credentials.accessKey,
                        awsS3Properties.credentials.secretKey,
                    ),
                ),
            )
            .build()
    }

    fun upload(file: MultipartFile): MediaResponse {
        if (file.size > awsS3Properties.maxFileSize) {
            throw CoreException(ErrorType.FILE_SIZE_EXCEEDED)
        }

        val originalFileName = file.originalFilename ?: "file"
        val fileName = generateFileName(originalFileName)

        val mediaBucket = "tempick-public-media-bucket"
        val putRequest = PutObjectRequest.builder()
            .bucket(mediaBucket)
            .key(fileName)
            .contentType(file.contentType)
            .acl("public-read")
            .build()

        s3Client.putObject(putRequest, RequestBody.fromInputStream(file.inputStream, file.size))

        val url = "https://${mediaBucket}.s3.${awsS3Properties.region}.amazonaws.com/$fileName"

        val mediaFile = mediaRepository.save(
            MediaFile.create(
                fileUrl = url,
                name = originalFileName,
            )
        )

        return MediaResponse(imageUrl = mediaFile.fileUrl, originalFileName = originalFileName)
    }

    private fun generateFileName(original: String, folder: String = "image"): String {
        val ext = original.substringAfterLast('.', "")
        val uuid = UUID.randomUUID().toString() // Use full UUID for better uniqueness
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
        val sanitizedOriginal = original.substringBeforeLast('.').replace("[^a-zA-Z0-9가-힣._-]".toRegex(), "_")
        return "$folder/${time}_${sanitizedOriginal}_$uuid.$ext"
    }
}
