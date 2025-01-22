package com.qre.fbk.query.api.service.impl;

import com.qre.cmel.ecommsvcs.sdk.dto.MessageDto;
import com.qre.cmel.ecommsvcs.sdk.service.ECommSvcService;
import com.qre.fbk.dto.feedback.FeedbackDto;
import com.qre.fbk.dynamodb.dao.feedback.FeedbackRepository;
import com.qre.fbk.dynamodb.entity.feedback.Feedback;
import com.qre.fbk.query.api.service.FeedbackService;
import com.qre.fbk.query.api.service.strategy.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final ECommSvcService eCommSvcService;

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(
                               ECommSvcService eCommSvcService,
                               FeedbackRepository feedbackRepository) {
        this.eCommSvcService = eCommSvcService;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback processFeedback(FeedbackDto feedbackDto) {

        Feedback feedback = createOrUpdateFeedback(feedbackDto);
        FeedbackStrategy strategy = switch (feedbackDto.getCategory()) {
            case 1 -> new GeneralFeedbackStrategy();
            case 2 -> new BugFeedbackStrategy();
            case 3 -> new FeatureFeedbackStrategy();
            default -> new OtherFeedbackStrategy();
        };

        String emailBody = strategy.generateAcknowledgment(feedback);
        CompletableFuture.runAsync(() -> {
            try {
                MessageDto messageDto = MessageDto
                        .builder()
                        .to(feedbackDto.getEmail())
                        .subject(strategy.generateAcknowledgmentSubject())
                        .message(emailBody).build();
                eCommSvcService.send(messageDto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return feedback;
    }

    @Override
    public Feedback createOrUpdateFeedback(FeedbackDto feedbackDto) {

        Feedback feedback = Feedback.builder()
                .feedbackId(UUID.randomUUID().toString())
                .name(feedbackDto.getName())
                .email(feedbackDto.getEmail())
                .category(feedbackDto.getCategory())
                .message(feedbackDto.getMessage())
                .createdDatetime(new Date().toInstant())
                .createdUser("system")
                .build();
        feedbackRepository.saveFeedback(feedback);
        return feedback;
    }

    @Override
    public Feedback getFeedbackById(String feedbackId) {
        return feedbackRepository.getFeedbackById(feedbackId);
    }

    @Override
    public void updateFeedback(FeedbackDto feedbackDto) {

        Feedback feedback = Feedback.builder()
                .feedbackId(feedbackDto.getFeedbackId())
                .name(feedbackDto.getName())
                .email(feedbackDto.getEmail())
                .category(feedbackDto.getCategory())
                .message(feedbackDto.getMessage())
                .build();
        feedbackRepository.updateFeedback(feedback);
    }

    @Override
    public void deleteFeedback(String feedbackId) {
        feedbackRepository.deleteFeedback(feedbackId);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.getAllFeedback();
    }

    @Override
    public List<Feedback> getFeedbackByDate(Date targetDate) {
        return feedbackRepository.getFeedbackByDate(targetDate.toInstant());
    }


}
