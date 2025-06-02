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
import ru.practicum.shareit.itemAndComment.ItemClient;
import ru.practicum.shareit.itemAndComment.ItemController;
import ru.practicum.shareit.itemAndComment.dto.CommentRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.user.dto.UserRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constants.Const.REQUEST_HEADER_ID;

@WebMvcTest(ItemController.class)
@ContextConfiguration(classes = ShareItGateway.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemClient itemClient;

    private ItemRequestDto validItemDto;
    private ItemUpdateRequestDto validUpdateDto;
    private CommentRequestDto validCommentDto;

    @BeforeEach
    void setUp() {
        validItemDto = new ItemRequestDto("Test name", "Test Description", true, null);
        validUpdateDto = new ItemUpdateRequestDto(1L, new UserRequestDto(), "Test name",
                "Test description", false);
        validCommentDto = new CommentRequestDto("Test text");
    }

    @Test
    void addNewItemValidRequestReturnsOk() throws Exception {
        when(itemClient.addNewItem(any(ItemRequestDto.class), anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/items")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validItemDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewItemMissingNameReturnsBadRequest() throws Exception {
        ItemRequestDto invalidDto = new ItemRequestDto();
        invalidDto.setDescription("Description, no name");
        invalidDto.setAvailable(true);

        mockMvc.perform(post("/items")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateItemValidRequestReturnsOk() throws Exception {
        when(itemClient.updateItem(any(ItemUpdateRequestDto.class), anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(patch("/items/1")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getItemByIdValidIdReturnsOk() throws Exception {
        when(itemClient.getItemById(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOwnerItemsValidRequestReturnsOk() throws Exception {
        when(itemClient.getAllOwnerItems(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/items")
                        .header(REQUEST_HEADER_ID, "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBySearchValidTextReturnsOk() throws Exception {
        when(itemClient.getAllBySearch(anyString()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/items/search")
                        .param("text", "дрель"))
                .andExpect(status().isOk());
    }

    @Test
    void addNewCommentValidRequestReturnsOk() throws Exception {
        when(itemClient.addNewComment(any(CommentRequestDto.class), anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/items/1/comment")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCommentDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewCommentInvalidTextLengthReturnsBadRequest() throws Exception {
        CommentRequestDto invalidDto = new CommentRequestDto();
        invalidDto.setText("a".repeat(501)); // Превышает максимальную длину

        mockMvc.perform(post("/items/1/comment")
                        .header(REQUEST_HEADER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewCommentMissingUserIdHeaderReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/items/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCommentDto)))
                .andExpect(status().isBadRequest());
    }
}
