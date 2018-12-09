package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.repositories.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ScanService implements ScanServiceStatic {

    private ScanRepository scanRepository;

    @Autowired
    public ScanService(ScanRepository scanRepository) {
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
    public List<Scan> getAll(){
        return scanRepository.getAll();
    }

    @Override
    public Optional<Scan> getNext(){
        return scanRepository.getNext();
    }

    @Override
    public List<Scan> getScansFiltered(int minResponses, int percentageGood){
        return scanRepository.getScansFiltered(minResponses, percentageGood);
    }
}
