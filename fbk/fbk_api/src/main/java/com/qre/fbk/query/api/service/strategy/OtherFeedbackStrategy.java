package com.qre.fbk.query.api.service.strategy;

import com.qre.fbk.dynamodb.entity.feedback.Feedback;
import org.springframework.stereotype.Component;

@Component
public class OtherFeedbackStrategy implements FeedbackStrategy {

    @Override
    public String generateAcknowledgmentSubject() {
        return "SQRTS - Thank you for your feedbackDto!";
    }

    @Override
    public String generateAcknowledgment(Feedback feedbackDto) {
        return String.format("""
                Dear %s,
                \nThank you for reaching out to us. We've received your feedbackDto, and our team will review it promptly. If you have any further questions or concerns, please feel free to contact us. Your engagement with our platform is highly valued.
                \nTicket ID: %s has been created to track this feature.
                \nThank you again for being a part of our journey.
                \nFeedback:
                %s
                """, feedbackDto.getName(), feedbackDto.getFeedbackId(), feedbackDto.getMessage());
    }
}
