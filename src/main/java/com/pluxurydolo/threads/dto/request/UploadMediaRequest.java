package com.pluxurydolo.threads.dto.request;

public record UploadMediaRequest(
    String mediaUrl,
    String caption
) {
}
