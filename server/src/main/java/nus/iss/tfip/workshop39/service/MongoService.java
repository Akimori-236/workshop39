package nus.iss.tfip.workshop39.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.workshop39.repository.MarvelMongoRepo;

@Service
public class MongoService {

    @Autowired
    private MarvelMongoRepo mongoRepo;

    public void insertComment(String comment) {
        Document commentDoc = Document.parse(comment);
        mongoRepo.insertComment(commentDoc);
    }

    public String getCommentsById(int characterId) {
        List<Document> charList = mongoRepo.getCommentById(characterId);
        List<String> jList = charList.stream().map(v -> v.toJson()).toList();
        return jList.toString();
    }
}
