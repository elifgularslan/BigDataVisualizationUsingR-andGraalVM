# SWE 307 - Big Data Project 1: Data Visualization using R and GraalVM
## English

This project is a Java Spring Boot web application that fetches data from a MongoDB NoSQL database and visualizes it dynamically with an R function running on GraalVM. The system reads one row from the database every second, passes the value to the R engine, and the generated graph is refreshed automatically in the web browser every second.

### 🚀 Features

* **Polyglot Programming:** Java and R communicate within the same project (via a `Context`) using the GraalVM infrastructure.
* **Dynamic Data Flow:** The graph updates continuously with the data fetched from MongoDB every second.
* **Visualization:** R's `lattice` library is used to produce high-quality line graphs (xyplot) in `svg` format, with a grid and a dark brown line.
* **Auto Refresh:** The web page refreshes itself every second with the HTTP `Refresh: 1` header, giving a live animation feel.

### 🛠 Technologies Used

* **Backend:** Java 17, Spring Boot
* **Database:** MongoDB, Spring Data MongoDB
* **Virtual Machine / Polyglot Runtime:** GraalVM (v22.3.0)
* **Data Visualization:** R (FastR component) and the `lattice` library
* **Build Tool:** Gradle

### 📋 System Requirements

The following software must be installed to run the project in your own environment:

1. **GraalVM CE 22.3.0 (Java 17):** GraalVM must be used instead of a standard JDK.
2. **GraalVM R Component (FastR):** After installing GraalVM, add R support from the terminal with the `gu install R` command.
3. **MongoDB:** A MongoDB server running on the local machine (localhost:27017).
4. **Gradle:** To manage project dependencies and start the application.

### ⚙️ Installation and Usage

**1. Clone the Project**

```bash
git clone <your-git-repository-link-here>
cd rjava-project
```

**2. Prepare the Database**

First make sure the MongoDB service is running, then import the project's CSV file into the database:

```bash
# Start the MongoDB service
sudo service mongod start

# Import the data (update with your own file name)
mongoimport --type csv -d swe307 -c projectData --headerline --drop swe307_pro1_2.csv
```

**3. Run the Project**

From the project root directory, start the Spring Boot application using the Gradle Wrapper:

```bash
./gradlew bootRun
```

**4. Access the Web Interface**

Once the application is up (it may wait at about 80% on the EXECUTING step), open your favorite web browser and go to the following address:

```
http://localhost:8080/plot
```

### 📂 Project Architecture

* `DataHolder.java`: A simple carrier class used to move data from Java to R.
* `PlotController.java`: The REST controller that sets up the MongoDB connection, reads the next data item every second, and triggers the R code through the GraalVM Context.
* `plot.R`: The main R script that takes the incoming numeric data and draws it on an X-Y plane (in `svg` format) with the `lattice` library.
* `swe307_pro1_2.csv`: The sample dataset imported into the database, containing different columns (Col-1, Col-2, etc.) for each group.
---
## Türkçe

Bu proje, MongoDB NoSQL veritabanından veri çekerek GraalVM üzerinden çalışan bir R fonksiyonu ile dinamik veri görselleştirmesi yapan bir Java Spring Boot web uygulamasıdır. Sistem, veritabanından saniyede bir satır okur, bu veriyi R motoruna iletir ve oluşturulan grafiği web tarayıcısında her saniye otomatik olarak yeniler.

### 🚀 Özellikler

* **Polyglot Programlama:** GraalVM altyapısı kullanılarak Java ve R dilleri aynı proje içinde (Context üzerinden) haberleştirilmiştir.
* **Dinamik Veri Akışı:** MongoDB'den saniyede bir çekilen verilerle grafik sürekli güncellenir.
* **Görselleştirme:** R'ın `lattice` kütüphanesi kullanılarak `svg` formatında yüksek kaliteli, ızgaralı (grid) ve koyu kahverengi çizgi grafikleri (xyplot) üretilir.
* **Otomatik Yenileme:** Web sayfası HTTP `Refresh: 1` başlığı ile kendini her saniye yenileyerek canlı bir animasyon hissi verir.

### 🛠 Kullanılan Teknolojiler

