package com.example.demo.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PDFGeneratorService {
    public void export(HttpServletResponse response, String soNumber) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        // Define fonts
        Font titleFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC);
        Font headerFont = new Font(Font.TIMES_ROMAN, 13, Font.BOLD);
        Font normalFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL);

        document.open();

        // HEADER TABLE WITH TWO COLUMNS (LEFT + RIGHT)
        PdfPTable headerTable = new PdfPTable(2); // Two columns for left and right headers
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{40, 60}); // Adjust column width ratios (40% left, 60% right)

// First row (Tổng Lãnh sự quán and CỘNG HOÀ XÃ HỘI CHỦ NGHĨA VIỆT NAM)
        PdfPCell leftCell = new PdfPCell(new Phrase("Tổng Lãnh sự quán", titleFont));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to the left
        leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align vertically
        headerTable.addCell(leftCell);

        PdfPCell rightCell = new PdfPCell(new Phrase("CỘNG HOÀ XÃ HỘI CHỦ NGHĨA VIỆT NAM", titleFont));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the right
        rightCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align vertically
        headerTable.addCell(rightCell);

// Second row (Việt Nam tại Houston, and Độc lập - Tự do - Hạnh phúc)
        PdfPCell leftCellSecondRow = new PdfPCell(new Phrase("Việt Nam tại Houston,", titleFont));
        leftCellSecondRow.setBorder(Rectangle.NO_BORDER);
        leftCellSecondRow.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to the left
        leftCellSecondRow.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align vertically
        headerTable.addCell(leftCellSecondRow);

        PdfPCell rightCellSecondRow = new PdfPCell(new Phrase("Độc lập - Tự do - Hạnh phúc", headerFont));
        rightCellSecondRow.setBorder(Rectangle.NO_BORDER);
        rightCellSecondRow.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the right
        rightCellSecondRow.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align vertically
        rightCellSecondRow.setRightIndent(60);
        headerTable.addCell(rightCellSecondRow);

// Third row (Texas, Hoa Kỳ)
        // Third row (Texas, Hoa Kỳ)
        PdfPCell leftCellThirdRow = new PdfPCell(new Phrase("Texas, Hoa Kỳ", titleFont));
        leftCellThirdRow.setBorder(Rectangle.NO_BORDER);
        leftCellThirdRow.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to the left
        leftCellThirdRow.setVerticalAlignment(Element.ALIGN_MIDDLE); // Align vertically
        leftCellThirdRow.setPaddingLeft(20);  // Adds padding to the left (adjust as needed)

// Empty cell for the second column to prevent unwanted rectangle
        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);  // No border for the empty cell
        headerTable.addCell(leftCellThirdRow);  // Add the content cell
        headerTable.addCell(emptyCell);  // Add the empty cell to prevent rectangle

// Complete the row manually to ensure proper rendering
        headerTable.completeRow();


// Add header table to document
        document.add(headerTable);

// Add one space below the table
        document.add(new Paragraph("\n"));

