package ru.practicum.shareit.mock;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class RestMockGeneric<Input, Output> {
    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private String dtoRespJsonString;

    public RestMockGeneric() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Output post(String url, Input dtoReq, Class<Output> outputClass) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public Output patch(String url, Input dtoReq, Class<Output> outputClass) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.patch(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public void deleteEntity(String url) throws Exception {
        mockMvc.perform(delete(url));
    }

    public Output get(String url, Class<Output> outputClass) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public Output get(String url, int httpResponseCode, Class<Output> outputClass) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(httpResponseCode))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public String list(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    }
}