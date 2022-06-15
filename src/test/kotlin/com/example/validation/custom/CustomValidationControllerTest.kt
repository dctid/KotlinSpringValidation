package com.example.validation.custom

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

const val ACCEPTABLE_PROPERTY="acceptable"

@SpringBootTest(properties = ["validation.request.value=$ACCEPTABLE_PROPERTY"])
@AutoConfigureMockMvc
internal class CustomValidationControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @Test
    internal fun `should accept request with valid objects`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/custom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"value":"$ACCEPTABLE_PROPERTY"}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("custom success"))

    }

    @Test
    internal fun `should accept request with null value in objects`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/custom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"value":null}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("custom success"))
    }

    @Test
    internal fun `should reject request with invalid objects`() {
       mockMvc.perform(
            MockMvcRequestBuilders.post("/custom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"value":""}""")
        )
            .andExpect(status().isBadRequest)
            .andExpect(status().reason(containsString("Invalid request content.")))
    }
}