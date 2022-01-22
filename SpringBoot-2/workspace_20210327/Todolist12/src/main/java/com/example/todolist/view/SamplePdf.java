package com.example.todolist.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class SamplePdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document doc,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response)
            throws Exception {
        // テキスト
        String currentTime = ((java.util.Date)model.get("currentTime")).toString();
        doc.add(new Paragraph(currentTime));

        // 表
        Table table = new Table(1);
        table.addCell("currentTime");
        table.addCell(currentTime);
        doc.add(table);
    }
}
