package plant.demo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


public class PlantDataList {
    private List<PlantData> items;


    public List<PlantData> getItems() {
        return items;
    }

    public void setItems(List<PlantData> items) {
        this.items = items;
    }

    public void join(List<PlantData> items){
        this.items.addAll(items);
    }

}
