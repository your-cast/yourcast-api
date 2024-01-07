package com.yourcast.api.http.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppError {

  @Schema(
      type = "text",
      description = "The field that identifies specific error occurrence. The format of the field should be UUID.",
      example = "c052421c-17ab-457f-9be6-5cbce2724094"
  )
  @JsonProperty(value = "errorId")
  private String errorId;


  @Schema(
      type = "text",
      description = "Should be rewritten from the X-Request-ID header.",
      example = "06f31981-c15d-48fb-86c6-53bfae940802"
  )
  @JsonProperty(value = "requestId")
  private String requestId;

  @Schema(
      type = "text",
      description = "Should be rewritten from the X-Correlation-ID header. Only for RICE API.",
      example = "1gf35981-c13d-48fb-84d2-53bfae949876"
  )
  @JsonProperty(value = "correlationId")
  private String correlationId;


  @Schema(
      type = "integer",
      description = "The status should be the originating HTTP response code.",
      example = "404"
  )
  @JsonProperty(value = "status")
  private Integer status;

  @Schema(
      type = "array",
      description = "The array of the reasons which cause an error. Error response should return at least one reason."
  )
  @JsonProperty(value = "reasons")
  private List<AppErrorReason> reasons;
}