package com.twm.mahout.algorithm;

import com.twm.mahout.utils.CustomFileDataModel;
import com.twm.mahout.utils.OriginalDataRetriever;
import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemBasedRecommender {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = GenericItemBasedRecommender.class.getClassLoader();
        File modelFile = new File(classLoader.getResource("DATA.csv").getFile());
        DataModel model = new CustomFileDataModel(modelFile);
        OriginalDataRetriever retriever = new OriginalDataRetriever(((CustomFileDataModel) model).getIndexedMapInstansce());

        ItemSimilarity similarity = new LogLikelihoodSimilarity(model);

        //GenericBooleanPrefItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(model, similarity);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(1, 1);

        for (RecommendedItem recommendation : recommendations) {
            System.out.println(retriever.getDataBack(recommendation.getItemID()));
            System.out.println(recommendation);
        }
        
    }
}
