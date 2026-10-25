package com.example.playwrightbdd.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CsvTestData {
    private static final String FILE =
            "src/test/resources/testdata/registerdata.csv";

    private CsvTestData() {}

    public static Map<String, String> readScenario(String scenarioKey,String csvFileLocation)
            throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(csvFileLocation));
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                if (scenarioKey.equals(record.get("TestcaseNo"))) {
                    Map<String, String> row = new LinkedHashMap<>();
                    for (String header : parser.getHeaderNames()) {
                        row.put(header, record.get(header));
                    }
                    return Map.copyOf(row);
                }
            }
        }

        throw new IllegalArgumentException(
                "No CSV test data row found for key: " + scenarioKey);
    }
}
