package com.twm.mahout.evaluator;

import com.twm.mahout.algorithm.UserBasedRecommender;
import java.io.File;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class RecommenderEvaluator {

    public static void main(String[] args) throws Exception {
        RandomUtils.useTestSeed();

        ClassLoader classLoader = UserBasedRecommender.class.getClassLoader();
        File modelFile = new File(classLoader.getResource("DATA.csv").getFile());

        DataModel model = new FileDataModel(modelFile);
        org.apache.mahout.cf.taste.eval.RecommenderEvaluator evaluator
                = new AverageAbsoluteDifferenceRecommenderEvaluator();

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood
                        = new NearestNUserNeighborhood(2, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // Use 90% of the data to train; test using the other 10%.
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
        System.out.println(score);
    }

}
