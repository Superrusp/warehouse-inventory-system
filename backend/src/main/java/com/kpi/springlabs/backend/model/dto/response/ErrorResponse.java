package com.kpi.springlabs.backend.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@ApiModel(value = "Error Response", description = "Wraps response information to any error occurred.")
public class ErrorResponse {

    @ApiModelProperty(value = "Path")
    private final String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "Timestamp", position = 1)
    private final LocalDateTime timestamp;

    @ApiModelProperty(value = "Status", position = 2)
    private final int status;

    @ApiModelProperty(value = "Error", position = 3)
    private final String error;

    @ApiModelProperty(value = "Message", position = 4)
    private final String message;
}
