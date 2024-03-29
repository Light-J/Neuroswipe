package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.domain.ScanResult;
import com.nsa.cubric.application.repositories.ScanRepository;
import com.nsa.cubric.application.repositories.ScanRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScanServiceStatic implements ScanService {

    private ScanRepository scanRepository;

    @Autowired
    public ScanServiceStatic(ScanRepository scanRepository) {
        this.scanRepository = scanRepository;
    }

    @Override
    public Scan findById(Long id){
        return scanRepository.findById(id);
    }

    @Override
    public void insert(Scan scan){
        scanRepository.insert(scan);
    }

    @Override
    public void updateKnownGood(Long id, Boolean knownGood){
        scanRepository.updateKnownGood(id, knownGood);
    }

    @Override
    public void updateReason(Long id, String reason){
        scanRepository.updateReason(id, reason);
    }

    @Override
    public List<Scan> getAll(int page, Boolean onlyPractice){
        if(onlyPractice == null){
            onlyPractice = false;
        }
        return scanRepository.getAll(10*(page-1), onlyPractice);
    }

    @Override
    public Optional<Scan> getNext(){
        return scanRepository.getNext();
    }

    @Override
    public Optional<Scan> getNextPractice(){
        return scanRepository.getNextPractice();
    }

    @Override
    public List<Scan> getScansFiltered(Integer minResponses, Integer percentageGood){
        if (minResponses == null){
            minResponses = 0;
        }
        if (percentageGood == null){
            percentageGood = 0;
        }
        return scanRepository.getScansFiltered(minResponses, percentageGood);
    }

    @Override
    public List<Scan> getScansFilteredPaginated(Integer minResponses, Integer percentageGood, int page){
        if (minResponses == null){
            minResponses = 0;
        }
        if (percentageGood == null){
            percentageGood = 0;
        }
        return scanRepository.getScansFilteredPaginated(minResponses, percentageGood, (10*(page-1)));
    }

    public List<ScanResult> getScanResults(Integer[] scanIds){
        return scanRepository.getScanResults(scanIds);
    }
}

