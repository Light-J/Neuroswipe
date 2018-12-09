package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScanRepositoryStatic {
    public Scan findById(Long id);
    public void insert(Scan scan);
    public void updateKnownGood(Long id, Boolean knownGood);
    public List<Scan> getAll();
    public Optional<Scan> getNext();
    List<Scan> getScansFiltered(int minResponses, int percentageGood);
}
