package mapua.backend.controller;

import lombok.RequiredArgsConstructor;
import mapua.backend.entity.*;
import mapua.backend.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    // --- Items ---
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // --- Equipment Types ---
    @GetMapping("/equipment-types")
    public List<EquipmentType> getAllTypes() {
        return equipmentTypeRepository.findAll();
    }

    @PostMapping("/equipment-types")
    public EquipmentType addEquipmentType(@RequestBody EquipmentType type) {
        if (!equipmentTypeRepository.existsByName(type.getName())) {
            return equipmentTypeRepository.save(type);
        }
        throw new RuntimeException("Equipment type already exists.");
    }
}
