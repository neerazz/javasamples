package com.neeraj.preperation.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.neeraj.preperation.utils.JsonHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JpaJsonNode2JsonConverter implements AttributeConverter<JsonNode, String> {

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        return JsonHelper.serialize(attribute);
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        var type = new TypeReference<JsonNode>() {
        };
        return JsonHelper.fromJson(dbData, type);
    }
}
