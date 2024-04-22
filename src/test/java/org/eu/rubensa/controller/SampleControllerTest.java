package org.eu.rubensa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SampleController.class)
public class SampleControllerTest {
  @Autowired
  private MockMvc mockMvc;

  private static final String SAMPLE_PATH = "/sample";
  private String NEW_SAMPLE = """
      {
        "title": "New sample"
      }
      """;;

  @Test
  void testSetSample_shouldReturnNewId() throws Exception {
    // given:
    // new Sample
    Long newSampleId = 1L;
    String sample = NEW_SAMPLE;

    // when:
    // POST to SAMPLE_PATH
    mockMvc
        .perform(MockMvcRequestBuilders.post(SAMPLE_PATH)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_PROBLEM_JSON)
            .content(sample))
        // then:
        // 201 Created
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$").value(newSampleId));
  }

}
