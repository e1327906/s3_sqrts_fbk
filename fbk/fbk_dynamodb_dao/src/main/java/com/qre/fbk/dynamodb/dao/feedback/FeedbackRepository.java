package com.qre.fbk.dynamodb.dao.feedback;

import com.qre.fbk.dynamodb.entity.base.DbFieldName;
import com.qre.fbk.dynamodb.entity.feedback.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackRepository {

    private static final String TABLE_NAME = DbFieldName.FEEDBACK_TABLE;

    private final DynamoDbTable<Feedback> feedbackTable;

    @Autowired
    public FeedbackRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.feedbackTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(Feedback.class));
    }

    // Create or Update Feedback
    public void saveFeedback(Feedback feedback) {
        feedbackTable.putItem(PutItemEnhancedRequest.builder(Feedback.class).item(feedback).build());
    }

    // Retrieve Feedback by ID
    public Feedback getFeedbackById(String feedbackId) {
        Key key = Key.builder()
                .partitionValue(feedbackId)
                .build();
        return feedbackTable.getItem(r -> r.key(key));
    }

    // Update Feedback
    public void updateFeedback(Feedback feedback) {
        feedbackTable.updateItem(feedback);
    }

    // Delete Feedback by ID
    public void deleteFeedback(String feedbackId) {
        Key key = Key.builder()
                .partitionValue(feedbackId)
                .build();
        feedbackTable.deleteItem(r -> r.key(key));
    }

    // Get all Feedback records
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            // Scan the entire table
            feedbackTable.scan().items().forEach(feedbackList::add);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return feedbackList;
    }

    public List<Feedback> getFeedbackByDate(Instant targetDate) {
        List<Feedback> feedbackList = new ArrayList<>();
        Instant startOfDay = targetDate.truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        Instant endOfDay = startOfDay.plus(1, java.time.temporal.ChronoUnit.DAYS);

        // Filter expression to match the date range
        String filterExpression = "#cd >= :startDate AND #cd < :endDate";

        try {
            // Build the filter expression
            Expression expression = Expression.builder()
                    .expression(filterExpression)
                    .putExpressionName("#cd", "createdDatetime")
                    .putExpressionValue(":startDate", AttributeValue.builder().s(startOfDay.toString()).build())
                    .putExpressionValue(":endDate", AttributeValue.builder().s(endOfDay.toString()).build())
                    .build();

            // Scan the table and filter by the date range
            feedbackTable.scan(r -> r.filterExpression(expression))
                    .items()
                    .forEach(feedbackList::add);

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            throw e;
        }

        return feedbackList;
    }
}