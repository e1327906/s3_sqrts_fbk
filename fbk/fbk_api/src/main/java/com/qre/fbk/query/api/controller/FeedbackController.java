package com.qre.fbk.query.api.controller;

import com.qre.fbk.dto.base.APIResponse;
import com.qre.fbk.dto.feedback.FeedbackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface FeedbackController {

    ResponseEntity<APIResponse> saveFeedback(@RequestBody FeedbackDto feedbackDto);

}
