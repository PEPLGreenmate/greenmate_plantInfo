package plant.demo.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import reactor.test.StepVerifier;

import java.io.IOException;

public class ApiDataTest {
    private MockWebServer mockWebServer;
    private WebClient webClient;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        webClient = WebClient.create(mockWebServer.url("http://api.nongsaro.go.kr").toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testApiRequest() throws ParserConfigurationException, SAXException, IOException {
        String apiKey = "202305306IYNDRHJQBHJ26YHPN6IDG";
        // Arrange
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/xml")
                .setResponseCode(200);
        mockWebServer.enqueue(mockResponse);

        // Act
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/service/garden/gardenList")
                        .queryParam("apiKey", apiKey)
                        .queryParam("pageNo", "2")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        parsingData(responseMono.block());
        String response = responseMono.block();
        System.out.println(response);
    }

    public void parsingData(String xmlData) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

        NodeList itemList = doc.getElementsByTagName("item");
        for (int i = 0; i < itemList.getLength(); i++) {
            Node item = itemList.item(i);
            NodeList nodeList = item.getChildNodes();
            for (int j=0; j<nodeList.getLength(); j++){
                Node node = nodeList.item((j));
                System.out.println(node.getNodeName()+" : "+node.getTextContent());
            }
            // 필요한 작업 수행
        }
    }

    @Test
    public void testGardenDtlRequest(){
        String apiKey = "202305306IYNDRHJQBHJ26YHPN6IDG";
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200);
        mockWebServer.enqueue(mockResponse);

        // Act
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/service/garden/gardenDtl")
                        .queryParam("apiKey", apiKey)
                        .queryParam("cntntsNo", "12990")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String response = responseMono.block();
        System.out.println(response);
    }
}
