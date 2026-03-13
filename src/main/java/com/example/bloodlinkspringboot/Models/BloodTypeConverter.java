package com.example.bloodlinkspringboot.Models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BloodTypeConverter implements AttributeConverter<BloodType, String> {

    @Override
    public String convertToDatabaseColumn(BloodType bloodType) {
        if (bloodType == null) return null;
        return bloodType.getLabel(); // Store as "A+", "O-", etc.
    }

    @Override
    public BloodType convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return BloodType.fromLabel(dbValue); // Map to A_POS, O_NEG, etc.
    }
}
