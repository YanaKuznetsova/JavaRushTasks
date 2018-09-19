package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {

    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/"
            + this.getClass().getPackage().getName().replace('.', '/')
            + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        System.out.println(vacancies.size());
        try{
            String newFile = getUpdatedFileContent(vacancies);
            updateFile(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        Document document;
        try {
            document = getDocument();

            Element templateOriginal = document.getElementsByClass("template").first();
            Element templateCopy = templateOriginal.clone();
            templateCopy.removeAttr("style");
            templateCopy.removeClass("template");

            document.select("tr[class=vacancy]").remove().not("tr[class=vacancy template");

//            Elements vacancyElements = document.getElementsByAttributeValue("class", "vacancy");
//            for (Element vacancyElement : vacancyElements) {
//                vacancyElement.remove();
//            }

            for (Vacancy vacancy : vacancyList) {
                Element element = templateCopy.clone();
                element.getElementsByClass("city").first().text(vacancy.getCity());
                element.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().text(vacancy.getSalary());
                Element elementLink = element.getElementsByTag("a").first();
                elementLink.text(vacancy.getTitle());
                elementLink.attr("href", vacancy.getUrl());

                templateOriginal.before(element.outerHtml());
            }

        } catch (IOException e){
            e.printStackTrace();
            return "Some exception occurred";
        }
        return document.html();
    }

     private void updateFile(String content) {
         try (FileWriter fileWriter = new FileWriter(new File(filePath))){
             fileWriter.write(content);
             fileWriter.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Kiev");
    }

}