// Số and Date section
        PdfPTable soTable = new PdfPTable(2);
        soTable.setWidthPercentage(100);

        // First part: Số
        PdfPCell soCell = new PdfPCell(new Phrase("Số: " + soNumber, headerFont));
        soCell.setBorder(Rectangle.NO_BORDER);
        soCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        soTable.addCell(soCell);

        // Second part: Date
        PdfPCell dateCell = new PdfPCell(new Phrase("Houston, ngày " + new SimpleDateFormat("dd 'tháng' MM 'năm' yyyy").format(new Date()), headerFont));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        soTable.addCell(dateCell);

        // Add the Số and Date table
        document.add(soTable);

        // Add one space below the table
        document.add(new Paragraph("\n"));

        // DOCUMENT TITLE (CENTERED)
        Paragraph docTitle = new Paragraph("TRÍCH LỤC \nGHI VÀO SỔ HỘ TỊCH VIỆC KHAI SINH", headerFont);
        docTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(docTitle);

        document.add(new Paragraph("\n"));

        // Fetch name from MySQL (assuming you have a variable fullName) example
        String fullName = "Nguyễn Văn A"; // Replace with the actual name from MySQL

        // Create table for "Họ, chữ đệm, tên:"
        PdfPTable nameTable = new PdfPTable(2);
        nameTable.setWidthPercentage(100);
        nameTable.setWidths(new float[]{30, 70}); // 30% label, 70% input

        // Label cell
        PdfPCell nameLabelCell = new PdfPCell(new Phrase("Họ, chữ đệm, tên:", headerFont));
        nameLabelCell.setBorder(Rectangle.NO_BORDER);
        nameLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        nameTable.addCell(nameLabelCell);

        // Name from MySQL
        PdfPCell nameValueCell = new PdfPCell(new Phrase(fullName, normalFont));
        nameValueCell.setBorder(Rectangle.NO_BORDER);
        nameValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        nameTable.addCell(nameValueCell);

        // Add the nameTable to the document
        document.add(nameTable);
        //space
        document.add(new Paragraph("\n")); // Space after name field
        headerTable.completeRow();


        //Fetch value from mySQL example
        String dob = "03/09/2024";
        String dobWritten = "Ngày mùng ba, tháng chín, năm hai nghìn không trăm hai mươi tư";

        PdfPTable dobTable = new PdfPTable(4);//create multi-column
        dobTable.setWidthPercentage(100);
        dobTable.setWidths(new float[]{25, 20, 20, 35});//adjust column ratios

        //"Ngay, thang, nam sinh:" label
        PdfPCell dobLabelCell = new PdfPCell(new Phrase("Ngày, tháng, năm sinh:", normalFont));
        dobLabelCell.setBorder(Rectangle.NO_BORDER);
        dobLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dobTable.addCell(dobLabelCell);

        // Date value from MySQL
        PdfPCell dobValueCell = new PdfPCell(new Phrase(dob, normalFont));
        dobValueCell.setBorder(Rectangle.NO_BORDER);
        dobValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dobTable.addCell(dobValueCell);

        // "Ghi bằng chữ:" label
        PdfPCell dobWrittenLabelCell = new PdfPCell(new Phrase("Ghi bằng chữ:", normalFont));
        dobWrittenLabelCell.setBorder(Rectangle.NO_BORDER);
        dobWrittenLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dobTable.addCell(dobWrittenLabelCell);

// Date written in words (wrap if needed)
        PdfPCell dobWrittenValueCell = new PdfPCell(new Phrase(dobWritten, normalFont));
        dobWrittenValueCell.setBorder(Rectangle.NO_BORDER);
        dobWrittenValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dobWrittenValueCell.setNoWrap(false); // Allows wrapping if text is too long
        dobTable.addCell(dobWrittenValueCell);

        headerTable.completeRow();
// Add the dobTable to the document
        document.add(dobTable);
        document.add(new Paragraph("\n")); // Add spacing after this section

        headerTable.completeRow();

        // Fetch values from MySQL (replace these with actual MySQL values)
        String gender = "Nam"; // Replace with actual value from MySQL
        String ethnicity = "Kinh"; // Replace with actual value from MySQL
        String nationality = "Việt Nam"; // This is static


        // Create table for Gender, Ethnicity, and Nationality
        PdfPTable infoTable = new PdfPTable(5);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{15, 15, 15, 20, 35}); // Adjust column width ratios


        // "Giới tính:" label
        PdfPCell genderLabelCell = new PdfPCell(new Phrase("Giới tính:", normalFont));
        genderLabelCell.setBorder(Rectangle.NO_BORDER);
        genderLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(genderLabelCell);

        // Gender value from MySQL
        PdfPCell genderValueCell = new PdfPCell(new Phrase(gender, normalFont));
        genderValueCell.setBorder(Rectangle.NO_BORDER);
        genderValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(genderValueCell);

        // "Dân tộc:" label
        PdfPCell ethnicityLabelCell = new PdfPCell(new Phrase("Dân tộc:", normalFont));
        ethnicityLabelCell.setBorder(Rectangle.NO_BORDER);
        ethnicityLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(ethnicityLabelCell);

        // Ethnicity value from MySQL
        PdfPCell ethnicityValueCell = new PdfPCell(new Phrase(ethnicity, normalFont));
        ethnicityValueCell.setBorder(Rectangle.NO_BORDER);
        ethnicityValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(ethnicityValueCell);

        // "Quốc tịch: Việt Nam" (Static text)
        PdfPCell nationalityCell = new PdfPCell(new Phrase("Quốc tịch: " + nationality, normalFont));
        nationalityCell.setBorder(Rectangle.NO_BORDER);
        nationalityCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTable.addCell(nationalityCell);

        // Add the infoTable to the document
        document.add(infoTable);
        document.add(new Paragraph("\n")); // Add spacing after this section
        headerTable.completeRow();
