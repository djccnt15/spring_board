package com.djccnt15.spring_board.utils;

import com.djccnt15.spring_board.annotations.TableHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class DownloadFileGenerator {
    
    public static <T> byte[] generateCsv(List<T> records, Class<T> type) {
        var out = new ByteArrayOutputStream();
        var format = CSVFormat.Builder.create()
            .setHeader(getFieldNames(type))
            .get();
        try (
            var writer = new OutputStreamWriter(out);
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
            throw new RuntimeException(e.getMessage());
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
}
