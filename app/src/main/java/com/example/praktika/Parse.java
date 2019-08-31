package com.example.praktika;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Parse
{
    String link = "";
    Document document;
    public void initialize()
    {
        try {
             document = Jsoup.connect("https://www.cbr.ru/currency_base/dynamics/?UniDbQuery.Posted=True&UniDbQuery.mode=1&UniDbQuery.date_req1=&UniDbQuery.date_req2=&UniDbQuery.VAL_NM_RQ=R01115&UniDbQuery.FromDate=05.03.2019&UniDbQuery.ToDate=04.04.2019").userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        Elements value = document.select("div#main_wrap.data");
    }
    }

