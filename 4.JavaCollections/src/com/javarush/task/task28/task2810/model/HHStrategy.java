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

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
    private static final String CACHED = "http://javarush.ru/testdata/big28data.html";

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
                    Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                    if (elements.size() > 0) {
                        for (Element element : elements) {
                            Vacancy vacancy = new Vacancy();

                            String title = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();
                            vacancy.setTitle(title);

                            String salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                            if (salary == null) {
                                vacancy.setSalary("");
                            } else {
                                vacancy.setSalary(salary);
                            }

                            String city = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                            vacancy.setCity(city);

                            String companyName = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                            vacancy.setCompanyName(companyName);

                            String url = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href");
                            vacancy.setUrl(url);

                            String siteName = "http://hh.ua";
                            vacancy.setSiteName(siteName);

                            vacancyList.add(vacancy);
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
//        String url = CACHED;
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/68.0.3440.84 Safari/537.36")
                .referrer("https://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2")
                .get();
    }
}
