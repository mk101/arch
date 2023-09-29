package kolesov.maxim.server.model;

import lombok.Data;

@Data
public class DataModel {

    private String data;
    private boolean isImage;

    public DataModel(String data) {
        this.data = data;

        this.isImage = data.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}.*$");
    }

}
