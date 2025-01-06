package com.julian.lbniwkalkulator.localdata;

import android.provider.BaseColumns;

public final class IsotopeDatabaseContract {
    private IsotopeDatabaseContract() {
        throw new UnsupportedOperationException("You are not supposed to do this");
    }
    public static class IsotopeEntry implements BaseColumns {
        public static final String TABLE_NAME = "isotopes";
        public static final String COLUMN_NAME_ISOTOPE_NAME = "name";
        public static final String COLUMN_NAME_ACTIVITY = "current_activity";
        public static final String COLUMN_NAME_MOST_RECENT_TIMESTAMP = "most_recent_timestamp";
        public static final String COLUMN_NAME_HALF_LIFE_TIME = "half_life_time";
    }
}
