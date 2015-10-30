package com.twm.mahout.algorithm;

import com.twm.mahout.utils.CustomFileDataModel;
import com.twm.mahout.utils.OriginalDataRetriever;
import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class ALSRecommender {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = SVDRecommender.class.getClassLoader();
        File modelFile = new File(classLoader.getResource("DATA.csv").getFile());
        DataModel model = new CustomFileDataModel(modelFile);
        OriginalDataRetriever retriever = new OriginalDataRetriever(((CustomFileDataModel) model).getIndexedMapInstansce());

        ALSWRFactorizer factorizer = new ALSWRFactorizer(model, 2, 0.065, 20);
        Recommender recommender = new SVDRecommender(model, factorizer);
        
        List<RecommendedItem> recommendations = recommender.recommend(1, 1);

        for (RecommendedItem recommendation : recommendations) {
            System.out.println(retriever.getDataBack(recommendation.getItemID()));
            System.out.println(recommendation);
        }
    }
}
