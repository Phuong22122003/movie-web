package com.web.movie.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.web.movie.Dto.CountryDto;
import com.web.movie.Entity.Country;

@Mapper(componentModel="spring")
public interface CountryMapper {
    List<CountryDto> toCountryDtos(List<Country> countries);
    Country toCountry(CountryDto countryDto);
    CountryDto toCountryDto(Country country);
}
