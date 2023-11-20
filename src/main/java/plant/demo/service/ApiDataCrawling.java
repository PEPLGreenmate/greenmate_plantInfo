package plant.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import plant.demo.repository.PlantDataRepository;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


@Service
public class ApiDataCrawling {
    private final WebClient webClient;
    private final String apiKey;
    private String totalCount;
    private String numOfRows;
    private String pageNo;
    private String[] plantDetailField;

    @Autowired
    private PlantDataRepository plantDataRepository;

    public ApiDataCrawling() {
        this.apiKey = "202305306IYNDRHJQBHJ26YHPN6IDG";
        this.webClient = WebClient.create("http://api.nongsaro.go.kr");
        this.numOfRows = "100";
        this.pageNo = "1";
        this.plantDetailField = new String[]{"cntntsNo", "plntbneNm", "plntzrNm", "distbNm", "fmlNm", "orgplceInfo", "adviseInfo", "growthHgInfo", "growthAraInfo", "smellCode", "toxctyInfo", "managelevelCode", "grwtveCode", "grwhTpCode", "winterLwetTpCode", "hdCode", "frtlzrInfo", "watercycleSprngCode", "watercycleSummerCode", "watercycleAutumnCode", "watercycleWinterCode", "speclmanageInfo", "fncltyInfo", "managedemanddoCode"};

    }

    public PlantDataList fetchGardenListData() throws Exception {
        //List<PlantDataList> plantDataLists = new ArrayList<>();
        List<PlantData> plants = new ArrayList<>();

        System.out.println("API 데이터 크롤링 중...");

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/service/garden/gardenList")
                        .queryParam("apiKey", this.apiKey)
                        .queryParam("numOfRows", this.numOfRows)
                        .queryParam("pageNo", this.pageNo)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //System.out.println(response);

        //XmlMapper xmlMapper = new XmlMapper();
        //PlantDataList plantDataList = xmlMapper.readValue(response, PlantDataList.class);
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(response));

        Element root = document.getRootElement();  // XML의 루트 요소를 가져옵니다.
        Element body = root.element("body");
        Element items = body.element("items");

        List<Element> itemList = items.elements("item");
        for (Element item : itemList) {
            // 각 item 요소의 자식 요소를 사용하려면 item.element("요소이름")을 사용하면 됩니다.
            String cntntsNo = item.elementText("cntntsNo");
            String cntntsSj = item.elementText("cntntsSj");
            PlantData plantData = new PlantData();
            plantData.setCntntsNo(cntntsNo);
            plantData.setCntntsSj(cntntsSj);
            plants.add(plantData);
        }

        //plantDataLists.add(plantDataList);


        this.totalCount = getTagValue(response, "totalCount");
        int num = Integer.parseInt(this.totalCount) / Integer.parseInt(this.numOfRows) + 1;

        for (int j = 2; j <= num; j++) {
            int finalJ = j;
            String res = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/service/garden/gardenList")
                            .queryParam("apiKey", this.apiKey)
                            .queryParam("numOfRows", this.numOfRows)
                            .queryParam("pageNo", Integer.toString(finalJ))
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            //PlantDataList plantList = xmlMapper.readValue(res, PlantDataList.class);
            //plantDataLists.add(plantList);

            Document doc = reader.read(new StringReader(res));
            root = doc.getRootElement();  // XML의 루트 요소를 가져옵니다.
            body = root.element("body");
            items = body.element("items");

            List<Element> item_list = items.elements("item");
            for (Element item : item_list) {
                // 각 item 요소의 자식 요소를 사용하려면 item.element("요소이름")을 사용하면 됩니다.
                String cntntsNo = item.elementText("cntntsNo");
                String cntntsSj = item.elementText("cntntsSj");
                PlantData plantData = new PlantData();
                plantData.setCntntsNo(cntntsNo);
                plantData.setCntntsSj(cntntsSj);
                //System.out.println("CntntsNo :"+cntntsNo+", CntntsSj : "+cntntsSj);
                //System.out.println("CntntsNo : "+plantData.getCntntsNo()+", cntntsSj : "+plantData.getCntntsSj());

                plants.add(plantData);
            }

            //System.out.println(res);

        }


        PlantDataList allPlantDataList = new PlantDataList();
        allPlantDataList.setItems(plants);

        /*
        for (PlantData plantData : allPlantDataList.getItems()) {
            System.out.println("CntntsNo : " + plantData.getCntntsNo() + ", CntntsSj : " + plantData.getCntntsSj());
        }

         */


        System.out.println("식물 상세 정보 데이터베이스에 저장 중...");
        //PlantData에 Detail 값까지 저장
        for (PlantData plantData : plants) {
            String res = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/service/garden/gardenDtl")
                            .queryParam("apiKey", this.apiKey)
                            .queryParam("cntntsNo", plantData.getCntntsNo())
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            SAXReader reader_ = new SAXReader();
            Document doc = reader_.read(new StringReader(res));

            Element root_ = doc.getRootElement();  // XML의 루트 요소를 가져옵니다.
            Element body_ = root_.element("body");
            Element item = body_.element("item");

            Field[] fields = PlantData.class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!fieldName.equals("cntntsNo") & !fieldName.equals("cntntsSj")) {
                    Element child = item.element(fieldName);
                    String fieldValue = child.getText();
                    field.setAccessible(true);
                    try {
                        field.set(plantData, fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            //System.out.println("DB save");
            saveData(plantData);

        }

        System.out.println("데이터베이스 plant 테이블에 저장 완료");
        return allPlantDataList;
    }

    @Transactional
    public void saveData(PlantData plantData) {
        if (plantData.getCntntsNo() == null) {
            System.out.println("CntntsNo 필드가 null 입니다.");
            System.out.println("CntntsNo : " + plantData.getCntntsNo());
            System.out.println("CntntsSj : " + plantData.getCntntsSj());
        } else {
            plantDataRepository.save(plantData);
        }
    }

    public String getTagValue(String xml, String tagName) throws Exception {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        InputSource source = new InputSource(new StringReader(xml));
        String result = xpath.evaluate("//" + tagName, source);
        return result;
    }

}