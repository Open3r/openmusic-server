package com.open3r.openmusic.domain.file.service

import com.open3r.openmusic.domain.file.dto.response.FileUploadResponse
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFile(file: MultipartFile): FileUploadResponse
}