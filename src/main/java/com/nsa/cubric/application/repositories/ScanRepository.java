package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.domain.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScanRepository {
    public Scan findById(Long id);
    public void insert(Scan scan);
    public void updateKnownGood(Long id, Boolean knownGood);
    public void updateReason(Long id, String reason);
    public List<Scan> getAll(int offset);
    public Optional<Scan> getNext();
    public Optional<Scan> getNextPractice();
    List<Scan> getScansFiltered(int minResponses, int percentageGood);
    List<Scan> getScansFilteredPaginated(int minResponses, int percentageGood, int offset);
    List<ScanResult> getScanResults(int[] scanIds);
}
