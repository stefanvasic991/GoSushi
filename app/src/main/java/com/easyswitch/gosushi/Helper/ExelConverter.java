package com.easyswitch.gosushi.Helper;


import android.content.Context;

import com.easyswitch.gosushi.model.Product;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExelConverter {

    private String currentExelFile = "";
    private File exelFile;
    private Context context;
    private List<String> list = new ArrayList<>();

    public ExelConverter(Context context) {
        this.context = context;
    }

    public void setExelFile(File exelFile) throws IOException {
        exelFile = createExelFile();
        this.exelFile = exelFile;
    }

    public File getExelFile() {
        return exelFile;
    }

    public String getCurrentExelFile() {
        return currentExelFile;
    }


    private File createExelFile() throws IOException {
        // Create an exel file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Exel" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir("Exel");
        File exel = File.createTempFile(
                imageFileName,  /* prefix */
                ".xlsx",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentExelFile = exel.getAbsolutePath();
        return exel;
    }

    public void writeExelFile(List<Product> productList) throws IOException {
        list.add("Name");
        list.add("Start date");
        list.add("End date");
        list.add("Location name");
        list.add("Mass");
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Product");



        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < list.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(list.get(i));

        }

        for (int i = 0; i < productList.size(); i++) {
            Row rowPrdocut = sheet.createRow(i + 1);
            rowPrdocut.createCell(0).setCellValue(productList.get(i).getName());
            rowPrdocut.createCell(1).setCellValue(productList.get(i).getStartDate());
            rowPrdocut.createCell(2).setCellValue(productList.get(i).getEndDate());
            rowPrdocut.createCell(3).setCellValue(productList.get(i).getLocationName());
            rowPrdocut.createCell(4).setCellValue(productList.get(i).getMass());

        }

        for (int i = 0; i < list.size(); i++) {
            sheet.setColumnWidth(i, 15*400);
        }
        FileOutputStream fileOutputStream = null;
        fileOutputStream = new FileOutputStream(getExelFile());

        wb.write(fileOutputStream);

        fileOutputStream.close();
    }
}

