package com.swe307.pro1;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//// Bu sınıf, MongoDB'deki "projectData" koleksiyonundaki bir satıra (belgeye) karşılık gelir.

@Document(collection = "projectData") // mongoimport komutunda yazdığınız koleksiyon adı
public class PlotData {

    @Id
    private String id;

    // CSV'deki Col-9 başlığındaki veriyi çekeceğiz
    @Field("Col-9")
    private double value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}