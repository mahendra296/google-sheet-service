package com.sheet.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SheetService {
    
    private final SpreadSheetService spreadSheetService;

    public static String SPREADSHEET_ID = "1gzwwthu06MQ6oRW9pjd8mkpCSVjjdUUsiXmGVkRRInw";
    public static String SHEET_NAME = "Google Sheet";
    public static String APPLICATION_NAME = "Sheet Name";

    public void readSheet() {
        String range = String.format("%s%s", SHEET_NAME, "!A2:B");
        List<List<Object>> values = spreadSheetService.readGoogleSheetByRange(SPREADSHEET_ID, range, APPLICATION_NAME);
        if (!CollectionUtils.isEmpty(values)) {
            for (var value : values) {
                log.info("\nCategory Name : {} And Product Name : {}", value.get(0).toString(), value.get(1).toString());
            }
        } else {
            log.info("Google datasheet is empty.");
            deleteFromGoogleDataSheet();
        }
    }

    public void writeSheet() {
        deleteFromGoogleDataSheet();
        List<List<Object>> dataSheetRows = new ArrayList<>();
        dataSheetRows.add(List.of("Test", "ttt"));
        dataSheetRows.add(List.of("Test", "ttt1"));
        dataSheetRows.add(List.of("Test", "ttt2"));
        dataSheetRows.add(List.of("Test", "ttt3"));
        writeDataToGoogleDataSheet(dataSheetRows);
    }

    private void deleteFromGoogleDataSheet() {
        try {
            log.info("Invoke deleteFromGoogleSheet method.");
            Integer editId = 0;
            DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                    .setRange(new DimensionRange().setSheetId(editId).setDimension("ROWS").setStartIndex(1).setEndIndex(899));
            List<Request> requests = new ArrayList<>();
            requests.add(new Request().setDeleteDimension(deleteRequest));
            BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
            Sheets sheetService = spreadSheetService.getSheetService(APPLICATION_NAME);
            sheetService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
            log.info("End deleteFromGoogleSheet method.");
        } catch (Exception ex) {
            log.error("Exception while delete rows from google sheet.", ex);
        }
    }

    private void writeDataToGoogleDataSheet(List<List<Object>> collect) {
        try {
            log.info("Invoke writeDataToGoogleDataSheet method.");
            ValueRange body = new ValueRange().setValues(collect);
            Sheets sheetService = spreadSheetService.getSheetService(APPLICATION_NAME);
            sheetService.spreadsheets().values().append(SPREADSHEET_ID, SHEET_NAME, body).setValueInputOption("USER_ENTERED").execute();
            log.info("End writeDataToGoogleDataSheet method.");
        } catch (Exception ex) {
            log.error("Exception while write data  to google data sheet : ", ex);
            throw new RuntimeException("Exception while write data  to google data sheet.");
        }
    }
}
