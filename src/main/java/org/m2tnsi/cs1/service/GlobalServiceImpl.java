package org.m2tnsi.cs1.service;

import org.m2tnsi.cs1.classes.Global;
import org.m2tnsi.cs1.repository.GlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    private GlobalRepository globalRepository;

    @Override
    @Transactional
    public void addGlobalToDatabase(Global global) {
        globalRepository.save(global);
    }
}
