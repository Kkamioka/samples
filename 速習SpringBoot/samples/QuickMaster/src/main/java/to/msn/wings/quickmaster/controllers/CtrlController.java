package to.msn.wings.quickmaster.controllers;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import to.msn.wings.quickmaster.models.Book;
import to.msn.wings.quickmaster.models.MemberForm;
import to.msn.wings.quickmaster.views.PdfBasicView;

@RequestMapping("/ctrl")
@Controller
public class CtrlController {

    @GetMapping("/index")
    public String index() {
        return "ctrl/index";
    }
   
    @GetMapping("/param/{id}")
    @ResponseBody
    public String param(@PathVariable String id) {
        return "id値：" + id;
    }
  
//  public String param(@PathVariable(name="id") String keyword) {
//      return "id値：" + keyword;
//  }
  
    @GetMapping({ "/param_any", "/param_any/{id}" })
    @ResponseBody
    public String param_any(@PathVariable(required=false) Optional<String> id) {
        return "id値：" + id.orElse("99999");
    }
  
    @GetMapping({ "/param_reg/{id:[0-9]{2,3}}" })
    @ResponseBody
    public String param_reg(@PathVariable String id) {
        return "id値：" + id;
    }
  
    //  @GetMapping("/**")
    @ResponseBody
    public String param_other() {
        return "既定のページです。";
    }
  
    //@GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("main", "ctrl/form::main");
        return "common/layout"; 
    }
  
    //@PostMapping("/form")
    public String form(
        @RequestParam String name,
        @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate birth,
        @RequestParam String email,
        Model model) {
        model.addAttribute("name", name);
        model.addAttribute("birth", birth);
        model.addAttribute("email", email);
        model.addAttribute("main", "ctrl/form_result::main");
        return "common/layout"; 
    }
  
    // 以下、フォームクラス対応
    @GetMapping("/form")
    public String form(@ModelAttribute MemberForm memberForm,
        Model model) {
        model.addAttribute("main", "ctrl/form::main");
        return "common/layout"; 
    }
  
    @PostMapping("/form")
    public String form_result(@ModelAttribute MemberForm memberForm,
        Model model) {
        model.addAttribute("main", "ctrl/form_result::main");
        return "common/layout"; 
    }
  
    @GetMapping("/query")
    @ResponseBody
    public String query(@RequestParam(defaultValue = "1") String id) {
         return "id値： " + id; 
    }
  
    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("main", "ctrl/upload::main");
        return "common/layout";
    }

    @PostMapping("/upload")
    public String upload(Model model,
        @RequestParam("upfile") MultipartFile file) {
        String name = file.getOriginalFilename();
        try(BufferedOutputStream bof = new BufferedOutputStream(
            new FileOutputStream("./" + name))) {
            bof.write(file.getBytes());
            model.addAttribute("success", name + "のアップロードに成功しました。");
        } catch(IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("main", "ctrl/upload::main");
        return "common/layout";
     }

    @GetMapping("/reqheader")
    @ResponseBody
    public String reqheader(@RequestHeader String accept) {
        return "Acceptヘッダー：" + accept;
    }
    
//  public String reqheader(@RequestHeader("accept") String key) {
//        return "Acceptヘッダー：" + key;
//  }

    @GetMapping("/json")
    public String json(Model model) {
        model.addAttribute("main", "ctrl/json::main");
        return "common/layout";
     }
  
    @PostMapping("/json")
    @ResponseBody
    public String json(@RequestBody Book book) {
        return book.getTitle();
    }
  
    @GetMapping("/redirect")
    public String redirect() {
//    return "redirect:/list";
    
        UriComponents builder = MvcUriComponentsBuilder
            .fromMethodName(CtrlController.class, "param", "108").build();
        return "redirect:" + builder.toUriString();
    }
  
    @GetMapping("/forward1")
    public String forward1(HttpServletRequest request) {
        request.setAttribute("name", "山田");
        return "forward:/ctrl/forward2";
    }
    
    @GetMapping("/forward2")
    @ResponseBody
    public String forward2(HttpServletRequest request) {
        return "こんにちは、" + request.getAttribute("name") + "さん！";
    }
  
    @GetMapping("/response")
    //@ResponseStatus(HttpStatus.CREATED)
    public void response(HttpServletResponse response) { 
        try {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/xml; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<result>成功</result>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    @GetMapping("/output.pdf")
    public void output(HttpServletResponse response) {
        response.setContentType("application/pdf");
        try {
             PdfDocument pdf = new PdfDocument(
                 new PdfWriter(response.getOutputStream()));
             Document doc = new Document(pdf);
             PdfFont font = PdfFontFactory.createFont(
                 "HeiseiKakuGo-W5", "UniJIS-UCS2-H");
             doc.setFont(font);
             doc.add(new Paragraph("こんにちは、世界！"));
             doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    @GetMapping("/useview.pdf")
    public ModelAndView useview() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "こんにちは、世界！");
        mv.setView(new PdfBasicView());
        return mv;
    }

    //@GetMapping("/useview.pdf")
    public View useview(Model model) {
        model.addAttribute("msg", "こんにちは、世界！");
        return new PdfBasicView();
    }
  
    @RequestMapping("/download.jpg")
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        FileCopyUtils.copy(
            new FileInputStream("wings.jpg"),
            response.getOutputStream()
        );
    }
}

