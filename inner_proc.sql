import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class DiffHandlerTest {
    private DiffHandler diffHandler;

    @Test
    public void initializeDiffHandler() {
        // Initialize the DiffHandler with attribute precisions for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Map<String, Integer> attributePrecisions = new HashMap<>();
        attributePrecisions.put("CASH.GROSS_AMT", 2);
        attributePrecisions.put("STREAM.NET_AMT", 3);

        diffHandler = new DiffHandler(attributePrecisions);
    }

    @Test
    public void testNumericComparisonWithDifferentPrecisions() {
        initializeDiffHandler();

        // Test numeric comparison with different precisions for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Object newValue1 = 10.12345; // A double with precision 5
        Object newValue2 = 10.1234567f; // A float with precision 7

        assertTrue(diffHandler.valuesMatch(newValue1, newValue2, "CASH", "GROSS_AMT"));
        assertTrue(diffHandler.valuesMatch(newValue1, newValue2, "STREAM", "NET_AMT"));
        assertFalse(diffHandler.valuesMatch(newValue1, newValue2, "CASH", "NET_AMT");
    }

    @Test
    public void testStringComparisonWithWhitespaces() {
        initializeDiffHandler();

        // Test string comparison with whitespaces for "CASH.GROSS_AMT"
        Object stringValue1 = "  10.1234500   ";
        Object stringValue2 = "  10.12345   ";

        assertTrue(diffHandler.valuesMatch(stringValue1, stringValue2, "CASH", "GROSS_AMT"));
    }

    @Test
    public void testDateComparisonWithDifferentDates() throws ParseException {
        initializeDiffHandler();

        // Test date comparison with different dates for "CASH.GROSS_AMT"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue1 = dateFormat.parse("2023-10-26");
        Date dateValue2 = dateFormat.parse("2023-10-27");

        assertFalse(diffHandler.valuesMatch(dateValue1, dateValue2, "CASH", "GROSS_AMT"));
    }

    @Test
    public void testNullValues() {
        initializeDiffHandler();

        // Test comparison with null values for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        assertTrue(diffHandler.valuesMatch(null, null, "CASH", "GROSS_AMT"));
        assertFalse(diffHandler.valuesMatch(10.0, null, "CASH", "GROSS_AMT"));
        assertTrue(diffHandler.valuesMatch(null, null, "STREAM", "NET_AMT"));
        assertFalse(diffHandler.valuesMatch(10.0, null, "STREAM", "NET_AMT"));
    }

    @Test
    public void testNumericStringComparisonWithDifferentPrecisions() {
        initializeDiffHandler();

        // Test numeric string comparison with different precisions for "CASH.GROSS_AMT"
        Object numericString1 = "10.12345"; // A numeric string
        Object numericString2 = "10.1234567"; // Another numeric string

        assertTrue(diffHandler.valuesMatch(numericString1, numericString2, "CASH", "GROSS_AMT"));
        assertFalse(diffHandler.valuesMatch(numericString1, numericString2, "STREAM", "NET_AMT"));
    }

    @Test
    public void testProcessRecordDiffInsert() {
        initializeDiffHandler();

        // Test processing of record insertion for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("GROSS_AMT", 10.12345);
        attributes.put("NET_AMT", 10.567); // A numeric value
        Set<String> keys = Set.of("issueID");

        TableRow newTableRow = new TableRow("CASH", attributes, keys);

        diffHandler.processRecordDiff(newTableRow, null);

        assertEquals(DMLOperationType.INSERT, newTableRow.getDmLOperationType());
        assertEquals(newTableRow.getAttributes(), newTableRow.getChangeAttributes());
        assertTrue(newTableRow.getExistingStateAttributes().isEmpty());
    }

    @Test
    public void testProcessRecordDiffInsertWithNull() {
        initializeDiffHandler();

        // Test processing of record insertion with null values for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("GROSS_AMT", null); // Null value
        attributes.put("NET_AMT", 10.567); // A numeric value
        Set<String> keys = Set.of("issueID");

        TableRow newTableRow = new TableRow("CASH", attributes, keys);

        diffHandler.processRecordDiff(newTableRow, null);

        assertEquals(DMLOperationType.INSERT, newTableRow.getDmLOperationType());
        assertEquals(newTableRow.getAttributes(), newTableRow.getChangeAttributes());
        assertTrue(newTableRow.getExistingStateAttributes().isEmpty());
    }

    @Test
    public void testProcessRecordDiffUpdate() {
        initializeDiffHandler();

        // Test processing of record update for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Map<String, Object> newAttributes = new HashMap<>();
        newAttributes.put("GROSS_AMT", 10.12345);
        newAttributes.put("NET_AMT", 10.567); // A numeric value
        Set<String> keys = Set of("issueID");

        TableRow newTableRow = new TableRow("CASH", newAttributes, keys, DMLOperationType.UPDATE);

        Map<String, Object> existingAttributes = new HashMap<>();
        existingAttributes.put("GROSS_AMT", 10.1234567);
        existingAttributes.put("NET_AMT", 10.567); // Same numeric value

        TableRow existingTableRow = new TableRow("CASH", existingAttributes, keys);

        diffHandler.processRecordDiff(newTableRow, existingTableRow);

        assertEquals(DMLOperationType.UPDATE, newTableRow.getDmLOperationType());
        assertEquals(newTableRow.getAttributes(), newTableRow.getChangeAttributes());
        assertEquals(existingTableRow.getAttributes(), newTableRow.getExistingStateAttributes());
    }

    @Test
    public void testProcessRecordDiffNoChange() {
        initializeDiffHandler();

        // Test processing of a record where no change occurred for "CASH.GROSS_AMT" and "STREAM.NET_AMT"
        Map<String, Object> newAttributes = new HashMap<>();
        newAttributes.put("GROSS_AMT", 10.12345);
        newAttributes.put("NET_AMT", 10.567); // A numeric value
        Set<String> keys = Set.of("issueID");

        TableRow newTableRow = new TableRow("CASH", newAttributes, keys, DMLOperationType.UPDATE);

        Map<String, Object> existingAttributes = new HashMap<>();
        existingAttributes.put("GROSS_AMT", 10.12345); // Same numeric value
        existingAttributes.put("NET_AMT", 10.567); // Same numeric value

        TableRow existingTableRow = new TableRow("CASH", existingAttributes, keys, DMLOperationType.UPDATE);

        diffHandler.processRecordDiff(newTableRow, existingTableRow);

        assertEquals(DMLOperationType.IGNORE, newTableRow.getDmLOperationType());
        assertTrue(newTableRow.getChangeAttributes().isEmpty());
        assertEquals(existingTableRow.getAttributes(), newTableRow.getExistingStateAttributes());
    }
}
