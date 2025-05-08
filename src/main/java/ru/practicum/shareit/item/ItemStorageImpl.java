package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong keyCounter = new AtomicLong(0);

    public Long addNewItem(Item item) {
        Long id = generateUniqueKey();
        items.put(id, item);
        log.info("Предмет добавлен в мапу");
        return id;
    }

    public Item updateItem(Item item, Long id) {
        items.put(id, item);
        log.info("Предмет обновлен в мапе");
        return item;
    }

    public Optional<Item> getItemById(Long id) {
        log.info("Получен запрос в репозиторий на получение предмета по id");
        return Optional.ofNullable(items.get(id));
    }

    public Map<Long, Item> getAllItems() {
        log.info("Получен запрос в репозиторий на получение всех предметов");
        return new HashMap<>(items);
    }

    public Map<Long, Item> getAllOwnerItems(Long userId) {
        log.info("Получен запрос в репозиторий на получение всех предметов пользователя");
        return items.entrySet().stream()
                .filter(entry -> entry.getValue().getOwnerId().equals(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Item> getItemsBySearch(String text) {
        if (text == null || text.isBlank()) {
            log.info("Получен пустой текст");
            return Map.of();
        }

        final String cleanKeyword = text.trim().toLowerCase();

        log.info("Получен запрос в репозиторий на получение предметов по поиску");

        Map<Long, Item> result = new HashMap<>();
        for (Long id : items.keySet()) {
            if ((containsIgnoreCase(items.get(id).getName(), cleanKeyword) ||
                    containsIgnoreCase(items.get(id).getDescription(), cleanKeyword)) &&
                    items.get(id).getAvailable().equals(true)) result.put(id, items.get(id));
        }
        return result;
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
