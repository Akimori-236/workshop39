package nus.iss.tfip.workshop39.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MarvelMongoRepo {

    public static final String COLLECTION_COMMENTS = "comments";
    public static final String FIELD_CHARACTERID = "characterId";

    @Autowired
    private MongoTemplate template;

    public Document insertComment(Document order) {
        Document response = template.insert(order, COLLECTION_COMMENTS);
        return response;
    }

    public List<Document> getCommentById(int characterId) {
        Criteria criteria = Criteria.where(FIELD_CHARACTERID).is(String.valueOf(characterId));
        Query query = Query.query(criteria);
        List<Document> commentList = template.find(query, Document.class, COLLECTION_COMMENTS);
        return commentList;
    }
}
