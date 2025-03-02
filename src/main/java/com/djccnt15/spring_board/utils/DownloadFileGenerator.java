package com.djccnt15.spring_board.utils;

import com.djccnt15.spring_board.annotations.TableHeader;
import com.djccnt15.spring_board.exception.ApiInternalServerException;
import com.djccnt15.spring_board.utils.model.ExcelCoverData;
import com.djccnt15.spring_board.utils.model.ExcelTableSheetData;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public final class DownloadFileGenerator {
    
    public static <T> byte[] generateCsv(List<T> records, Class<T> type) {
        var out = new ByteArrayOutputStream();
        var format = CSVFormat.Builder.create()
            .setHeader(getFieldNames(type))
            .get();
        try (
            var writer = new OutputStreamWriter(out, Charset.forName("EUC-KR"));
            var csvPrinter = new CSVPrinter(writer, format)
        ) {
            if (records == null || records.isEmpty()) {
                csvPrinter.flush();
                return out.toByteArray();
            }
            for (T record : records) {
                csvPrinter.printRecord(getFieldValues(record));
            }
            csvPrinter.flush();
            return out.toByteArray();
        } catch (IOException e) {
            log.error("", e);
            throw new ApiInternalServerException(e.getMessage());
        }
    }
    
    private static <T> String[] getFieldNames(Class<T> type) {
        return Stream.of(type.getDeclaredFields())
            .map(field -> {
                var annotation = field.getAnnotation(TableHeader.class);
                return (annotation != null) ? annotation.value() : field.getName();
            })
            .toArray(String[]::new);
    }
    
    private static <T> List<Object> getFieldValues(T record) {
        return Stream.of(record.getClass().getDeclaredFields())
            .map(field -> {
                field.setAccessible(true); // Enable access to private fields
                try {
                    return field.get(record);
                } catch (IllegalAccessException e) {
                    return "ERROR";
                }
            })
            .toList();
    }
    
    public static <T> byte[] generateExcel(ExcelTableSheetData<T> sheetData) {
        return generateExcel(null, List.of(sheetData));
    }
    
    public static <T> byte[] generateExcel(List<ExcelTableSheetData<T>> sheetData) {
        return generateExcel(null, sheetData);
    }
    
    public static <T> byte[] generateExcel(ExcelCoverData coverData, ExcelTableSheetData<T> sheetData) {
        return generateExcel(coverData, List.of(sheetData));
    }
    
    public static <T> byte[] generateExcel(ExcelCoverData coverData, List<ExcelTableSheetData<T>> sheetData) {
        try (
            var workbook = new XSSFWorkbook();
            var outputStream = new ByteArrayOutputStream()
        ) {
            if (coverData != null) {
                generateCover(workbook, coverData);
            }
            for (ExcelTableSheetData<T> data : sheetData) {
                generateTableSheet(workbook, data);
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("", e);
            throw new ApiInternalServerException(e.getMessage());
        }
    }
    
    private static void generateCover(Workbook workbook, ExcelCoverData data) {
        var sheet = workbook.createSheet("표지");
        try {
            int rowNum = 0;
            var headers = getFieldNames(data.getClass());
            var fields = data.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                var row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(headers[i]);
                fields[i].setAccessible(true);
                var value = fields[i].get(data);
                row.createCell(1).setCellValue(value != null ? value.toString() : "");
            }
        } catch (IllegalAccessException e) {
            log.error("", e);
            throw new ApiInternalServerException(e.getMessage());
        }
    }
    
    private static <T> void generateTableSheet(Workbook workbook, ExcelTableSheetData<T> data) {
        var sheet = workbook.createSheet(data.getSheetName());
        try {
            int rowNum = 0;
            
            // create header row
            var headers = getFieldNames(data.getType());
            var headerRow = sheet.createRow(rowNum++);
            var headerStyle = getHeaderCellStyle(workbook);
            for (int i = 0; i < headers.length; i++) {
                var cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // create record row
            var fields = data.getType().getDeclaredFields();
            for (T record : data.getRecords()) {
                var row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (var field : fields) {
                    field.setAccessible(true);
                    var value = field.get(record);
                    row.createCell(colNum++).setCellValue(value != null ? value.toString() : "");
                }
            }
        } catch (IllegalAccessException e) {
            log.error("", e);
            throw new ApiInternalServerException(e.getMessage());
        }
    }
    
    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
