package plant.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import plant.demo.service.PlantApiDataCrawling;
import plant.demo.service.PlantDataList;
import plant.demo.service.PlantFileApiDataCrawling;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final PlantApiDataCrawling plantApiDataCrawling;
    private final PlantFileApiDataCrawling plantFileApiDataCrawling;

    @GetMapping("/plantDetail")
    public String fetchAndSaveGardenListData(Model model) throws Exception {
        PlantDataList plantDataList = plantApiDataCrawling.fetchGardenListData();
        model.addAttribute("plantDataList", plantDataList.getItems());
        return "plant";
    }

    @GetMapping("/plantImage")
    public String fetchAndSavePlantImage() throws Exception {
        plantFileApiDataCrawling.fetchGardenListData();
        return "plantImage";
    }

    @GetMapping("/createSql")
    public String createSql() throws Exception {
        plantFileApiDataCrawling.createSql();
        return "index";
    }
}
