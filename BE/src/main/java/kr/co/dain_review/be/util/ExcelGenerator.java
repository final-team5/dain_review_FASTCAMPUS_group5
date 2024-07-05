package kr.co.dain_review.be.util;

import kr.co.dain_review.be.model.campaign.CampaignDate;

import kr.co.dain_review.be.model.campaign.CampaignExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelGenerator {
    public static byte[] generateCampaignExcel(CampaignDate period, ArrayList<String> participants) {
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (period == null || participants == null) {
                throw new IllegalArgumentException("Period or participants data is null");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(period.getExperienceStartDate(), formatter);
            LocalDate end = LocalDate.parse(period.getExperienceEndDate(), formatter).plusDays(7);

            List<LocalDate> dates = new ArrayList<>();
            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                dates.add(date);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dates and Members");

            // 첫 번째 줄에 헤더 추가
            Row headerRow = sheet.createRow(0);
            Cell headerCell0 = headerRow.createCell(0);
            headerCell0.setCellValue("날짜");
            Cell headerCell1 = headerRow.createCell(1);
            headerCell1.setCellValue("닉네임");
            Cell headerCell2 = headerRow.createCell(2);
            headerCell2.setCellValue("PC 조회수");
            Cell headerCell3 = headerRow.createCell(3);
            headerCell3.setCellValue("모바일 조회수");

            int rowNum = 1;
            for (LocalDate date : dates) {
                for (String nickname : participants) {
                    Row row = sheet.createRow(rowNum++);
                    Cell dateCell = row.createCell(0);
                    dateCell.setCellValue(date.toString());

                    Cell memberCell = row.createCell(1);
                    memberCell.setCellValue(nickname);

                    // PC 조회수와 모바일 조회수 셀을 비워둠
                    row.createCell(2); // 빈 셀 생성
                    row.createCell(3); // 빈 셀 생성
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();

            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(); // 실제 애플리케이션에서는 로깅을 통해 예외를 기록하는 것이 좋습니다.
            return null;
        }
    }

    public static ArrayList<CampaignExcel> readExcelFile(InputStream inputStream) {
        ArrayList<CampaignExcel> list = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            // 첫 번째 행은 헤더이므로 건너뜁니다.
            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell dateCell = row.getCell(0);
                    Cell nicknameCell = row.getCell(1);
                    Cell pcViewsCell = row.getCell(2);
                    Cell mobileViewsCell = row.getCell(3);
                    CampaignExcel excel = new CampaignExcel();
                    excel.setDate(getCellValueAsString(dateCell));
                    excel.setNickname(getCellValueAsString(nicknameCell));
                    excel.setPcViews(getCellValueAsStringOrDefault(pcViewsCell, 0));
                    excel.setMobileViews(getCellValueAsStringOrDefault(mobileViewsCell, 0));

                    list.add(excel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static String getCellValueAsStringOrDefault(Cell cell, int defaultValue) {
        if (cell == null) {
            return String.valueOf(defaultValue);
        }
        switch (cell.getCellType()) {
            case STRING:
                try {
                    return String.valueOf((int) Double.parseDouble(cell.getStringCellValue()));
                } catch (NumberFormatException e) {
                    return String.valueOf(defaultValue);
                }
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return String.valueOf(defaultValue);
        }
    }
}
