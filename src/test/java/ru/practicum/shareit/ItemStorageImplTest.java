package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.ItemStorageImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

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
    void addNewItemShouldReturnItemDtoWithIdAndCorrectFields() {
        ItemDto dto = storage.addNewItem(item1);

        assertNotNull(dto.getId());
        assertEquals("TestItem1", dto.getName());
        assertEquals("TestDescription1", dto.getDescription());
        assertTrue(dto.getAvailable());
    }

    @Test
    void updateItemShouldUpdateFields() {
        ItemDto added = storage.addNewItem(item1);

        item1.setName("UpdatedTestItem1");
        item1.setDescription("UpdatedTestDescription1");
        item1.setAvailable(false);

        ItemDto updated = storage.updateItem(item1, added.getId());

        assertEquals(added.getId(), updated.getId());
        assertEquals("UpdatedTestItem1", updated.getName());
        assertEquals("UpdatedTestDescription1", updated.getDescription());
        assertFalse(updated.getAvailable());
    }

    @Test
    void getItemByIdShouldReturnCorrectItemDto() {
        ItemDto added = storage.addNewItem(item2);

        ItemDto found = storage.getItemById(added.getId());

        assertNotNull(found);
        assertEquals(added.getId(), found.getId());
        assertEquals("TestItem2", found.getName());
        assertEquals("TestDescription2", found.getDescription());
    }

    @Test
    void getAllItemsShouldReturnAllAddedItems() {
        storage.addNewItem(item1);
        storage.addNewItem(item2);

        List<Item> all = storage.getAllItems();

        assertEquals(2, all.size());
        assertEquals("TestItem1", all.getFirst().getName());
    }

    @Test
    void getAllOwnerItemsShouldReturnOnlyOwnerItems() {
        storage.addNewItem(item1);
        storage.addNewItem(item2);

        List<Item> ownerItems = storage.getAllOwnerItems(1L);

        assertEquals(1, ownerItems.size());
        assertEquals("TestItem1", ownerItems.get(0).getName());
    }

    @Test
    void getItemsBySearchShouldReturnMatchingItems() {
        storage.addNewItem(item1);
        storage.addNewItem(item2);

        List<Item> found = storage.getItemsBySearch("TESTITEM1");

        assertEquals(1, found.size());
        assertEquals("TestItem1", found.get(0).getName());
    }

    @Test
    void getItemsBySearchShouldReturnEmptyListForNoMatch() {
        storage.addNewItem(item1);

        List<Item> found = storage.getItemsBySearch("TestItem3");

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
