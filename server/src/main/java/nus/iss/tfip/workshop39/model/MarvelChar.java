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
    private String resourceURI;
    private String thumbnail;

    public static MarvelChar toMarvelChar(JsonObject obj) {
        MarvelChar MarvelChar = new MarvelChar();
        MarvelChar.setId(obj.getInt("id"));
        MarvelChar.setName(obj.getString("name"));
        MarvelChar.setResourceURI(obj.getString("resourceURI"));
        JsonObject t = obj.getJsonObject("thumbnail");
        MarvelChar.setThumbnail("%s.%s".formatted(t.getString("path"), t.getString("extension")));
        return MarvelChar;
    }

    public String toJson() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("name", this.getName())
                .add("resourceURI", this.getResourceURI())
                .add("thumbnail", this.getThumbnail())
                .build()
                .toString();
    }
}
