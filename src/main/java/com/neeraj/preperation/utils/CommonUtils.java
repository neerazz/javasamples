package com.neeraj.preperation.utils;

import com.wayfair.security.vms.aggregator.model.avro.*;
import com.wayfair.security.vms.aggregator.model.entity.*;
import com.wayfair.security.vms.aggregator.model.entity.VMSControlSubCategories;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaUtils;
import org.apache.avro.Schema;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
    private CommonUtils() {
    }

    public static String getSHA256Hash(Object... inputs) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Object input : inputs) {
                if (input == null) continue;
                sb.append(input);
            }
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            var encodedHash = digest.digest(bytes);
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getSchema(Object inputObj) {
        Schema schema = AvroSchemaUtils.getSchema(inputObj, true, true, true);
        return schema.toString();
    }

    public static void main(String[] args) {
        var vmsControls = getSchema(new VMSControls());
        var vmsControlSubCategories = getSchema(new VMSControlSubCategories());
        var vmsControlFindings = getSchema(new VMSControlFindings());
        var vmsVulnerabilities = getSchema(new VMSVulnerabilityEntity());
        var findings = getSchema(new VMSVulnerabilityFindingEntity());
        var vmsIssues = getSchema(new VMSIssues());
        System.out.println("VMSControls = " + vmsControls);
        System.out.println("VMSControlSubCategories = " + vmsControlSubCategories);
        System.out.println("VMSControlFindings = " + vmsControlFindings);
        System.out.println("VMSVulnerabilityEntity = " + vmsVulnerabilities);
        System.out.println("VMSVulnerabilityFindingEntity = " + findings);
        System.out.println("VMSIssues = " + vmsIssues);
        System.out.println(getSchema(new VMSControlsAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSControlSubCategoriesAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSControlFindingsAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSVulnerabilityAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSVulnerabilityFindingAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSControlFindingsAvro()).replace("\"", "\\" + "\""));
        System.out.println(getSchema(new VMSIssuesAvro()).replace("\"", "\\" + "\""));
    }
}
