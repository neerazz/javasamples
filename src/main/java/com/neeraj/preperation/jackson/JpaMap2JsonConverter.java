package com.neeraj.preperation.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wayfair.security.vms.aggregator.utils.JsonHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter(autoApply = true)
public class JpaMap2JsonConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        return JsonHelper.serialize(attribute);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        var type = new TypeReference<Map<String, Object>>() {
        };
        return JsonHelper.fromJson(dbData, type);
    }
}
