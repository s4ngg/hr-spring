package kr.co.korea.rag_backend.ai.dto;

public enum DocumentFileType {
	DOCX,
	TEXT;
	
	public static DocumentFileType from(String fileName,
										String contentType) {
		String normalizedName = fileName == null ? "" : fileName.toLowerCase();
		String normalizedType = contentType == null ? "" : contentType.toLowerCase();

		if (normalizedName.endsWith(".docx")
				|| normalizedType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return DOCX;
		}
		
		return TEXT;
	}
}
