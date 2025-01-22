package com.qre.fbk.query.api.controller.impl;

import com.qre.fbk.dto.base.APIResponse;
import com.qre.fbk.dto.feedback.FeedbackDto;
import com.qre.fbk.query.api.controller.FeedbackController;
import com.qre.fbk.query.api.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackControllerImpl implements FeedbackController {
    private final Logger logger = LoggerFactory.getLogger(FeedbackControllerImpl.class);

    private final FeedbackService feedbackService;

    @PostMapping("/General")
    @Override
    public ResponseEntity<APIResponse> saveFeedback(@RequestBody FeedbackDto feedbackDto) {

        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.CREATED.value())) // Changed to CREATED for new resources
                .responseMsg("Feedback submitted successfully.")
                .responseData(feedbackService.processFeedback(feedbackDto))
                .build();

        logger.info("FeedbackControllerImpl.save Method - Feedback submitted: {}", feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<APIResponse> save(@RequestBody FeedbackDto feedbackDto) {

        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.CREATED.value())) // Changed to CREATED for new resources
                .responseMsg("Feedback submitted successfully.")
                .responseData(feedbackService.createOrUpdateFeedback(feedbackDto))
                .build();

        logger.info("FeedbackControllerImpl.save Method - Feedback submitted: {}", feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // Get all feedback (Read)
    @GetMapping
    public ResponseEntity<APIResponse> getAllFeedback() {
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .responseData(feedbackService.getAllFeedback())
                .build();

        logger.info("FeedbackControllerImpl.getAllFeedback Method");
        return ResponseEntity.ok(apiResponse);
    }

    // Get feedback by ID (Read)
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getFeedbackById(@PathVariable("id") String feedbackId) {

        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .responseData(feedbackService.getFeedbackById(feedbackId))
                .build();

        logger.info("FeedbackControllerImpl.getFeedbackById Method");
        return ResponseEntity.ok(apiResponse);
    }

    // Get feedback by date (Read)
    @GetMapping("/date")
    public ResponseEntity<APIResponse> getFeedbackByDate(@RequestParam("date") Date targetDate) {
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .responseData(feedbackService.getFeedbackByDate(targetDate))
                .build();

        logger.info("FeedbackControllerImpl.getFeedbackByDate Method");
        return ResponseEntity.ok(apiResponse);
    }

    // Update feedback (Update)
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateFeedback(@PathVariable("id") String feedbackId, @RequestBody FeedbackDto feedbackDto) {
        feedbackDto.setFeedbackId(feedbackId);
        feedbackService.updateFeedback(feedbackDto);

        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg("Feedback updated successfully.")
                .build();

        logger.info("FeedbackControllerImpl.updateFeedback Method");
        return ResponseEntity.ok(apiResponse);
    }

    // Delete feedback (Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteFeedback(@PathVariable("id") String feedbackId) {

        feedbackService.deleteFeedback(feedbackId);
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg("Feedback deleted successfully.")
                .build();

        logger.info("FeedbackControllerImpl.deleteFeedback Method");
        return ResponseEntity.ok(apiResponse);
    }
}