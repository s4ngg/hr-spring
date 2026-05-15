package kr.co.korea.rag_backend.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "ai")
public class AiProperties {
   
   private FastApi fastApi = new FastApi();
   private Document document = new Document(); 
   private User user = new User();
   private RateLimit rateLimit =new RateLimit();
   
   @Setter
   @Getter
   public static class FastApi {
      private String baseUrl = "http://localhost:8000";
      private String generatePath = "/generate";
      private int connectTimeoutMillis = 10_000;
      private int readTimeoutMillis = 120_000;
   }
   
   @Setter
   @Getter
   public static class Document {
      private String storagePath = "./data/documents";
      private int maxConextCharacter = 6_000;
      private int chunkSize = 200;
      private int previewLength = 500;
      private long maxUploadBytes = 10*1024*1024; // 10MB
   }
   
   @Getter
   @Setter
   public static class User {
      private String defaultId = "frontend-user";
   }
   
   @Getter
   @Setter
   public static class RateLimit {
      private long secondLimit = 2;
      private long minuteLimit = 15;
      private long dailyLimit = 100;
      private boolean localFallbackEndable = true;
   }
   

}