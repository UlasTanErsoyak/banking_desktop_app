package CurrentMarketPricesPackage;


import BalanceScreenPackage.BalanceScreen;
import BalanceScreenPackage.BalanceScreenBackend;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CurrentMarketPrices {
    final private String[] currencies = {"dolar/","euro/"};
    public CurrentMarketPrices() {
        ArrayList<Object> values = new ArrayList<>();
        for(int i=0;i<currencies.length;i++){
            String url = "https://bigpara.hurriyet.com.tr/doviz/";
            url+=currencies[i];
            try {
                final Document document = Jsoup.connect(url).get();
                Elements a = document.getElementsByClass("value up");
                for(Element x : a){
                    values.add(x.text());
                }
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
        String url = "https://bigpara.hurriyet.com.tr/altin/";
        try {
            final Document document = Jsoup.connect(url).get();
            Elements a = document.getElementsByClass("value");
            for(Element x : a){
                values.add(x.text());
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        float dollarToLira = Float.parseFloat(values.get(0).toString().replace(',','.'));
        float liraToDollar = Float.parseFloat(values.get(1).toString().replace(',','.'));

        float euroToLira = Float.parseFloat(values.get(2).toString().replace(',','.'));
        float liraToEuro = Float.parseFloat(values.get(3).toString().replace(',','.'));


        float goldToLira = Float.parseFloat(values.get(5).toString().replace(".","").replace(",","."));
        float liraToGold = Float.parseFloat(values.get(6).toString().replace(".","").replace(",","."));

        LocalDate myObj = LocalDate.now();

        String date = myObj.toString();

        BalanceScreenBackend.date = date;

        BalanceScreenBackend.dollarToLira = dollarToLira;
        BalanceScreenBackend.liraToDollar = liraToDollar;

        BalanceScreenBackend.euroToLira = euroToLira;
        BalanceScreenBackend.liraToEuro = liraToEuro;

        BalanceScreenBackend.goldToLira = goldToLira;
        BalanceScreenBackend.liraToGold = liraToGold;
    }
}
