package com.twm.mahout.utils;

import java.io.File;
import java.io.IOException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

public class CustomFileDataModel extends FileDataModel {

    IndexGenerator indexgenerator;

    public CustomFileDataModel(File dataFile) throws IOException {
        super(dataFile);
    }

    @Override
    protected long readItemIDFromString(String value) {
        if (indexgenerator == null) {
            indexgenerator = new IndexGenerator();
        }
        return Long.parseLong(indexgenerator.toLongID(value) + "");
    }

    public FastByIDMap<String> getIndexedMapInstansce() {
        return indexgenerator.getIndexedMapInstansce();
    }

}
