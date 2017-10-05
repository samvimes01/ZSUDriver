/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface SnapshotRetrieveListener {
    void onRetrieveDataSnapshot(DataSnapshot dataSnapshot);
    void onFailed(DatabaseError databaseError);
    void onDataSnapshotNonExists();
}
