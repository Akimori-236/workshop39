package nus.iss.tfip.workshop39.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarvelChar {
    private int id;
    private String name;
    private String description;
    private String thumbnail;

    public static MarvelChar toMarvelChar(JsonObject obj) {
        MarvelChar MarvelChar = new MarvelChar();
        MarvelChar.setId(obj.getInt("id"));
        MarvelChar.setName(obj.getString("name"));
        MarvelChar.setDescription(obj.getString("description"));
        JsonObject t = obj.getJsonObject("thumbnail");
        MarvelChar.setThumbnail("%s.%s".formatted(t.getString("path"), t.getString("extension")));
        return MarvelChar;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("name", this.getName())
                .add("description", this.getDescription())
                .add("thumbnail", this.getThumbnail())
                .build();
    }
}
