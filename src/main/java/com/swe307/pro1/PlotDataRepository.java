package com.swe307.pro1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotDataRepository extends MongoRepository<PlotData, String> {
}

// MongoRepository<PlotData, String> sayesinde findAll(), save(), count() gibi
// metotları biz yazmadan Spring otomatik üretir.

/*
* CSV  --mongoimport-->  MongoDB (projectData koleksiyonu)
                             |
                  PlotDataRepository.findAll()
                             |
            PlotController.load()  (her saniye 1 değer seçer)
                             |
                 DataHolder(value)  --GraalVM-->  plot.R
                             |
                    SVG grafik --> tarayıcı
                    *
                    * */