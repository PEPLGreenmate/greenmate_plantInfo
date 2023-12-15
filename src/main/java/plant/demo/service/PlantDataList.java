package plant.demo.service;

import plant.demo.entity.PlantInfo;

import java.util.List;


public class PlantDataList {
    private List<PlantInfo> items;


    public List<PlantInfo> getItems() {
        return items;
    }

    public void setItems(List<PlantInfo> items) {
        this.items = items;
    }

    public void join(List<PlantInfo> items){
        this.items.addAll(items);
    }

}
