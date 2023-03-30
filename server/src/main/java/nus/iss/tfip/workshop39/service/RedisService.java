package nus.iss.tfip.workshop39.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nus.iss.tfip.workshop39.model.MarvelChar;
import nus.iss.tfip.workshop39.repository.MarvelRedisRepo;

@Service
public class RedisService {

    @Autowired
    private MarvelRedisRepo redisRepo;

    public JsonObject toJson(MarvelChar c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("name", c.getName())
                .add("description", c.getDescription())
                .add("thumbnail", c.getThumbnail())
                .build();
    }

    public MarvelChar toMarvelChar(JsonObject obj) {
        MarvelChar MarvelChar = new MarvelChar();
        MarvelChar.setId(obj.getInt("id"));
        MarvelChar.setName(obj.getString("name"));
        MarvelChar.setDescription(obj.getString("description"));
        JsonObject t = obj.getJsonObject("thumbnail");
        MarvelChar.setThumbnail("%s.%s".formatted(t.getString("path"), t.getString("extension")));
        return MarvelChar;
    }

    public MarvelChar redisToMarvelChar(JsonObject obj) {
        MarvelChar MarvelChar = new MarvelChar();
        MarvelChar.setId(obj.getInt("id"));
        MarvelChar.setName(obj.getString("name"));
        MarvelChar.setDescription(obj.getString("description"));
        MarvelChar.setThumbnail(obj.getString("thumbnail"));
        return MarvelChar;
    }

    public void cacheCharacters(List<MarvelChar> characters) {
        List<JsonObject> jsonCharList = characters.stream()
                .map(v -> this.toJson(v))
                .toList();
        redisRepo.cacheCharacters(jsonCharList);
    }

    public MarvelChar getOneCharacter(int characterId) {
        JsonObject jObj = redisRepo.getOneCharacter(characterId);
        return this.redisToMarvelChar(jObj);
    }
}
