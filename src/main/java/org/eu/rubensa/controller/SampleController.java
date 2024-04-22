package org.eu.rubensa.controller;

import org.eu.rubensa.openapi.api.SampleApi;
import org.eu.rubensa.openapi.model.SetSampleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class SampleController implements SampleApi {

  @Override
  public ResponseEntity<Long> setSample(@Valid SetSampleRequest setSampleRequest) {
    Long id = 1L;
    ResponseEntity<Long> returnValue = ResponseEntity.status(HttpStatus.CREATED).body(id);
    return returnValue;
  }

}
