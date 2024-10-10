package com.example.demo.validators;

import com.example.demo.models.enums.Material;

public class MaterialValidator {

    public static boolean esMaterialValido(String material) {
        try {
            Material.valueOf(material.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}