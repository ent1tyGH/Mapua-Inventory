package mapua.backend.controller;

import lombok.RequiredArgsConstructor;
import mapua.backend.entity.Item;
import mapua.backend.entity.EquipmentType;
import mapua.backend.service.ItemService;
import mapua.backend.service.EquipmentTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // Used for returning 404/200 status codes

import java.util.List;
import java.util.Optional; // Still needed for the equipmentTypeService check

@RestController
@RequestMapping("api/items")
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

    /**
     * ✅ FIX: Added the missing @PutMapping endpoint for item update.
     * This uses ResponseEntity to return 404 if the item is not found (assuming getItemById can throw or return null).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        // Assuming getItemById(id) returns an Item or throws/returns null if not found.
        Item existingItem;
        try {
            existingItem = itemService.getItemById(id);
        } catch (Exception e) {
            // Handle cases where service throws an exception (e.g., ItemNotFoundException)
            return ResponseEntity.notFound().build();
        }

        if (existingItem == null) {
            return ResponseEntity.notFound().build();
        }

        // 1. Update core properties from the request body
        existingItem.setSpecifications(itemDetails.getSpecifications());
        existingItem.setLocation(itemDetails.getLocation());
        existingItem.setConditionStatus(itemDetails.getConditionStatus());

        // 2. Update EquipmentType (by ID) - Must still use Optional for best practice lookup
        if (itemDetails.getEquipmentType() != null && itemDetails.getEquipmentType().getId() != null) {
            // Assuming getTypeById returns Optional<EquipmentType>
            Optional<EquipmentType> typeOptional = Optional.ofNullable(equipmentTypeService.getTypeById(itemDetails.getEquipmentType().getId()));
            typeOptional.ifPresent(existingItem::setEquipmentType);
        }

        // 3. Save the updated item
        Item updatedItem = itemService.addItem(existingItem);
        return ResponseEntity.ok(updatedItem);
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

    /**
     * FIX: Borrow item is updated to return ResponseEntity for robust error handling.
     */
    @PutMapping("/{id}/borrow")
    public ResponseEntity<Item> borrowItem(@PathVariable Long id) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        item.setBorrowed(true);
        Item updatedItem = itemService.addItem(item);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * FIX: Return item is updated to return ResponseEntity for robust error handling.
     */
    @PutMapping("/{id}/return")
    public ResponseEntity<Item> returnItem(@PathVariable Long id) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        item.setBorrowed(false);
        Item updatedItem = itemService.addItem(item);
        return ResponseEntity.ok(updatedItem);
    }
}