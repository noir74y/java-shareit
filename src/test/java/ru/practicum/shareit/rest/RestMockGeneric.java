package ru.practicum.shareit.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.utils.AppConstants;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class RestMockGeneric<DtoIn, DtoOut> {
    @Getter
    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private String dtoRespJsonString;

    public RestMockGeneric() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public DtoOut post(String url, DtoIn dtoReq, Class<DtoOut> outputClass, Integer... requestorId) throws Exception {
        dtoRespJsonString = mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : "")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public DtoOut patch(String url, DtoIn dtoReq, Class<DtoOut> outputClass, Integer... requestorId) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.patch(url)
                        .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public DtoOut patch(String url, Class<DtoOut> outputClass, Integer... requestorId) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.patch(url)
                        .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public void delete(String url, Integer... requestorId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : ""));
    }

    public DtoOut get(String url, Class<DtoOut> outputClass, Integer... requestorId) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }

    public String get(String url, Integer... requestorId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header(AppConstants.HEADER_USER_ID, requestorId.length != 0 ? requestorId[0] : "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    public DtoOut get(String url, int httpResponseCode, Class<DtoOut> outputClass) throws Exception {
        dtoRespJsonString = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(httpResponseCode))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(dtoRespJsonString, outputClass);
    }
}