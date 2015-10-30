package com.twm.mahout.utils;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;

public class OriginalDataRetriever {

    FastByIDMap<String> map = null;

    public OriginalDataRetriever(FastByIDMap<String> dataMap) {
        map = dataMap;
    }

    public String getDataBack(Long id) {
        return map.get(id);
    }

}
