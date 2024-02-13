package veronika.golikova;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import veronika.golikova.model.SchoolClass;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {
    private final ClassLoader cl = FileParsingTest.class.getClassLoader();


    @Test
    void unzipAndReadCsvFile() throws Exception{
        try (InputStream is = cl.getResourceAsStream("Архив.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("broker_fin_2.csv")) {

                    CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(zis))
                            .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                            .build();
                    List<String[]> csvFile = csvReader.readAll();
                    Assertions.assertArrayEquals(
                            new String[]{"AVEUX6", "Македонский", "Феофаний", "Сатурнович", "m", "1971-01-09", "79156773060", "финиш логистика", "Москва"},
                            csvFile.get(1)
                    );
                }
            }
        }
    }


    @Test
    void unzipAndReadXlsFile() throws Exception{
        try (InputStream is = cl.getResourceAsStream("Архив.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("Прайс-лист Алеч 01.02.2024 (1).xlsx")) {

                    XLS xls = new XLS(zis);
                    Assertions.assertEquals(
                            "Абрикос сублимированный (порошок)",
                            xls.excel.getSheet("Продукция")
                                    .getRow(7)
                                    .getCell(1)
                                    .getStringCellValue()
                    );
                }
            }
        }
    }

    @Test
    void unzipAndReadPdfFile() throws Exception{
        try (InputStream is = cl.getResourceAsStream("Архив.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("_OceanofPDF.com_The_Science_of_Spice_-_Stuart_Farrimond.pdf")) {

                    PDF pdf = new PDF(zis);
                    Assertions.assertEquals("The Science of Spice", pdf.title);
                    Assertions.assertEquals("Stuart Farrimond", pdf.author);
                }
            }
        }
    }

    @Test
    void jsonParsingWithJacksonCoreTest() throws Exception {
        File file = new File("src/test/resources/schoolClass.json");
            SchoolClass schoolClass = new ObjectMapper().readValue(file, SchoolClass.class);
        Assertions.assertEquals("Veronika", schoolClass.getHumans().get(0).getName());
    }
}