//Added Nơi sinh:
        String placeOfBirth = "thành phố San Jose, bang California, Hoa Kì";

        PdfPTable placeOfBirthTable = new PdfPTable(2);
        placeOfBirthTable.setWidthPercentage(100);
        placeOfBirthTable.setWidths(new float[]{20, 80});

        //"Nơi sinh:" label
        PdfPCell placeOfBirthLabelCell = new PdfPCell(new Phrase("Nơi sinh:", normalFont));
        placeOfBirthLabelCell.setBorder(Rectangle.NO_BORDER);
        placeOfBirthLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        placeOfBirthTable.addCell(placeOfBirthLabelCell);

        //Take placeOfBirth content from MySQL
        PdfPCell placeOfBirthContentCell = new PdfPCell(new Phrase(placeOfBirth, normalFont));
        placeOfBirthContentCell.setBorder(Rectangle.NO_BORDER);
        placeOfBirthContentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        placeOfBirthTable.addCell(placeOfBirthContentCell);

        //add placeOfBirthTable into doc
        document.add(placeOfBirthTable);
        document.add(new Paragraph("\n"));

        headerTable.completeRow();
//Added "Quê quán:"
        // Fetch "Quê quán" value from MySQL (Replace with actual data)
        String hometown = "Thừa Thiên Huế, Việt Nam"; // Example value from MySQL

        // Create table for "Quê quán"
        PdfPTable hometownTable = new PdfPTable(2);
        hometownTable.setWidthPercentage(100);
        hometownTable.setWidths(new float[]{20, 80}); // Adjust column width ratios

        // "Quê quán:" label

        PdfPCell hometownLabelCell = new PdfPCell(new Phrase("Quê quán:", headerFont));
        hometownLabelCell.setBorder(Rectangle.NO_BORDER);
        hometownLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hometownTable.addCell(hometownLabelCell);

        // Hometown value from MySQL
        PdfPCell hometownValueCell = new PdfPCell(new Phrase(hometown, normalFont));
        hometownValueCell.setBorder(Rectangle.NO_BORDER);
        hometownValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hometownTable.addCell(hometownValueCell);

        // Add the hometownTable to the document
        document.add(hometownTable);
        document.add(new Paragraph("\n"));
        headerTable.completeRow();
//Add Số định danh cá nhân:
        String id = "...........................";
        PdfPTable idTable = new PdfPTable(2); // Use two columns
        idTable.setWidthPercentage(100);
        idTable.setWidths(new float[]{30, 70});

        //Số định danh cá nhân: label
        PdfPCell idTableLabelCell = new PdfPCell(new Phrase("Số định danh cá nhân:", headerFont));
        idTableLabelCell.setBorder(Rectangle.NO_BORDER);
        idTableLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        idTable.addCell(idTableLabelCell);


        // id value from MySQL
        PdfPCell idTableValueCell = new PdfPCell(new Phrase(id, normalFont));
        idTableValueCell.setBorder(Rectangle.NO_BORDER);
        idTableValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        idTable.addCell(idTableValueCell);

        // Add the hometownTable to the document
        document.add(idTable);
        document.add(new Paragraph("\n"));

        // Closing Signature Section
        document.add(new Paragraph("\n"));
        Paragraph footer = new Paragraph("KT. TỔNG LÃNH SỰ\nPHÓ TỔNG LÃNH SỰ", headerFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);

        document.close();
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}
