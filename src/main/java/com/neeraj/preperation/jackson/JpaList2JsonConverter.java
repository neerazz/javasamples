package com.neeraj.preperation.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wayfair.security.vms.aggregator.utils.JsonHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class JpaList2JsonConverter implements AttributeConverter<List, String> {

    @Override
    public String convertToDatabaseColumn(List attribute) {
        return JsonHelper.serialize(attribute);
    }

    @Override
    public List convertToEntityAttribute(String dbData) {
        var type = new TypeReference<List>() {
        };
        return JsonHelper.fromJson(dbData, type);
    }
}
