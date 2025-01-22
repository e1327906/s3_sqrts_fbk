package com.qre.fbk.query.api.service.strategy;

import com.qre.fbk.dynamodb.entity.feedback.Feedback;
import org.springframework.stereotype.Component;

@Component
public class SuggestionFeedbackStrategy implements FeedbackStrategy {

    @Override
    public String generateAcknowledgmentSubject() {
        return "SQRTS - Thank you for your Suggestion!";
    }

    @Override
    public String generateAcknowledgment(Feedback feedback) {
        return String.format("""
                Dear %s,
                \nWe want to express our gratitude for your valuable suggestion. Your suggestions plays a crucial role in guiding our development efforts.
                \nTicket ID: %s has been created to track this issue.
                \nThank you for being an active part of our community.
                \nSuggestion given:
                %s
                """, feedback.getName(), feedback.getFeedbackId(), feedback.getMessage());
    }
}
