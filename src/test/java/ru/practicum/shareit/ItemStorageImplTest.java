package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.ItemStorageImpl;
import ru.practicum.shareit.item.model.Item;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemStorageImplTest {

    private ItemStorageImpl storage;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        storage = new ItemStorageImpl();

        item1 = new Item();
        item1.setName("TestItem1");
        item1.setDescription("TestDescription1");
        item1.setAvailable(true);
        item1.setOwnerId(1L);

        item2 = new Item();
        item2.setName("TestItem2");
        item2.setDescription("TestDescription2");
        item2.setAvailable(true);
        item2.setOwnerId(2L);
    }

    @Test
    void addNewItemShouldReturnItemId() {
        Long id = storage.addNewItem(item1);
        assertNotNull(id);
    }

    @Test
    void updateItemShouldUpdateFields() {
        Long addedId = storage.addNewItem(item1);

        item1.setName("UpdatedTestItem1");
        item1.setDescription("UpdatedTestDescription1");
        item1.setAvailable(false);

        Item updated = storage.updateItem(item1, addedId);

        assertEquals("UpdatedTestItem1", updated.getName());
        assertEquals("UpdatedTestDescription1", updated.getDescription());
        assertFalse(updated.getAvailable());
    }

    @Test
    void getItemByIdShouldReturnCorrectItemDto() {
        Long addedId = storage.addNewItem(item2);

        Item found = storage.getItemById(addedId)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не найден"));

        assertNotNull(found);
        assertEquals("TestItem2", found.getName());
        assertEquals("TestDescription2", found.getDescription());
    }

    @Test
    void getAllItemsShouldReturnAllAddedItems() {
        Long addedId = storage.addNewItem(item1);
        storage.addNewItem(item2);

        Map<Long, Item> all = storage.getAllItems();

        assertEquals(2, all.size());
        assertEquals("TestItem1", all.get(addedId).getName());
    }

    @Test
    void getAllOwnerItemsShouldReturnOnlyOwnerItems() {
        Long addedId = storage.addNewItem(item1);
        storage.addNewItem(item2);

        Map<Long, Item> ownerItems = storage.getAllOwnerItems(1L);

        assertEquals(1, ownerItems.size());
        assertEquals("TestItem1", ownerItems.get(addedId).getName());
    }

    @Test
    void getItemsBySearchShouldReturnMatchingItems() {
        Long addedId = storage.addNewItem(item1);
        storage.addNewItem(item2);

        Map<Long, Item> found = storage.getItemsBySearch("TESTITEM1");

        assertEquals(1, found.size());
        assertEquals("TestItem1", found.get(addedId).getName());
    }

    @Test
    void getItemsBySearchShouldReturnEmptyListForNoMatch() {
        storage.addNewItem(item1);

        Map<Long, Item> found = storage.getItemsBySearch("TestItem3");

        assertTrue(found.isEmpty());
    }

    @Test
    void getItemsBySearchShouldReturnEmptyListForBlankOrNull() {
        storage.addNewItem(item1);

        assertTrue(storage.getItemsBySearch("").isEmpty());
        assertTrue(storage.getItemsBySearch("   ").isEmpty());
        assertTrue(storage.getItemsBySearch(null).isEmpty());
    }
}
