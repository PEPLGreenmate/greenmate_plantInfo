package plant.demo.service;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import plant.demo.repository.PlantDataRepository;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service
public class PlantFileApiDataCrawling {
    private final String apiKey;
    private final SAXReader reader;
    private final WebClient webClient;
    private final String fileRootPath;
    private final PlantDataRepository plantDataRepository;

    public PlantFileApiDataCrawling(@Autowired PlantDataRepository plantDataRepository) {
        this.apiKey = "202305306IYNDRHJQBHJ26YHPN6IDG";
        this.webClient = WebClient.create("http://api.nongsaro.go.kr");
        this.reader = new SAXReader();
        this.plantDataRepository = plantDataRepository;
        //this.fileRootPath = "./image/";
        this.fileRootPath = "./thumbnail/";
    }

    public void fetchGardenListData() throws Exception {
        String[] cntntsNoList = getCntntsNoList();

        for (String cntntsNo : cntntsNoList) {
            System.out.print(cntntsNo + " 이미지 크롤링 중...");
            String res = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/service/garden/gardenFileList")
                            .queryParam("apiKey", this.apiKey)
                            .queryParam("cntntsNo", cntntsNo)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Document doc = reader.read(new StringReader(res));

            Element root_ = doc.getRootElement();  // XML의 루트 요소를 가져옵니다.
            Element body_ = root_.element("body");
            Element items = body_.element("items");
            Element item = items.element("item");
            //Element fileUrl = item.element("rtnFileUrl");
            Element fileUrl = item.element("rtnThumbFileUrl");
            String text = fileUrl.getText();
            String fullFileName = text.substring(text.lastIndexOf("/") + 1);

            URL url = new URL(text);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();

            File file = new File(fileRootPath + fullFileName);
            file.deleteOnExit();
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (FileNotFoundException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
            System.out.println("완료");
        }
        System.out.println("===== 작업 종료 =====");
    }

    public void createSql() throws Exception {
        String[] cntntsNoList = getCntntsNoList();

        String gcpLargeFileUrl = "https://storage.googleapis.com/spring-boot-image-storage/plantInfoImage/large/";
        String gcpSmallFileUrl = "https://storage.googleapis.com/spring-boot-image-storage/plantInfoImage/thumbnail/";
        for (String cntntsNo : cntntsNoList) {
            String res = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/service/garden/gardenFileList")
                            .queryParam("apiKey", this.apiKey)
                            .queryParam("cntntsNo", cntntsNo)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Document doc = reader.read(new StringReader(res));

            Element root_ = doc.getRootElement();  // XML의 루트 요소를 가져옵니다.
            Element body_ = root_.element("body");
            Element items = body_.element("items");
            Element item = items.element("item");
            Element largeFileUrl = item.element("rtnFileUrl");
            Element smallFileUrl = item.element("rtnThumbFileUrl");
            String largeFileText = largeFileUrl.getText();
            String smallFileText = smallFileUrl.getText();
            String fullLargeFileUrl = gcpLargeFileUrl + largeFileText.substring(largeFileText.lastIndexOf("/") + 1);
            String fullSmallFileUrl = gcpSmallFileUrl + smallFileText.substring(smallFileText.lastIndexOf("/") + 1);

            String sql = "UPDATE plant_guide SET " +
                    "thumbnail_url=\"" + fullSmallFileUrl +
                    "\", image_url=\"" + fullLargeFileUrl +
                    "\" WHERE cntnts_no=" + cntntsNo + ";";
            System.out.println(sql);
        }

        System.out.println("===== 작업 종료 =====");
    }

    private String[] getCntntsNoList() {
        return new String[]{
                "12901", "12919", "12920", "12932", "12937", "12938", "12954", "12955", "12956", "12957", "12962",
                "12963", "12966", "12971", "12972", "12973", "12974", "12975", "12976", "12978", "12979", "12981",
                "12983", "12986", "12987", "12988", "12989", "12990", "12991", "12994", "12996", "12998", "13001",
                "13002", "13003", "13004", "13186", "13187", "13188", "13189", "13190", "13191", "13192", "13193",
                "13194", "13196", "13197", "13199", "13201", "13202", "13203", "13206", "13207", "13208", "13209",
                "13210", "13211", "13212", "13213", "13214", "13215", "13216", "13218", "13240", "13242", "13244",
                "13245", "13247", "13248", "13249", "13251", "13253", "13255", "13257", "13260", "13261", "13309",
                "13317", "13318", "13319", "13333", "13335", "13336", "13337", "13338", "13339", "13340", "14663",
                "14674", "14675", "14676", "14687", "14688", "14689", "14690", "14691", "14692", "14694", "14695",
                "14696", "14697", "14698", "14699", "14700", "14713", "14911", "14913", "14914", "14915", "14916",
                "14917", "14918", "14919", "14920", "15828", "15829", "15830", "15831", "15834", "15835", "16033",
                "16034", "16035", "16036", "16037", "16038", "16138", "16238", "16239", "16241", "16242", "16245",
                "16246", "16248", "16447", "16448", "16449", "16450", "17393", "17741", "17748", "17749", "17750",
                "17751", "18575", "18576", "18578", "18581", "18582", "18585", "18587", "18588", "18589", "18591",
                "18593", "18595", "18596", "18597", "18598", "18599", "18600", "18601", "18602", "18604", "18613",
                "18649", "18652", "18654", "18655", "18656", "18657", "18658", "18659", "18660", "18691", "18692",
                "18694", "18695", "18697", "19448", "19449", "19451", "19452", "19453", "19455", "19457", "19459",
                "19460", "19461", "19462", "19463", "19464", "19465", "19466", "19467", "19468", "19469", "19470",
                "19471", "19474", "19663", "19696", "19701", "19703", "19704", "19706", "19707", "19709", "19711",
                "19712", "19713", "19714", "19715", "19716", "19717", "19718", "19719"
        };
    }

}