package plant.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import plant.demo.service.ApiDataCrawling;
import plant.demo.service.PlantDataList;

@Controller
public class ApiController {

    private final ApiDataCrawling apiDataCrawling;


    @Autowired
    public ApiController(ApiDataCrawling apiDataCrawling) {
        this.apiDataCrawling = apiDataCrawling;
    }


    @GetMapping("/plantDetail")
    public String fetchAndSaveGardenListData(Model model) throws Exception {
        PlantDataList plantDataList = apiDataCrawling.fetchGardenListData();
        model.addAttribute("plantDataList", plantDataList.getItems());
        return "plant";
    }

}
