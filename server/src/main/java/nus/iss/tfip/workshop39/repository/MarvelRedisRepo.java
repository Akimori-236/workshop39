package nus.iss.tfip.workshop39.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class MarvelRedisRepo {

    @Autowired
    private RedisTemplate<String, String> template;

    public void cacheCharacters(List<JsonObject> characterList) {

        for (JsonObject c : characterList) {
            template.opsForValue().set(String.valueOf(c.getInt("id")), c.toString());
        }
    }
}
