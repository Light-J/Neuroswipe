package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.domain.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScanService {
    Scan findById(Long id);
    void insert(Scan scan);
    void updateKnownGood(Long id, Boolean knownGood);
    void updateReason(Long id, String reason);
    List<Scan> getAll(int page, Boolean onlyPractice);
    Optional<Scan> getNext();
    Optional<Scan> getNextPractice();
    List<Scan> getScansFiltered(Integer minResponses, Integer percentageGood);
    List<Scan> getScansFilteredPaginated(Integer minResponses, Integer percentageGood, int Paginated);
    List<ScanResult> getScanResults(Integer[] scanIds);
}
