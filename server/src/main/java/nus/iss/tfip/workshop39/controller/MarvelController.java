package nus.iss.tfip.workshop39.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nus.iss.tfip.workshop39.model.MarvelChar;
import nus.iss.tfip.workshop39.service.MarvelAPIService;
import nus.iss.tfip.workshop39.service.MongoService;
import nus.iss.tfip.workshop39.service.RedisService;

@Controller
@ResponseBody
@CrossOrigin("*")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarvelController {

        @Autowired
        private MarvelAPIService marvelAPI;
        @Autowired
        private RedisService redisSvc;
        @Autowired
        private MongoService mongoSvc;

        // VIEW 1
        @GetMapping(path = "/characters")
        public ResponseEntity<String> getCharacterList(
                        @RequestParam(defaultValue = "20") Integer limit,
                        @RequestParam(defaultValue = "0") Integer offset,
                        @RequestParam(required = true) String search)
                        throws NoSuchAlgorithmException {
                System.out.println("CONTROLLER > Searching for " + search + limit + offset);
                // GET FROM API
                List<MarvelChar> characters = marvelAPI.getCharacters(search, limit, offset);
                // REDIS CACHING
                redisSvc.cacheCharacters(characters);
                // OUTPUT
                String output = characters.stream()
                                .map(v -> redisSvc.toJson(v))
                                .toList()
                                .toString();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(output);
        }

        // VIEW 2
        @GetMapping(path = "/characters/{characterId}")
        public ResponseEntity<String> getCharacter(@PathVariable int characterId)
                        throws NoSuchAlgorithmException {
                System.out.println("CONTROLLER > Getting character no:" + characterId);
                // GET FROM REDIS
                MarvelChar mc = redisSvc.getOneCharacter(characterId);
                String output = redisSvc.toJson(mc).toString();
                // FAILSAFE - get from API
                if (output.isBlank()) {
                        mc = marvelAPI.getOneCharacter(characterId);
                        output = redisSvc.toJson(mc).toString();
                }
                System.out.println("CONTROLLER > Redis json data > " + output);
                if (output.isEmpty()) {
                        return ResponseEntity
                                        .status(HttpStatus.GONE)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body("");
                }
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(output);
        }

        // VIEW 3
        @PostMapping(path = "/characters/{characterId}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> postComment(@PathVariable int characterId, @RequestBody String comment) {
                System.out.println("CONTROLLER > Incoming comment > " + comment);
                // save comment to mongo
                mongoSvc.insertComment(comment);
                // get from api
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("");
        }

        // VIEW 3.5
        @GetMapping(path = "/characters/{characterId}/comments")
        public ResponseEntity<String> getComments(@PathVariable int characterId) {
                // get comments to mongo
                String comments = mongoSvc.getCommentsById(characterId);
                System.out.println("CONTROLLER > Mongo comments > " + comments);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(comments);
        }
}
