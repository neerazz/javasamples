package com.neeraj.preperation.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neeraj.preperation.spring.exceptions.JSONConversionException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class JsonHelper {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Convert a String to JsonNode.
     *
     * @param data Value to convert in Json.
     * @return to serialize object into a JsonNode
     */
    public static JsonNode toJson(String data) throws JsonProcessingException {
        return objectMapper.readTree(data);
    }

    /**
     * Convert an object to JsonNode.
     *
     * @param data Value to convert in Json.
     * @return to serialize object into a JsonNode
     */
    public static JsonNode toJson(Object data) {
        try {
            return objectMapper.valueToTree(data);
        } catch (Exception e) {
            throw new JSONConversionException(data, e);
        }
    }

    public static Map<String, Object> toMap(Object data) {
        try {
            return objectMapper.convertValue(data, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new JSONConversionException(data, e);
        }
    }


    /**
     * Convert a JSON tree to string.
     *
     * @param json JsonNode to serialize to String.
     * @return the serialized JSON string
     */
    public static String serialize(JsonNode json) {
        try {
            StringWriter writer = new StringWriter();
            final JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(writer);
            objectMapper.writeTree(jsonGenerator, json);
            return writer.toString();
        } catch (Exception e) {
            throw new JSONConversionException(json, e);
        }
    }

    /**
     * Convert a JSON tree to string.
     *
     * @param object Object to serialize to String.
     * @return the serialized JSON string
     */
    public static String serialize(Object object) {
        try {
            StringWriter writer = new StringWriter();
            final JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(writer);
            var json = toJson(object);
            objectMapper.writeTree(jsonGenerator, json);
            return writer.toString();
        } catch (Exception e) {
            throw new JSONConversionException(object, e);
        }
    }


    /**
     * Convert a JsonNode to a Java value
     *
     * @param <A>   the type parameter
     * @param json  Json value to convert.
     * @param clazz Expected Java value type.
     * @return the deserialized Java model
     */
    public static <A> A fromJson(JsonNode json, Class<A> clazz) {
        try {
            return objectMapper.treeToValue(json, clazz);
        } catch (Exception e) {
            throw new JSONConversionException(json, e, clazz);
        }
    }


    /**
     * Convert a JsonNode to a Java value
     * Example:   fromJson( jsonNode, new TypeReference&lt;ListModel&lt;MyModel&gt;&gt;() {} )
     *
     * @param <A>          the type parameter
     * @param json         Json value to convert.
     * @param valueTypeRef Expected Java value type.
     * @return the deserialized Java model
     */
    public static <A> A fromJson(JsonNode json, TypeReference<A> valueTypeRef) {
        try {
            return objectMapper.readValue(json.traverse(), valueTypeRef);
        } catch (Exception e) {
            throw new JSONConversionException(json, e, valueTypeRef);
        }
    }

    public static JsonNode fromJson(Path filePath) {
        log.debug("Converting the Path: {} to Json.", filePath);
        try {
            return objectMapper.readTree(filePath.toFile());
        } catch (Exception e) {
            throw new JSONConversionException(filePath, e);
        }
    }

    public static <A> A fromJson(Path filePath, TypeReference<A> valueTypeRef) {
        return fromJson(fromJson(filePath), valueTypeRef);
    }

    public static <A> A fromJson(Path filePath, TypeReference<A> valueTypeRef, String... fieldNames) {
        return fromJson(fromJson(filePath), valueTypeRef, fieldNames);
    }

    /**
     * Convert a Json string to a Java value
     *
     * @param <A>   the type parameter
     * @param json  Json string to convert.
     * @param clazz Expected Java value type.
     * @return the deserialized Java model
     */
    public static <A> A fromJson(String json, Class<A> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new JSONConversionException(json, e, clazz);
        }
    }


    /**
     * Convert a JsonNode to a Java value. Use this to deserialize into a generic type
     * Example:   fromJson( json, new TypeReference&lt;ListModel&lt;MyModel&gt;&gt;() {} )
     *
     * @param <A>          the type parameter
     * @param json         Json value to convert.
     * @param valueTypeRef Expected Java value type.
     * @return the deserialized Java model
     */
    public static <A> A fromJson(String json, TypeReference<A> valueTypeRef) {
        try {
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new JSONConversionException(json, e, valueTypeRef);
        }
    }

    public static <A> A fromJson(JsonNode jsonNode, TypeReference<A> valueTypeRef, String... fieldNames) {
        try {
            JsonNode currentNode = jsonNode;
            for (var fieldName : fieldNames) {
                currentNode = currentNode.get(fieldName);
                if (currentNode == null) break;
            }
            return fromJson(currentNode, valueTypeRef);
        } catch (Exception e) {
            throw new JSONConversionException(jsonNode, e, valueTypeRef, fieldNames);
        }
    }

    public static <A> A fromJson(String json, TypeReference<A> valueTypeRef, String... fieldNames) {
        try {
            JsonNode jsonNode = toJson(json);
            return fromJson(jsonNode, valueTypeRef, fieldNames);
        } catch (Exception e) {
            throw new JSONConversionException(json, e, valueTypeRef, fieldNames);
        }
    }


    /**
     * Creates a new empty ObjectNode.
     *
     * @return the ObjectNode
     */
    public static ObjectNode newObject() {
        return objectMapper.createObjectNode();
    }

    /**
     * Convert a JsonNode to its string representation.
     *
     * @param json the json
     * @return the serialized JSON string
     */
    public static String stringify(JsonNode json) {
        return json.toString();
    }

    public static String stringify(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONConversionException(object, e);
        }
    }

    /**
     * Parse a InputStream representing a json, and return it as a JsonNode.
     *
     * @param src the src
     * @return the deserialized src stream into a JsonNode
     */
    public static JsonNode parse(java.io.InputStream src) {
        try {
            return objectMapper.readValue(src, JsonNode.class);
        } catch (IOException e) {
            throw new JSONConversionException(src, e);
        }
    }

    public static JsonNode getJsonNodeProperty(JsonNode node, String... keys) {
        JsonNode currentNode = node;
        if (currentNode == null) return null;
        for (String key : keys) {
            if (currentNode == null) break;
            currentNode = currentNode.get(key);
        }
        return currentNode;
    }

    public static String getNodeProperty(JsonNode node, String... keys) {
        JsonNode currentNode = getJsonNodeProperty(node, keys);
        if (currentNode == null) return null;
        return currentNode.asText(null);
    }

    public static <A> A getNodeProperty(JsonNode node, Class<A> clazz, String... keys) {
        JsonNode currentNode = getJsonNodeProperty(node, keys);
        if (currentNode == null) return null;
        return fromJson(currentNode, clazz);
    }

    public static <A> A getNodeProperty(JsonNode node, TypeReference<A> valueTypeRef, String... keys) {
        JsonNode currentNode = getJsonNodeProperty(node, keys);
        if (currentNode == null) return null;
        return fromJson(currentNode, valueTypeRef);
    }

    /**
     * Creates a new empty ArrayNode.
     *
     * @return the ArrayNode
     */
    public ArrayNode newArrayNode() {
        return objectMapper.createArrayNode();
    }

    /**
     * Parse a String representing a json, and return it as a JsonNode.
     *
     * @param src the src
     * @return the deserialized src string into a JsonNode
     */
    public JsonNode parse(String src) {
        try {
            return objectMapper.readValue(src, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new JSONConversionException(src, e);
        }
    }
}