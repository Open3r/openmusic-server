package com.open3r.openmusic.domain.file.service.impl

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.open3r.openmusic.domain.file.dto.response.FileUploadResponse
import com.open3r.openmusic.domain.file.service.FileService
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

    override fun uploadFiles(files: List<MultipartFile>): List<FileUploadResponse> {
        return files.map {
            val name = "${UUID.randomUUID()}.${it.originalFilename?.substringAfterLast(".")}"

            val metadata = ObjectMetadata().apply {
                contentType = it.contentType
                contentLength = it.size
            }

            amazonS3Client.putObject(bucket, name, it.inputStream, metadata)

            val url = "https://$bucket.s3.$region.amazonaws.com/$name"

            FileUploadResponse(url)
        }
    }
}