package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Scan;

import java.util.List;

public interface ScanServiceStatic {
    Scan findById(Long id);
    void insert(Scan scan);
    void updateKnownGood(Long id, Boolean knownGood);
    List<Scan> getAll();
}
