package com.Velouraz.velouraz.service;


import com.Velouraz.velouraz.entity.NecklaceSet;
import com.Velouraz.velouraz.repository.NecklaceSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecklaceSetService {

    private final NecklaceSetRepository repo;

    public NecklaceSetService(NecklaceSetRepository repo) {
        this.repo = repo;
    }

    public NecklaceSet save(NecklaceSet set) {
        return repo.save(set);
    }

    public List<NecklaceSet> findAll() {
        return repo.findAll();
    }

    public NecklaceSet findById(Long neck_id) {
        return repo.findById(neck_id).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}