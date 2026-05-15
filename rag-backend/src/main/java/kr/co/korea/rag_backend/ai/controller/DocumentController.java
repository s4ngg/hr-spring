package kr.co.korea.rag_backend.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.korea.rag_backend.ai.constraints.AiApiPaths;
import kr.co.korea.rag_backend.ai.dto.UploadDocumentRequest;
import kr.co.korea.rag_backend.ai.dto.UploadDocumentResponse;
import kr.co.korea.rag_backend.ai.service.DocumentUploadService;
import kr.co.korea.rag_backend.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentController {
   
   private final DocumentUploadService documentUploadService;

   @PostMapping(AiApiPaths.UPLOAD)
   public ResponseEntity<ApiResponse<UploadDocumentResponse>> upload(
         @Valid @RequestBody UploadDocumentRequest request
   ) {
      UploadDocumentResponse response = documentUploadService.upload(request);
      return ResponseEntity.ok(ApiResponse.success(response));
   }

}



