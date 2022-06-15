package com.example.validation.list

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(ListValidationController::class)
internal class ListValidationControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @Test
    internal fun `should accept request with valid objects`() {
        mockMvc.perform(
            post("/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""[{"name":"abc"}]""")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("list success"))
    }

    @Test
    internal fun `should reject request with invalid objects`() {
        mockMvc.perform(
            post("/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""[{"name":"abcdefg"}]""")
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().string("validateListObjects.listRequest[0].name: name must be 2 to 4 chars"))
    }
}
