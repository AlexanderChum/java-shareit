package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.itemAndComment.CommentRepository;
import ru.practicum.shareit.itemAndComment.ItemMapper;
import ru.practicum.shareit.itemAndComment.ItemRepository;
import ru.practicum.shareit.itemAndComment.ItemServiceImpl;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;
import ru.practicum.shareit.itemAndComment.model.Item;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User owner;
    private Item testItem;
    private ItemDto testItemDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        owner = new User(1L, "Owner", "owner@ya.ru");
        testItem = new Item(1L, "Test Item", "Test Description", true, null,
                owner, new ItemRequest());
        testItemDto = new ItemDto(1L, "Test Item", "Test Description", true, null, null, null);
        testUser = new User(2L, "Test User", "test@ya.ru");
    }

    private ItemUpdateRequest createTestUpdateRequest() {
        ItemUpdateRequest request = new ItemUpdateRequest();
        request.setName("Updated Name");
        request.setDescription("Updated Description");
        request.setAvailable(false);
        return request;
    }

    private Comment createTestComment() {
        Comment comment = new Comment();
        comment.setText("Test Comment");
        comment.setCreated(LocalDateTime.now());
        return comment;
    }

    @Test
    void updateItemValidDataShouldUpdateFields() {
        ItemUpdateRequest updateRequest = createTestUpdateRequest();
        testItem.setName("Updated Name");
        testItem.setAvailable(false);
        testItemDto.setName("Updated Name");
        testItemDto.setAvailable(false);

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(testItem));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemMapper.updateItemFromRequest(any(), any())).thenReturn(testItem);
        when(itemMapper.toItemDto(any())).thenReturn(testItemDto);

        ItemDto result = itemService.updateItem(updateRequest, testItem.getId(), owner.getId());

        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getAvailable()).isFalse();
    }

    @Test
    void getItemByIdNonExistingIdShouldThrowException() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.getItemById(999L));
    }

    @Test
    void getAllOwnerItemsShouldReturnItems() {
        when(itemRepository.findByUserId(anyLong())).thenReturn(List.of(testItem));
        when(itemMapper.toItemDtoList(any())).thenReturn(List.of(testItemDto));

        List<ItemDto> result = itemService.getAllOwnerItems(owner.getId());

        assertThat(result).hasSize(1);
    }

    @Test
    void getItemsBySearchValidTextShouldReturnItems() {
        when(itemRepository.findItemsByTextSearch(anyString())).thenReturn(List.of(testItem));
        when(itemMapper.toItemDtoList(any())).thenReturn(List.of(testItemDto));

        List<ItemDto> result = itemService.getItemsBySearch("Description");

        assertThat(result).hasSize(1);
    }

    @Test
    void addNewCommentWithoutBookingShouldThrowValidationException() {
        Comment comment = createTestComment();
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(testItem));
        when(bookingRepository.findLastBookingEndByItemId(anyLong()))
                .thenReturn(pastDate);
        when(bookingRepository.existsByBookerIdAndItemIdAndStatus(anyLong(), anyLong(), any()))
                .thenReturn(false);

        assertThrows(ValidationException.class,
                () -> itemService.addNewComment(comment, testItem.getId(), testUser.getId()));

        verify(commentRepository, never()).save(any());
    }
}