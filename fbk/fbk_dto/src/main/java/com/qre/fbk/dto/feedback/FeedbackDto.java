package com.qre.fbk.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDto implements Serializable {
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("category")
    private Integer category;

    @JsonProperty("message")
    private String message;

    @JsonProperty("id")
    private String feedbackId;

}
