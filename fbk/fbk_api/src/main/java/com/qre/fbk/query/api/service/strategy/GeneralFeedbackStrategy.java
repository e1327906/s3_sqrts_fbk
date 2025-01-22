package com.qre.fbk.query.api.service.strategy;

import com.qre.fbk.dynamodb.entity.feedback.Feedback;
import org.springframework.stereotype.Component;

@Component
public class GeneralFeedbackStrategy implements FeedbackStrategy {

    @Override
    public String generateAcknowledgmentSubject() {
        return "SQRTS - Thank you for your feedback!";
    }

    @Override
    public String generateAcknowledgment(Feedback feedback) {
        return String.format("""
                Dear %s,
                \nThank you for taking the time to share your thoughts with us. Your input is invaluable as we continually strive to improve SQRTS.
                \nTicket ID: %s has been created to address your concern. A Customer Service Representative will be in contact with you within 3 - 5 working days.
                \nThank you again for being a part of our journey.
                \nFeedback:
                %s
                """, feedback.getName(), feedback.getFeedbackId(), feedback.getMessage());
    }
}
