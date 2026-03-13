package com.example.bloodlinkspringboot.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodType {
    A_NEG("A-"), A_POS("A+"),
    B_NEG("B-"), B_POS("B+"),
    AB_NEG("AB-"), AB_POS("AB+"),
    O_NEG("O-"), O_POS("O+");

    private final String label;

    BloodType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    // Allows JSON string like "A+" to map to A_POS
    @JsonCreator
    public static BloodType fromLabel(String label) {
        for (BloodType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + label);
    }
}

