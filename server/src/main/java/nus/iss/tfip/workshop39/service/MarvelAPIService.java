package nus.iss.tfip.workshop39.service;

import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.iss.tfip.workshop39.Util.HashingMD5;
import nus.iss.tfip.workshop39.model.MarvelChar;

@Service
public class MarvelAPIService {
    // http://gateway.marvel.com/v1/public/comics?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150
    // (the hash value is the md5 digest of 1abcd1234)
    public static final String MARVEL_API = "https://gateway.marvel.com/v1/public/characters";
    public static final String PUBLIC_KEY = "d90d196eb6b5387b6aee1c809b99b064";

    @Value("${private.key}")
    private String privateKey;

    public List<MarvelChar> getCharacters(String search, int limit, int offset) throws NoSuchAlgorithmException {
        int ts = Instant.now().getNano();
        String hash = HashingMD5.generateMarvelHashNEW(ts, privateKey, PUBLIC_KEY);
        System.out.println("HASH > timestamp - " + ts);
        System.out.println("HASH > " + hash);

        String url = UriComponentsBuilder.fromUriString(MARVEL_API)
                .queryParam("nameStartsWith", search.strip())
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .queryParam("ts", ts)
                .queryParam("apikey", PUBLIC_KEY)
                .queryParam("hash", hash)
                .toUriString();
        // REQUEST CONTAINER
        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        RestTemplate template = new RestTemplate();
        String payload = "";
        try {
            ResponseEntity<String> response = template.exchange(request, String.class);
            payload = response.getBody();
        } catch (RestClientException ex) {
            System.err.println(ex);
            // ex.printStackTrace();
            // return Collections.EMPTY_LIST;
        }
        // READ PAYLOAD
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject marvelResp = reader.readObject();
        JsonObject data = marvelResp.getJsonObject("data");
        JsonArray results = data.getJsonArray("results");

        return results.stream()
                .map(v -> v.asJsonObject())
                .map(MarvelChar::toMarvelChar)
                .toList();
    }

    public MarvelChar getOneCharacter(int characterId) throws NoSuchAlgorithmException {
        int ts = Instant.now().hashCode();
        String hash = HashingMD5.generateMarvelHashNEW(ts, privateKey, PUBLIC_KEY);
        System.out.println("HASH > timestamp - " + ts);
        System.out.println("HASH > " + hash);

        String url = UriComponentsBuilder.fromUriString(MARVEL_API + "/" + characterId)
                .queryParam("ts", ts)
                .queryParam("apikey", PUBLIC_KEY)
                .queryParam("hash", hash)
                .toUriString();
        // REQUEST CONTAINER
        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        RestTemplate template = new RestTemplate();
        String payload = "";
        try {
            ResponseEntity<String> response = template.exchange(request, String.class);
            payload = response.getBody();
        } catch (RestClientException ex) {
            System.err.println(ex);
            // ex.printStackTrace();
            // return Collections.EMPTY_LIST;
        }
        // READ PAYLOAD
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject marvelResp = reader.readObject();
        JsonObject data = marvelResp.getJsonObject("data");
        JsonArray results = data.getJsonArray("results");

        return results.stream()
                .map(v -> v.asJsonObject())
                .map(MarvelChar::toMarvelChar)
                .toList().get(0);
    }
}
