package com.example.demo.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.beans.Statement;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PDFGeneratorService {
    public void export(HttpServletResponse response, String soNumber) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        // Define fonts
        // Define fonts
        BaseFont baseFont = BaseFont.createFont("NotoSerif-VariableFont_wdth,wght.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 12, Font.BOLD | Font.ITALIC);
        Font headerFont = new Font(baseFont, 10, Font.BOLD);
        Font normalFont = new Font(baseFont, 10, Font.NORMAL);
        Font normalFontItalic = new Font(baseFont, 10, Font.ITALIC);

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
        PdfPCell soCell = new PdfPCell(new Phrase("Số: " + soNumber, normalFont));
        soCell.setBorder(Rectangle.NO_BORDER);
        soCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        soTable.addCell(soCell);

        // Second part: Date
        PdfPCell dateCell = new PdfPCell(new Phrase("Houston, ngày " + new SimpleDateFormat("dd 'tháng' MM 'năm' yyyy").format(new Date()), normalFontItalic));
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
        String dobWritten = "Ngày mùng ba, tháng chín, \nnăm hai nghìn không trăm hai mươi tư";

        PdfPTable dobTable = new PdfPTable(4);//create multi-column
        dobTable.setWidthPercentage(100);
        dobTable.setWidths(new float[]{25, 15, 15, 45});//adjust column ratios

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


// Date written in words (with intelligent line breaks)
        PdfPCell dobWrittenValueCell = new PdfPCell(new Phrase(dobWritten, normalFont));
        dobWrittenValueCell.setBorder(Rectangle.NO_BORDER);
        dobWrittenValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dobWrittenValueCell.setNoWrap(false); // Allow wrapping
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
        String hometown = "Hà Nội"; // fetched from MySQL, ensure the value is set correctly

        // Adjusting "Quê quán" table rendering to ensure uniform font rendering
        PdfPTable hometownTable = new PdfPTable(2);
        hometownTable.setWidthPercentage(100);
        hometownTable.setWidths(new float[]{20, 80}); // Adjust column width ratios

// "Quê quán:" label with consistent font
        PdfPCell hometownLabelCell = new PdfPCell(new Phrase("Quê quán:", normalFont));
        hometownLabelCell.setBorder(Rectangle.NO_BORDER);
        hometownLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hometownTable.addCell(hometownLabelCell);

// Hometown value from MySQL with consistent font
        PdfPCell hometownValueCell = new PdfPCell(new Phrase(hometown, normalFont));
        hometownValueCell.setBorder(Rectangle.NO_BORDER);
        hometownValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hometownTable.addCell(hometownValueCell);

// Add to document
        document.add(hometownTable);
        document.add(new Paragraph("\n"));
        headerTable.completeRow();
//Add Số định danh cá nhân:
        String id = ". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .";
        PdfPTable idTable = new PdfPTable(2); // Use two columns
        idTable.setWidthPercentage(100);
        idTable.setWidths(new float[]{30, 70});

        //Số định danh cá nhân: label
        PdfPCell idTableLabelCell = new PdfPCell(new Phrase("Số định danh cá nhân:", normalFont));
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
        document.add(new Paragraph("\n")); //space line down

// Fetch motherName from MySQL (assuming you have a variable fullName) example
        String fullMotherName = "ĐỖ HẢI KIM "; // Replace with the actual name from MySQL

// Create table for "Họ, chữ đệm, tên người mẹ:"
        PdfPTable motherNameTable = new PdfPTable(2);
        motherNameTable.setWidthPercentage(100);
        motherNameTable.setWidths(new float[]{30, 70}); // 30% label, 70% input

        // Label cell
        PdfPCell motherNameLabelCell = new PdfPCell(new Phrase("Họ, chữ đệm, tên người mẹ:", headerFont));
        motherNameLabelCell.setBorder(Rectangle.NO_BORDER);
        motherNameLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        motherNameTable.addCell(motherNameLabelCell);

        // Name from MySQL
        PdfPCell motherNameValueCell = new PdfPCell(new Phrase(fullMotherName, normalFont));
        motherNameValueCell.setBorder(Rectangle.NO_BORDER);
        motherNameValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        motherNameTable.addCell(motherNameValueCell);

        // Add the nameTable to the document
        document.add(motherNameTable);
        //space
        document.add(new Paragraph("\n")); // Space after name field
        headerTable.completeRow();

//////here

        // Fetch values from MySQL (replace these with actual MySQL values)
        String motherBirth = "1990"; // Replace with actual value from MySQL
        String motherEthnicity = "Kinh"; // Replace with actual value from MySQL
        String motherNationality = "Việt Nam"; // This is static


        // Create table for motherBirth, motherEthnicity, and motherNationality
        PdfPTable infoTableMother = new PdfPTable(5);
        infoTableMother.setWidthPercentage(100);
        infoTableMother.setWidths(new float[]{15, 15, 15, 20, 35}); // Adjust column width ratios


        // "Năm sinh:" label
        PdfPCell birthLabelCell = new PdfPCell(new Phrase("Năm sinh:", normalFont));
        birthLabelCell.setBorder(Rectangle.NO_BORDER);
        birthLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableMother.addCell(birthLabelCell);

        // Gender value from MySQL
        PdfPCell motherValueCell = new PdfPCell(new Phrase(motherBirth, normalFont));
        motherValueCell.setBorder(Rectangle.NO_BORDER);
        motherValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableMother.addCell(motherValueCell);

        // "Dân tộc:" label
        PdfPCell motherNameEthnicityLabelCell = new PdfPCell(new Phrase("Dân tộc:", normalFont));
        motherNameEthnicityLabelCell.setBorder(Rectangle.NO_BORDER);
        motherNameEthnicityLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableMother.addCell(motherNameEthnicityLabelCell);

        // motherEthnicity value from MySQL
        PdfPCell motherEthnicityValueCell = new PdfPCell(new Phrase(motherEthnicity, normalFont));
        motherEthnicityValueCell.setBorder(Rectangle.NO_BORDER);
        motherEthnicityValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableMother.addCell(motherEthnicityValueCell);

        // "Quốc tịch: Việt Nam" (Static text)
        PdfPCell motherNationalityCell = new PdfPCell(new Phrase("Quốc tịch: " + motherNationality, normalFont));
        motherNationalityCell.setBorder(Rectangle.NO_BORDER);
        motherNationalityCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableMother.addCell(motherNationalityCell);

        // Add the infoTableMother to the document
        document.add(infoTableMother);
        document.add(new Paragraph("\n")); // Add spacing after this section
        headerTable.completeRow();

//Added Nơi cư trú:
        String motherPlaceOfBirth = "10505 Cowberry Ct, Vienna, VA 22182, Hoa Kì";

        PdfPTable motherPlaceOfBirthTable = new PdfPTable(2);
        motherPlaceOfBirthTable.setWidthPercentage(100);
        motherPlaceOfBirthTable.setWidths(new float[]{20, 80});

        //"Nơi cư trú:" label
        PdfPCell motherPlaceOfBirthLabelCell = new PdfPCell(new Phrase("Nơi cư trú:", normalFont));
        motherPlaceOfBirthLabelCell.setBorder(Rectangle.NO_BORDER);
        motherPlaceOfBirthLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        motherPlaceOfBirthTable.addCell(placeOfBirthLabelCell);

        //Take placeOfBirth content from MySQL
        PdfPCell mohterPlaceOfBirthContentCell = new PdfPCell(new Phrase(motherPlaceOfBirth, normalFont));
        mohterPlaceOfBirthContentCell.setBorder(Rectangle.NO_BORDER);
        mohterPlaceOfBirthContentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        motherPlaceOfBirthTable.addCell(mohterPlaceOfBirthContentCell);

        //add placeOfBirthTable into doc
        document.add(motherPlaceOfBirthTable);
        document.add(new Paragraph("\n"));

////here is Dad table
        // Fetch dadName from MySQL (assuming you have a variable fullName) example
        String fullDadName = "DOAN DUC THO"; // Replace with the actual name from MySQL

// Create table for "Họ, chữ đệm, tên người cha:"
        PdfPTable dadNameTable = new PdfPTable(2);
        dadNameTable.setWidthPercentage(100);
        dadNameTable.setWidths(new float[]{30, 70}); // 30% label, 70% input

        // Label cell
        PdfPCell dadNameLabelCell = new PdfPCell(new Phrase("Họ, chữ đệm, tên người cha:", headerFont));
        dadNameLabelCell.setBorder(Rectangle.NO_BORDER);
        dadNameLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dadNameTable.addCell(dadNameLabelCell);

        // Name from MySQL
        PdfPCell dadNameValueCell = new PdfPCell(new Phrase(fullDadName, normalFont));
        dadNameValueCell.setBorder(Rectangle.NO_BORDER);
        dadNameValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dadNameTable.addCell(dadNameValueCell);

        // Add the nameTable to the document
        document.add(dadNameTable);
        //space
        document.add(new Paragraph("\n")); // Space after name field
        headerTable.completeRow();

//////here

        // Fetch values from MySQL (replace these with actual MySQL values)
        String dadBirth = "1979"; // Replace with actual value from MySQL
        String dadEthnicity = "Kinh"; // Replace with actual value from MySQL
        String dadNationality = "Việt Nam"; // This is static


        // Create table for motherBirth, motherEthnicity, and motherNationality
        PdfPTable infoTableDad = new PdfPTable(5);
        infoTableDad.setWidthPercentage(100);
        infoTableDad.setWidths(new float[]{15, 15, 15, 20, 35}); // Adjust column width ratios


        // "Năm sinh bố:" label
        PdfPCell dadBirthLabelCell = new PdfPCell(new Phrase("Năm sinh:", normalFont));
        dadBirthLabelCell.setBorder(Rectangle.NO_BORDER);
        dadBirthLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableDad.addCell(dadBirthLabelCell);

        // Gender value from MySQL
        PdfPCell dadValueCell = new PdfPCell(new Phrase(dadBirth, normalFont));
        dadValueCell.setBorder(Rectangle.NO_BORDER);
        dadValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableDad.addCell(dadValueCell);

        // "Dân tộc:" label
        PdfPCell dadEthnicityLabelCell = new PdfPCell(new Phrase("Dân tộc:", normalFont));
        dadEthnicityLabelCell.setBorder(Rectangle.NO_BORDER);
        dadEthnicityLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableDad.addCell(dadEthnicityLabelCell);

        // motherEthnicity value from MySQL
        PdfPCell dadEthnicityValueCell = new PdfPCell(new Phrase(dadEthnicity, normalFont));
        dadEthnicityValueCell.setBorder(Rectangle.NO_BORDER);
        dadEthnicityValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableDad.addCell(dadEthnicityValueCell);

        // "Quốc tịch: Việt Nam" (Static text)
        PdfPCell dadNationalityCell = new PdfPCell(new Phrase("Quốc tịch: " + dadNationality, normalFont));
        dadNationalityCell.setBorder(Rectangle.NO_BORDER);
        dadNationalityCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoTableDad.addCell(dadNationalityCell);

        // Add the infoTableDad to the document
        document.add(infoTableDad);
        document.add(new Paragraph("\n")); // Add spacing after this section
        headerTable.completeRow();

//Added Nơi cư trú:
        String dadPlaceOfBirth = "10505 Cowberry Ct, Vienna, VA 22182, Hoa Kì";

        PdfPTable dadPlaceOfBirthTable = new PdfPTable(2);
        dadPlaceOfBirthTable.setWidthPercentage(100);
        dadPlaceOfBirthTable.setWidths(new float[]{20, 80});

        //"Nơi cư trú:" label
        PdfPCell dadPlaceOfBirthLabelCell = new PdfPCell(new Phrase("Nơi cư trú:", normalFont));
        dadPlaceOfBirthLabelCell.setBorder(Rectangle.NO_BORDER);
        dadPlaceOfBirthLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dadPlaceOfBirthTable.addCell(dadPlaceOfBirthLabelCell);

        //Take placeOfBirth content from MySQL
        PdfPCell dadPlaceOfBirthContentCell = new PdfPCell(new Phrase(dadPlaceOfBirth, normalFont));
        dadPlaceOfBirthContentCell.setBorder(Rectangle.NO_BORDER);
        dadPlaceOfBirthContentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        dadPlaceOfBirthTable.addCell(dadPlaceOfBirthContentCell);

        //add placeOfBirthTable into doc
        document.add(dadPlaceOfBirthTable);
        document.add(new Paragraph("\n"));


        String Birth_certificate_registration_place = "Cơ quan hộ tịch bang California, Hoa Kì";
// Đăng ký khai sinh tại:
        PdfPTable Birth_certificate_registration_table = new PdfPTable(2);
        Birth_certificate_registration_table.setWidthPercentage(100);
        Birth_certificate_registration_table.setWidths(new float[]{30, 70}); // 30% label, 70% input

        // Label cell
        PdfPCell Birth_certificate_registration_LabelCell = new PdfPCell(new Phrase("Đăng ký khai sinh tại:", headerFont));
        Birth_certificate_registration_LabelCell.setBorder(Rectangle.NO_BORDER);
        Birth_certificate_registration_LabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        Birth_certificate_registration_table.addCell(Birth_certificate_registration_LabelCell);

        // Name from MySQL
        PdfPCell Birth_certificate_registration_NameValueCell = new PdfPCell(new Phrase(Birth_certificate_registration_place, normalFont));
        Birth_certificate_registration_NameValueCell.setBorder(Rectangle.NO_BORDER);
        Birth_certificate_registration_NameValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Birth_certificate_registration_table.addCell(Birth_certificate_registration_NameValueCell);

        // Add the Birth_certificate_registration_table to the document
        document.add(Birth_certificate_registration_table);
        //space
        document.add(new Paragraph("\n")); // Space after name field
        headerTable.completeRow();


        // Hardcoded values (simulating data from MySQL)
        String numberFromMySQL = "123456"; // Birth certificate number
        String dateFromMySQL = "2024-09-03"; // Birth date (simulating MySQL value)


// Concatenate the values into the desired string
        String khaiSinhSo = "Khai sinh số: " + numberFromMySQL + " , cấp ngày " + dateFromMySQL + ".";

// Now, create the PDF table and add this string to the table
        PdfPTable khaiSinhSoTable = new PdfPTable(1); // Create multi-column table
        khaiSinhSoTable.setWidthPercentage(100);
        khaiSinhSoTable.setWidths(new float[]{100}); // Adjust column ratios

// Add the "Khai sinh số" and date string to the table
        PdfPCell khaiSinhSoCell = new PdfPCell(new Phrase(khaiSinhSo, normalFont));
        khaiSinhSoCell.setBorder(Rectangle.NO_BORDER); // Remove border for cleaner layout
        khaiSinhSoCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to left
        khaiSinhSoTable.addCell(khaiSinhSoCell);

// Continue with other cells...
        document.add(khaiSinhSoTable);
        document.add(new Paragraph("\n")); // Space after name field
        headerTable.completeRow();

//Đã được ký vào Sổ đăng ký khai sinh: table
        String empty = "";

        // Now, create the PDF table and add this string to the table
        PdfPTable SoDangKyKhaiSinhTable = new PdfPTable(2); // Create multi-column table
        SoDangKyKhaiSinhTable.setWidthPercentage(100);
        SoDangKyKhaiSinhTable.setWidths(new float[]{40, 60}); // Adjust column ratios


        //"Đã được ký vào Sổ đăng ký khai sinh::" label
        PdfPCell SoDangKyKhaiSinhLabelCell = new PdfPCell(new Phrase("Đã được ký vào Sổ đăng ký khai sinh:", headerFont));
        SoDangKyKhaiSinhLabelCell.setBorder(Rectangle.NO_BORDER);
        SoDangKyKhaiSinhLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        SoDangKyKhaiSinhTable.addCell(SoDangKyKhaiSinhLabelCell);

        //Take placeOfBirth content from MySQL
        PdfPCell SoDangKyKhaiSinhContentCell = new PdfPCell(new Phrase(empty, normalFont));
        SoDangKyKhaiSinhContentCell.setBorder(Rectangle.NO_BORDER);
        SoDangKyKhaiSinhContentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        SoDangKyKhaiSinhTable.addCell(SoDangKyKhaiSinhContentCell);

        document.add(SoDangKyKhaiSinhTable);

// Optionally, add some space after this section
        document.add(new Paragraph("\n"));

//Họ, chữ đệm, tên người yêu cầu ghi vào Sổ hộ tịch việc khai sinh:"
        // The label and the value (these could be fetched from the database or hardcoded for now)
        String singerName = "ĐỖ HẢI KIM"; // This could be the value fetched from MySQL

// Create a table with two columns (for label and value)
        PdfPTable signerNameTable = new PdfPTable(2); // Create a table with 2 columns
        signerNameTable.setWidthPercentage(100); // Set table width to 100%
        signerNameTable.setWidths(new float[]{66, 34}); // Adjust column ratios (40% for label, 60% for value)

// Add the label cell (Họ, chữ đệm, tên...)
        PdfPCell labelCell = new PdfPCell(new Phrase("Họ, chữ đệm, tên người yêu cầu ghi vào Sổ hộ tịch việc khai sinh:", normalFont));
        labelCell.setBorder(Rectangle.NO_BORDER); // No border for the label cell
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left
        signerNameTable.addCell(labelCell);


// Add the value cell (e.g., ĐỖ HẢI KIM)
        PdfPCell singerNameValueCell = new PdfPCell(new Phrase(singerName, normalFont));
        singerNameValueCell.setBorder(Rectangle.NO_BORDER); // No border for the value cell
        singerNameValueCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left
        signerNameTable.addCell(singerNameValueCell);

        document.add(signerNameTable);
        document.add(new Paragraph("\n"));

//Giấy tờ tùy thân
// Simulating MySQL values
        String identityDocument = "Giấy tờ tùy thân: "; // Static part
        String firstName = "John Doe";  // From MySQL, let's assume this comes from a database
        String documentNumber = "123456"; // Document number, also from MySQL
        String additionalInfo = "Additional Info"; // Some other field from the database, maybe 'Dữ liêu'!U41

// Check if firstName is empty and use documentNumber or firstName based on that
        String finalText = identityDocument + (firstName.isEmpty() ? documentNumber : firstName) + " - " + additionalInfo;

// Now let's use this finalText in a PDF document


        PdfPTable table = new PdfPTable(1); // 1 column table
        table.setWidthPercentage(100);
        table.setWidths(new float[]{100}); // Adjust column width to 100%

// Add the final concatenated string into a cell
        PdfPCell cell = new PdfPCell(new Phrase(finalText, normalFont));
        cell.setBorder(Rectangle.NO_BORDER); // No border for a cleaner look
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left
        table.addCell(cell);

// Add the table to the document
        document.add(table);

// Add a line break after the table
        document.add(new Paragraph("\n"));

// Add the footer at the bottom of the first page
        Paragraph footer = new Paragraph("KT. TỔNG LÃNH SỰ\nPHÓ TỔNG LÃNH SỰ", headerFont);
        footer.setAlignment(Element.ALIGN_RIGHT); // Right-align the footer
        document.add(footer);

// String signingPerson is fetched dynamically from MySQL or set as needed
        String signingPerson = "Đặng Bảo Châu"; // Placeholder for the person signing

// Step 3: Add the name of the person signing in a cell below the footer
// Create a table with 1 row and 1 column
        PdfPTable tableSigner = new PdfPTable(1);
        tableSigner.setWidthPercentage(100); // Set table width

// Add the cell with the name of the signing person
        PdfPCell cellSigner = new PdfPCell(new Phrase(signingPerson, headerFont));
        cellSigner.setBorder(Rectangle.NO_BORDER); // Remove border around the cell
        cellSigner.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the right
        // Move the text a little to the left by reducing the right padding
        cellSigner.setPaddingRight(10f); // Adjust this value to move it a little to the left

// Add the cell to the table
        tableSigner.addCell(cellSigner);

// Add the table below the footer, on the first page
        document.add(tableSigner);

// Force the second table to be on the next page
        document.newPage(); // Start a new page

// Add the second table with "Số" and "Ngày, tháng, năm đăng ký"
        PdfPTable soTable2 = new PdfPTable(2);
        soTable2.setWidthPercentage(100);

// First part: Số (Number)
        PdfPCell soCell2 = new PdfPCell(new Phrase("Số: " + soNumber, normalFont));
        soCell2.setBorder(Rectangle.NO_BORDER);
        soCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        soTable2.addCell(soCell2);

// Second part: Date (Ngày, tháng, năm đăng ký)
        PdfPCell dateCell2 = new PdfPCell(new Phrase("Ngày, tháng, năm đăng ký: " +
                new SimpleDateFormat("dd 'tháng' MM 'năm' yyyy").format(new Date()), normalFont));
        dateCell2.setBorder(Rectangle.NO_BORDER);
        dateCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        soTable2.addCell(dateCell2);

// Add the second table to the document (on the second page)
        document.add(soTable2);

// Add one space below the table
        document.add(new Paragraph("\n"));

// Closing the document
        document.close();
    }


    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}
