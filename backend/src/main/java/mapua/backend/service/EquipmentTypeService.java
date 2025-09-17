package mapua.backend.service;

import mapua.backend.entity.EquipmentType;
import mapua.backend.repository.EquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;

    public List<EquipmentType> getAllTypes() {
        return equipmentTypeRepository.findAll();
    }

    public EquipmentType addType(EquipmentType type) {
        if (equipmentTypeRepository.existsByName(type.getName())) {
            throw new RuntimeException("Equipment type already exists: " + type.getName());
        }
        return equipmentTypeRepository.save(type);
    }

    public EquipmentType getTypeById(Long id) {
        return equipmentTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment type not found with id: " + id));
    }
}
