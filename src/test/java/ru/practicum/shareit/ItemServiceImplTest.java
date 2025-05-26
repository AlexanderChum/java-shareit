package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.itemAndComment.ItemServiceImpl;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "ru.practicum.shareit")
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private UserRepository userRepository;

    private Item testItem;
    private ItemUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setName("Test Item");
        testItem.setDescription("Test Description");
        testItem.setAvailable(true);
        testItem.setUser(userRepository.findById(1L).orElseThrow());

        updateRequest = new ItemUpdateRequest();
        updateRequest.setName("Updated Name");
        updateRequest.setDescription("Updated Description");
        updateRequest.setAvailable(false);
    }

    @Test
    void addNewItemValidDataShouldReturnItemDto() {
        ItemDto savedItem = itemService.addNewItem(testItem, 1L);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    void updateItemValidDataShouldUpdateFields() {
        ItemDto updatedItem = itemService.updateItem(updateRequest, 1L, 1L);

        assertThat(updatedItem.getName()).isEqualTo("Updated Name");
        assertThat(updatedItem.getAvailable()).isFalse();
    }

    @Test
    void getItemByIdNonExistingIdShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> itemService.getItemById(999L));
    }

    @Test
    void getAllOwnerItemsShouldReturnTwoItems() {
        List<ItemDto> items = itemService.getAllOwnerItems(1L);
        assertThat(items).hasSize(2);
    }

    @Test
    void getItemsBySearchValidTextShouldReturnItems() {
        List<ItemDto> items = itemService.getItemsBySearch("Description1");
        assertThat(items).hasSize(1);
    }

    @Test
    void addNewCommentWithoutBookingShouldThrowValidationException() {
        Comment comment = new Comment();
        comment.setText("Test Comment");
        comment.setCreated(LocalDateTime.now());

        assertThrows(EntityNotFoundException.class,
                () -> itemService.addNewComment(comment, 1L, 999L));
    }
}