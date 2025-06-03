import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ShareItGateway;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.RequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constants.Const.REQUEST_HEADER_ID;

@WebMvcTest(RequestController.class)
@ContextConfiguration(classes = ShareItGateway.class)
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestClient requestClient;
    private ItemRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new ItemRequestDto("Test description");
    }

    @Test
    void addNewItemRequestShouldReturnOk() throws Exception {
        when(requestClient.addNewItemRequest(any(ItemRequestDto.class), anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/requests")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewItemRequestMissingHeaderShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserRequestsShouldReturnOk() throws Exception {
        when(requestClient.getUserRequests(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/requests")
                        .header(REQUEST_HEADER_ID, "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllItemRequestsShouldReturnOk() throws Exception {
        when(requestClient.getAllItemsRequests())
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/requests/all"))
                .andExpect(status().isOk());
    }

    @Test
    void getRequestByIdShouldReturnOk() throws Exception {
        when(requestClient.getRequestById(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/requests/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRequestByIdInvalidIdShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/requests/-1"))
                .andExpect(status().isBadRequest());
    }
}