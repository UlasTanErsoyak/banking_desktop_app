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
                Elements a = document.getElementsByClass("kurBox");
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

        String DolarLira = values.get(1).toString();
        DolarLira = DolarLira.replaceAll(",", ".").replaceAll("[^\\d.]", "");

        String LiraDollar = values.get(2).toString();
        LiraDollar = LiraDollar.replaceAll(",", ".").replaceAll("[^\\d.]", "");


        String EuroLira = values.get(5).toString();
        EuroLira = EuroLira.replaceAll(",", ".").replaceAll("[^\\d.]", "");

        String LiraEuro = values.get(6).toString();
        LiraEuro = LiraEuro.replaceAll(",", ".").replaceAll("[^\\d.]", "");



        float dollarToLira = Float.parseFloat(DolarLira);
        float liraToDollar = Float.parseFloat(LiraDollar);

        float euroToLira = Float.parseFloat(EuroLira);
        float liraToEuro = Float.parseFloat(LiraEuro);
        String GoldLira = values.get(9).toString();
        GoldLira = GoldLira.replaceAll(",", "/").replaceAll("\\.","").replaceAll("/",".");

        String LiraGold = values.get(10).toString();
        LiraGold = LiraGold.replaceAll(",", "/").replaceAll("\\.","").replaceAll("/",".");

        float goldToLira = Float.parseFloat(GoldLira);
        float liraToGold = Float.parseFloat(LiraGold);

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