* **Backend:** Java 17, Spring Boot
* **Veritabanı:** MongoDB, Spring Data MongoDB
* **Sanal Makine / Çok Dilli Çalışma Ortamı:** GraalVM (v22.3.0)
* **Veri Görselleştirme:** R (FastR bileşeni) ve `lattice` kütüphanesi
* **Derleme Aracı:** Gradle

### 📋 Sistem Gereksinimleri

Projeyi kendi ortamınızda çalıştırabilmek için aşağıdaki yazılımların kurulu olması gerekmektedir:

1. **GraalVM CE 22.3.0 (Java 17):** Standart bir JDK yerine GraalVM kullanılmalıdır.
2. **GraalVM R Bileşeni (FastR):** GraalVM kurulduktan sonra terminalden `gu install R` komutu ile R desteği eklenmelidir.
3. **MongoDB:** Yerel makinede (localhost:27017) çalışan bir MongoDB sunucusu.
4. **Gradle:** Proje bağımlılıklarını yönetmek ve uygulamayı başlatmak için.

### ⚙️ Kurulum ve Çalıştırma

**1. Projeyi Klonlayın**

```bash
git clone <proje-git-linkini-buraya-yazin>
cd rjava-project
```
### 1.2. GraalVM'i JDK olarak ayarlayın

```bash
export JAVA_HOME=/path/to/graalvm-ce-java17-22.3.0
export PATH=$JAVA_HOME/bin:$PATH
 
java -version      # çıktıda GraalVM yazmalı
gu install r       # yalnızca bir kez
```


**2. Veritabanını Hazırlayın**

Öncelikle MongoDB servisinin çalıştığından emin olun ve projeye ait CSV dosyasını veritabanına aktarın:

```bash
#Linux
# MongoDB servisini başlatın
sudo service mongod start

# Veriyi içe aktarın (Kendi dosya adınıza göre güncelleyin)
mongoimport --type csv -d swe307 -c projectData --headerline --drop swe307_pro1_2.csv
```

**3. Projeyi Çalıştırın**

Projenin ana dizinindeyken Gradle Wrapper kullanarak Spring Boot uygulamasını başlatın:

```bash
./gradlew bootRun
```

**4. Web Arayüzüne Erişin**

Uygulama başarıyla (yaklaşık %80 EXECUTING adımında bekleyerek) ayağa kalktığında favori web tarayıcınızı açın ve aşağıdaki adrese gidin:

```
http://localhost:8080/plot
```
## Veriyi MongoDB'de doğrulama

```bash
mongosh
```

```js
use swe307project1
db.projectData.countDocuments()                       // 100
db.projectData.find({}, {"Col-9": 1, _id: 0})         // Col-9 değerleri, -1 ... 0.9
exit
```

## Proje yapısı

```
src/main/
├── java/com/swe307/pro1/
│   ├── *Application.java       Spring Boot giriş noktası
│   ├── PlotController.java     /plot endpoint'i, GraalVM context'i, sıradaki değeri seçer
│   ├── PlotData.java           MongoDB belge eşlemesi (yalnızca "Col-9")
│   ├── PlotDataRepository.java Spring Data repository (findAll)
│   └── DataHolder.java         Java'dan R'a tek bir double değer taşır
└── resources/
    ├── plot.R                  xyplot'u SVG olarak çizen R fonksiyonu
    └── application.properties  MongoDB bağlantı ayarları
```
### 📂 Proje Mimarisi

* `DataHolder.java`: Veriyi Java'dan R'a taşımak için kullanılan basit taşıyıcı sınıf.
* `PlotController.java`: MongoDB bağlantısını kuran, her saniye sıradaki veriyi okuyan ve GraalVM Context'i üzerinden R kodunu tetikleyen REST kontrolcüsü.
* `plot.R`: Gelen sayısal veriyi alıp `lattice` kütüphanesi ile X-Y düzleminde (`svg` formatında) çizen ana R betiği.
* `swe307_pro1_2.csv`: Veritabanına aktarılan, her gruba ait farklı kolonların (Col-1, Col-2 vb.) bulunduğu örnek veri seti.

---

