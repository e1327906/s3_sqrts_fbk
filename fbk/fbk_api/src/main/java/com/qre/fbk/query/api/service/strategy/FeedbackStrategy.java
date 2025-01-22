package com.qre.fbk.query.api.service.strategy;


import com.qre.fbk.dynamodb.entity.feedback.Feedback;

public interface FeedbackStrategy {

    String generateAcknowledgmentSubject();
    String generateAcknowledgment(Feedback feedback);

}
