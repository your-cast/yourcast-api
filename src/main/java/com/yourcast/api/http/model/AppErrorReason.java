package com.yourcast.api.http.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppErrorReason {

  @Schema(
      type = "text",
      description = "This is the standardized error entry that can be translated by the frontends to a custom error message or used by support to track the origination of a bug.",
      example = "BAD_REQUEST"
  )
  @JsonProperty(value = "code")
  private String code;

  @Schema(
      type = "text",
      description = "Shows if the reason for an unexpected situation is critical or just information. Should be one of values ERROR, WARNING, INFO.",
      example = "ERROR"
  )
  @JsonProperty(value = "severity")
  private String severity;

  @Schema(
      type = "text",
      description = "This is a human-readable message in user-requested language. Errors received must give an answer to the API customer what to do next.",
      example = "Required entity not found."
  )
  @JsonProperty(value = "message")
  private String message;

  @Schema(
      type = "text",
      description = "The path of the problematic field causes the error.",
      example = "/api/v1/monitoring/update"
  )
  @JsonProperty(value = "path")
  private String path;
}