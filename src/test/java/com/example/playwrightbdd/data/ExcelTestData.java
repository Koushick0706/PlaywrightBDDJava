package com.example.playwrightbdd.data;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class ExcelTestData {
    private static final String SHEET_NAME = "AccountAccess";
    private static final String WORKBOOK_PATH_PROPERTY = "testdata.file";
    private static final String DEFAULT_WORKBOOK_PATH = "src/test/resources/testdata/account-access.xlsx";
    private static final Set<String> DATA_COLUMNS = Set.of(
            "firstName", "lastName", "email", "phone", "password", "occupation", "gender");

    private ExcelTestData() {
    }

    public static Map<String, String> readScenario(String scenarioKey) {
        if (scenarioKey == null || scenarioKey.isBlank()) {
            throw new IllegalArgumentException("Excel scenario key must not be blank");
        }

        Path filloWorkbook = null;
        try {
            filloWorkbook = createFilloCompatibleCopy();
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(filloWorkbook.toString());
            try {
                String query = "SELECT * FROM " + SHEET_NAME + " WHERE scenarioKey='" + sqlText(scenarioKey) + "'";
                Recordset recordset = connection.executeQuery(query);
                try {
                    if (recordset.next()) {
                        return readDataRow(recordset);
                    }
                } finally {
                    recordset.close();
                }
            } finally {
                connection.close();
            }
        } catch (FilloException | IOException e) {
            throw new IllegalStateException("Could not read Excel test data for scenario key: " + scenarioKey, e);
        } finally {
            deleteTemporaryWorkbook(filloWorkbook);
        }

        throw new IllegalArgumentException("No Excel test data row found for scenario key: " + scenarioKey);
    }

    public static void updateScenarioValue(String scenarioKey, String columnName, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Excel test data value must not be null");
        }
        String writableColumn = DATA_COLUMNS.stream()
                .filter(column -> column.equalsIgnoreCase(columnName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported Excel test data column: " + columnName));


        readScenario(scenarioKey);
        String query = "UPDATE " + SHEET_NAME + " SET " + writableColumn + "='" + sqlText(value)
                + "' WHERE scenarioKey='" + sqlText(scenarioKey) + "'";
        Path originalWorkbook = workbookPath();
        Path filloWorkbook = null;
        try {
            filloWorkbook = createFilloCompatibleCopy();
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(filloWorkbook.toString());
            try {
                connection.executeUpdate(query);
            } finally {
                connection.close();
            }
            Files.copy(filloWorkbook, originalWorkbook, StandardCopyOption.REPLACE_EXISTING);
        } catch (FilloException | IOException e) {
            throw new IllegalStateException("Could not update Excel test data column " + writableColumn
                    + " for scenario key: " + scenarioKey, e);
        } finally {
            deleteTemporaryWorkbook(filloWorkbook);
        }
    }

    private static Map<String, String> readDataRow(Recordset recordset) throws FilloException {
        Map<String, String> values = new LinkedHashMap<>();
        for (String column : DATA_COLUMNS) {
            String value = recordset.getField(column);
            values.put(column, value == null ? "" : value.trim());
        }
        return Map.copyOf(values);
    }

    private static Path workbookPath() {
        Path configuredPath = Path.of(System.getProperty(WORKBOOK_PATH_PROPERTY, DEFAULT_WORKBOOK_PATH)).normalize();
        if (configuredPath.isAbsolute()) {
            return requireWorkbook(configuredPath);
        }

        Path workingDirectory = Path.of("").toAbsolutePath().normalize();
        for (Path directory = workingDirectory; directory != null; directory = directory.getParent()) {
            Path candidate = directory.resolve(configuredPath).normalize();
            if (Files.isRegularFile(candidate)) {
                return workingDirectory.relativize(candidate);
            }
        }

        throw new IllegalStateException("Excel test data workbook not found at relative path " + configuredPath
                + " from working directory " + workingDirectory + ". Set -D" + WORKBOOK_PATH_PROPERTY
                + " to a valid workbook path.");
    }

    private static Path requireWorkbook(Path path) {
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("Excel test data workbook not found: " + path);
        }
        return path;
    }

    private static Path createFilloCompatibleCopy() throws IOException {
        Path source = workbookPath();
        Path temporaryWorkbook = Files.createTempFile("account-access-", ".xlsm");
        try {
            Files.copy(source, temporaryWorkbook, StandardCopyOption.REPLACE_EXISTING);
            return temporaryWorkbook;
        } catch (IOException e) {
            Files.deleteIfExists(temporaryWorkbook);
            throw e;
        }
    }

    private static void deleteTemporaryWorkbook(Path temporaryWorkbook) {
        if (temporaryWorkbook == null) {
            return;
        }
        try {
            Files.deleteIfExists(temporaryWorkbook);
        } catch (IOException e) {
            throw new IllegalStateException("Could not remove temporary Excel workbook: " + temporaryWorkbook, e);
        }
    }

    private static String sqlText(String text) {
        return text.replace("'", "''");
    }
}
