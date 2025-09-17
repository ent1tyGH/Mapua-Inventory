package mapua.backend.controller;

import lombok.RequiredArgsConstructor;
import mapua.backend.entity.Item;
import mapua.backend.entity.EquipmentType;
import mapua.backend.service.ItemService;
import mapua.backend.service.EquipmentTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final EquipmentTypeService equipmentTypeService;

    // --- Items ---
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    // --- Equipment Types ---
    @GetMapping("/equipment-types")
    public List<EquipmentType> getAllTypes() {
        return equipmentTypeService.getAllTypes();
    }

    @PostMapping("/equipment-types")
    public EquipmentType addEquipmentType(@RequestBody EquipmentType type) {
        return equipmentTypeService.addType(type);
    }
}
