import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ShareItGateway;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@ContextConfiguration(classes = ShareItGateway.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingClient bookingClient;

    private BookingRequestDto validDto = new BookingRequestDto(1L, LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2));
    private BookingRequestDto invalidDto = new BookingRequestDto(1L, LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1));

    @Test
    void addNewBookingValidRequestReturnsOk() throws Exception {
        when(bookingClient.addNewBooking(anyLong(), any(BookingRequestDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewBookingInvalidStartReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewBookingMissingHeaderReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBookingValidRequestReturnsOk() throws Exception {
        when(bookingClient.updateBooking(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", "1")
                        .param("approved", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void getByBookingIdValidRequestReturnsOk() throws Exception {
        when(bookingClient.getBooking(anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOwnerBookingsValidRequestReturnsOk() throws Exception {
        when(bookingClient.getAllOwnerBookings(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUserBookingsValidRequestReturnsOk() throws Exception {
        when(bookingClient.getAllUserBookings(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUserBookingsMissingHeaderReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isBadRequest());
    }
}