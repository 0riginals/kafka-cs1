package org.m2tnsi.cs1.service;

import org.m2tnsi.cs1.classes.Country;
import org.m2tnsi.cs1.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    @Transactional
    public void addCountry(Country country) {
        countryRepository.save(country);
    }

}
