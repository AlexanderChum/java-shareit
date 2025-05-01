package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private AtomicLong keyCounter = new AtomicLong(0);

    public ItemDto addNewItem(Item item) {
        Long id = generateUniqueKey();
        items.put(id, item);
        return new ItemDto(id, item.getName(), item.getDescription(), item.getAvailable());
    }

    public ItemDto updateItem(Item item, Long id) {
        items.put(id, item);
        return new ItemDto(id, item.getName(), item.getDescription(), item.getAvailable());
    }

    public ItemDto getItemById(Long id) {
        return new ItemDto(id, items.get(id).getName(), items.get(id).getDescription(), items.get(id).getAvailable());
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public List<Item> getAllOwnerItems(Long userId) {
        return getAllItems().stream()
                .filter(item -> userId.equals(item.getOwnerId()))
                .collect(Collectors.toList());
    }

    public List<Item> getItemsBySearch(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        final String cleanKeyword = text.trim().toLowerCase();

        return items.values().stream()
                .filter(entity -> containsIgnoreCase(entity.getName(), cleanKeyword)
                        || containsIgnoreCase(entity.getDescription(), cleanKeyword))
                .filter(entity -> entity.getAvailable().equals(true))
                .collect(Collectors.toList());
    }

    private boolean containsIgnoreCase(String source, String text) {
        if (source == null || text == null) {
            return false;
        }
        return source.toLowerCase().contains(text);
    }

    private Long generateUniqueKey() {
        return keyCounter.incrementAndGet();
    }
}
