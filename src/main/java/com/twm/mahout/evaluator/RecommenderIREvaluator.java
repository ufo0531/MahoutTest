package com.twm.mahout.evaluator;

import com.twm.mahout.algorithm.UserBasedRecommender;
import java.io.File;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class RecommenderIREvaluator {

    public static void main(String[] args) throws Exception {
        RandomUtils.useTestSeed();

        RandomUtils.useTestSeed();

        ClassLoader classLoader = UserBasedRecommender.class.getClassLoader();
        File modelFile = new File(classLoader.getResource("DATA.csv").getFile());

        DataModel model = new FileDataModel(modelFile);

        RecommenderIRStatsEvaluator evaluator
                = new GenericRecommenderIRStatsEvaluator();

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood
                        = new NearestNUserNeighborhood(2, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // Evaluate precision and recall "at 2":
        IRStatistics stats = evaluator.evaluate(recommenderBuilder,
                null, model, null, 2,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
                1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }
}
