package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeer(UUID id);

    BeerDto save(BeerDto beer);

    void update(UUID id, BeerDto beer);

    void delete(UUID id);

}
