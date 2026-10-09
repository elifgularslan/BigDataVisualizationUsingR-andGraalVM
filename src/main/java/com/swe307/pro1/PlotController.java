package com.swe307.pro1;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Controller
public class PlotController {

    @Value(value = "classpath:plot.R") // resources klasöründeki plot.R dosyasını okur
    private Resource rSource;

    @Autowired
    private Function<DataHolder, String> plotFunction; // R'daki fonksiyonu Java'da çağırılabilir hale getirilmiş hali

    @Autowired
    private PlotDataRepository repository; //mongo db ye erişim için repo


    // plot.R dosyasını GraalVM ile derleyip bir Java Function'ına çevirir.
    // Uygulama açılırken bir kez çalışır.
    @Bean
    Function<DataHolder, String> getPlotFunction(@Autowired Context ctx) throws IOException {
        Source source = Source.newBuilder("R", rSource.getURL()).build();
        return ctx.eval(source).as(Function.class);
    }

    // Gelen verileri sırayla göstermek için sayaç
    private int currentIndex = 0;

    @RequestMapping(value = "/plot", produces = "image/svg+xml")
    public ResponseEntity<String> load() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Refresh", "1");

        // MongoDB'den 100 satırı çeker (VERİ BURADA JAVA TARAFINDAN OKUNUR)
        List<PlotData> dataList = repository.findAll();

        String svg;
        synchronized (plotFunction) {
            double currentValue = 0;
            if (dataList != null && !dataList.isEmpty()) {
                currentValue = dataList.get(currentIndex % dataList.size()).getValue();
                currentIndex++;
            }
            svg = plotFunction.apply(new DataHolder(currentValue));//Tek değeri R fonksiyonuna gönder, R grafiği SVG metni olarak döndürür
        }

        return new ResponseEntity<>(svg, responseHeaders, HttpStatus.OK);
    }
    @Bean
    public Context getGraalVMContext() {
        return Context.newBuilder().allowAllAccess(true).build();
    }
}