package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Scan;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScanServiceStatic {
    Scan findById(Long id);
    void insert(Scan scan);
    void updateKnownGood(Long id, Boolean knownGood);
    List<Scan> getAll(int page);
    Optional<Scan> getNext();
    Optional<Scan> getNextPractice();
    List<Scan> getScansFiltered(Integer minResponses, Integer percentageGood);
    List<Scan> getScansFilteredPaginated(Integer minResponses, Integer percentageGood, int Paginated);
}
