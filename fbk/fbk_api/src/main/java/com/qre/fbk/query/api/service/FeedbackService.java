package com.qre.fbk.query.api.service;

import com.qre.fbk.dto.feedback.FeedbackDto;
import com.qre.fbk.dynamodb.entity.feedback.Feedback;

import java.util.Date;
import java.util.List;

public interface FeedbackService {

    Feedback processFeedback(FeedbackDto feedbackDto);

    Feedback createOrUpdateFeedback(FeedbackDto feedbackDto);

    Feedback getFeedbackById(String feedbackId);

    void updateFeedback(FeedbackDto feedbackDto);

    void deleteFeedback(String feedbackId);

    List<Feedback> getAllFeedback();

    List<Feedback> getFeedbackByDate(Date targetDate);
}
