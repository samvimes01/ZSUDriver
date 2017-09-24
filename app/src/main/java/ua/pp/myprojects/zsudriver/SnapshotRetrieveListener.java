/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Map;

public interface SnapshotRetrieveListener {
    void retrieveDataSnapshot(DataSnapshot dataSnapshot);
    void retrieveFbsNodeData(Map<String, Object> fbsNodeData);
    void onFailed(DatabaseError databaseError);
}
