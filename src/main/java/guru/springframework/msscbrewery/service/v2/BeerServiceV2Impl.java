package guru.springframework.msscbrewery.service.v2;

import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static guru.springframework.msscbrewery.web.model.v2.BeerStyle.LAGER;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {

    @Override
    public BeerDtoV2 getBeer(UUID id) {
        return BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(LAGER)
                .build();
    }

    @Override
    public BeerDtoV2 save(BeerDtoV2 beer) {
        return BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void update(UUID id, BeerDtoV2 beer) {
        //TODO impl - would add real impl to update beer
        log.trace("Updating beer {id: {}, dto: {}}", id, beer);
    }

    @Override
    public void delete(UUID id) {
        log.trace("Deleting beer {id: {}}", id);
    }
}
