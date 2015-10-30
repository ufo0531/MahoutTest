package com.twm.mahout.utils;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;

public class StoreIndexedData {

    private final FastByIDMap<String> longToString;

    protected StoreIndexedData() {
        this.longToString = new FastByIDMap<String>(100);
    }

    public void storeMappingInMemory(long longID, String stringID) {
        synchronized (longToString) {
            longToString.put(longID, stringID);
        }
    }

    public String toStringID(long longID) {
        synchronized (longToString) {
            return longToString.get(longID);
        }
    }

    public FastByIDMap<String> getIndexedMap() {

        return longToString;
    }

}
