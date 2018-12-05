package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;

import java.util.List;

public interface ScanRepositoryStatic {
    public Scan findById(Long id);
    public void insert(Scan scan);
    public void updateKnownGood(Long id, Boolean knownGood);
    public List<Scan> getAll();
}
