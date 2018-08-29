package com.jpdong.easy.punch.model;

import android.content.Context;

import com.jpdong.easy.punch.PunchItem;

import java.util.List;

public class PunchRepository {

    private PunchDatabase mDatabase;

    public PunchRepository(Context context) {
        mDatabase = new PunchDatabase(context);
    }

    public List<PunchItem> punchItemList() {
        return mDatabase.punchItems();
    }

    public boolean addPunchItem(PunchItem punchItem) {
        return mDatabase.savePunch(punchItem);
    }

    public boolean addPunchTime(String id, long punchTime) {
        return mDatabase.savePunchTime(id, punchTime);
    }

    public List<Long> punchTimeList(String id) {
        return mDatabase.punchTimes(id);
    }
}
