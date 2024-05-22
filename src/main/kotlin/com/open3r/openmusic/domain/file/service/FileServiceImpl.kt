package com.open3r.openmusic.domain.file.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.open3r.openmusic.domain.file.dto.response.FileUploadResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class FileServiceImpl(
    private val amazonS3Client: AmazonS3Client
) : FileService {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    @Value("\${cloud.aws.region.static}")
    private lateinit var region: String

    override fun uploadFile(file: MultipartFile): FileUploadResponse {
        val name = "${UUID.randomUUID()}_${file.originalFilename}"
        val metadata = ObjectMetadata().apply {
            contentType = file.contentType
            contentLength = file.size
        }

        amazonS3Client.putObject(bucket, name, file.inputStream, metadata)

        val url = "https://$bucket.s3.$region.amazonaws.com/$name"

        return FileUploadResponse(name = name, url = url)
    }
}