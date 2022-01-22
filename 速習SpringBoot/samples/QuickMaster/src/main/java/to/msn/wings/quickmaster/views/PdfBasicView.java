package to.msn.wings.quickmaster.views;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.http.MediaType;

public class PdfBasicView extends AbstractView {  
    @Override
    protected void renderMergedOutputModel(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=output.pdf");
        PdfDocument pdf = new PdfDocument(
            new PdfWriter(response.getOutputStream()));
        Document doc = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(
            "HeiseiKakuGo-W5", "UniJIS-UCS2-H");
        doc.setFont(font);
        doc.add(new Paragraph((String)model.get("msg")));
        doc.close();
    }
}
