package mapua.backend.controller;

import lombok.RequiredArgsConstructor;
import mapua.backend.entity.EquipmentType;
import mapua.backend.repository.EquipmentTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/equipment-types")
@RequiredArgsConstructor
public class EquipmentTypeController {

    private final EquipmentTypeRepository equipmentTypeRepository;

    @GetMapping
    public List<EquipmentType> getAllTypes() {
        return equipmentTypeRepository.findAll();
    }

    @PostMapping
    public EquipmentType addType(@RequestBody EquipmentType type) {
        return equipmentTypeRepository.save(type);
    }
}
