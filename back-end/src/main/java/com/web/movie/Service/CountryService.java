package com.web.movie.Service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.web.movie.Dto.CountryDto;
import com.web.movie.Entity.Country;
import com.web.movie.Repository.CountryRepository;
import com.web.movie.mapper.CountryMapper;

@Service
public class CountryService {
    private CountryRepository countryRepository;
    private CountryMapper countryMapper;
    public CountryService(CountryRepository countryRepository,CountryMapper countryMapper){
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Cacheable(value = "countries")
    public List<CountryDto> getAllCountries(){
        List<Country> countries = countryRepository.findAll();
        return countryMapper.toCountryDtos(countries);
    }
    public CountryDto addCountry(CountryDto request){
        Country country = countryMapper.toCountry(request);
        countryRepository.save(country);
        return countryMapper.toCountryDto(country);
    }
}
