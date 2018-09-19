package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoikrugStrategy implements Strategy {

    private static final String URL_FORMAT= "https://moikrug.ru/vacancies?q=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        if (searchString == null) {
            vacancyList = Collections.emptyList();
        } else {
            int pageNum = 0;
            while (true) {
                try {
                    Document doc = getDocument(searchString, pageNum++);
                    Elements elements = doc.getElementsByClass("job");
                    if (elements.size() > 0) {
                        for (Element element : elements) {
                            if (element != null) {
                                Vacancy vacancy = new Vacancy();

                                vacancy.setTitle(element.getElementsByAttributeValue("class", "title").text());
                                vacancy.setCompanyName(element.getElementsByAttributeValue("class", "company_name").text());
                                vacancy.setSiteName(URL_FORMAT);
                                vacancy.setUrl("https://moikrug.ru" + element.select("a[class=job_icon]").attr("href"));
                                String salary = element.getElementsByAttributeValue("class", "salary").text();
                                String city = element.getElementsByAttributeValue("class", "location").text();
                                vacancy.setSalary(salary.length()==0 ? "" : salary);
                                vacancy.setCity(city.length()==0 ? "" : city);
                                vacancyList.add(vacancy);
                            }
                        }
                    } else break;
                } catch (IOException e) {

                }
            }
        }
        return vacancyList;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/68.0.3440.84 Safari/537.36")
                .referrer("https://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2")
                .get();
    }

}
