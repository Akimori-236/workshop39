package nus.iss.tfip.workshop39.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import nus.iss.tfip.workshop39.model.MarvelChar;
import nus.iss.tfip.workshop39.service.MarvelAPIService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarvelController {

    @Autowired
    private MarvelAPIService marvelAPI;

    // VIEW 1
    @GetMapping(path = "/characters")
    public ResponseEntity<String> getCharacterList(
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(required = true) String search)
            throws NoSuchAlgorithmException {
        // get from api
        List<MarvelChar> characters = marvelAPI.getCharacters(search, limit, offset);
        String output = characters.stream()
                .map(v -> v.toJson())
                .toList()
                .toString();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(output);
    }

    // VIEW 2
    @GetMapping(path = "/character/{characterId}")
    public ResponseEntity<String> getCharacter(@PathVariable(required = true) int characterId)
            throws NoSuchAlgorithmException {
        // get from api
        List<MarvelChar> characters = marvelAPI.getOneCharacter(characterId);
        String output = characters.stream()
                .map(v -> v.toJson())
                .toList()
                .toString();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(output);
    }

    // VIEW 3
    @PostMapping(path = "/character/{characterId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postComment(@PathVariable int characterId) {

        // get from api
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

}
