package com.web.movie.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.movie.Dto.CountryDto;
import com.web.movie.Service.CountryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/countries")
@Tag(name = "Countries")
public class CountryRestController {
    @Autowired private CountryService countryService;
    @GetMapping("")
    @Operation(summary = "Get all countries")
    public ResponseEntity<List<CountryDto>> findCountries(){
        return ResponseEntity.ok().body(countryService.getAllCountries());
    }

    @PostMapping("")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto request){
        return ResponseEntity.ok(countryService.addCountry(request));
    }
}
