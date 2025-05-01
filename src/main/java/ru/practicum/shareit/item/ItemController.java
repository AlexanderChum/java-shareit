package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addNewItem(@Valid @RequestBody Item item,
                                              @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(itemService.addNewItem(item, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody Item item,
                                              @PathVariable @Positive Long id,
                                              @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(itemService.updateItem(item, id, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllOwnerItems(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(itemService.getAllOwnerItems(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> getAllBySearch(@RequestParam String text) {
        //сделай проверку на доступность вещей
        return ResponseEntity.ok(itemService.getItemsBySearch(text));
    }
}
