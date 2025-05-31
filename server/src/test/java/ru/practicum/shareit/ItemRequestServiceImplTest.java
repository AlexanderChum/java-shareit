package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.ItemRequestServiceImpl;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    private User testUser = new User(1L, "Test User", "test@ya.ru");
    private ItemRequest testItemRequest = new ItemRequest(1L, "Test request", testUser, LocalDateTime.now(), null);

    private ItemRequestDto createTestRequestDto() {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);
        dto.setDescription("Test request");
        return dto;
    }

    @Test
    void addNewItemRequestValidDataShouldReturnDto() {
        ItemRequestDto expectedDto = createTestRequestDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(itemRequestRepository.save(any())).thenReturn(testItemRequest);
        when(itemRequestMapper.toItemRequestDto(any())).thenReturn(expectedDto);

        ItemRequestDto result = itemRequestService.addNewItemRequest(testItemRequest, testUser.getId());

        assertThat(result).isEqualTo(expectedDto);
        verify(itemRequestRepository).save(testItemRequest);
    }

    @Test
    void addNewItemRequestUserNotFoundShouldThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> itemRequestService.addNewItemRequest(testItemRequest, testUser.getId()));

        verify(itemRequestRepository, never()).save(any());
    }

    @Test
    void getRequestByIdExistingRequestShouldReturnDto() {
        ItemRequestDto expectedDto = createTestRequestDto();

        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(testItemRequest));
        when(itemRequestMapper.toItemRequestDto(any())).thenReturn(expectedDto);

        ItemRequestDto result = itemRequestService.getRequestById(testItemRequest.getId());

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getRequestByIdNonExistingRequestShouldThrowException() {
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> itemRequestService.getRequestById(999L));
    }

    @Test
    void getUserRequestsValidUserShouldReturnRequests() {
        ItemRequestDto dto = createTestRequestDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(itemRequestRepository.findByRequesterId(anyLong())).thenReturn(List.of(testItemRequest));
        when(itemRequestMapper.toListDto(any())).thenReturn(List.of(dto));

        List<ItemRequestDto> result = itemRequestService.getUserRequests(testUser.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(dto);
    }

    @Test
    void getUserRequestsUserNotFoundShouldThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> itemRequestService.getUserRequests(999L));

        verify(itemRequestRepository, never()).findByRequesterId(anyLong());
    }

    @Test
    void getAllRequestsShouldReturnAllRequests() {
        ItemRequestDto dto = createTestRequestDto();

        when(itemRequestRepository.findAll()).thenReturn(List.of(testItemRequest));
        when(itemRequestMapper.toListDto(any())).thenReturn(List.of(dto));

        List<ItemRequestDto> result = itemRequestService.getAllRequests();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(dto);
    }
}