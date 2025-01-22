package com.qre.fbk.dynamodb.entity.feedback;

import com.qre.fbk.dynamodb.entity.base.DbFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Feedback {

    private String feedbackId;

    private String name;

    private String email;

    private Integer category;

    private String message;

    private Instant createdDatetime;

    private String createdUser;

    private Instant updatedDatetime;

    private String updatedUser;

    private String terminalId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(DbFieldName.FEEDBACK_ID)
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }
}