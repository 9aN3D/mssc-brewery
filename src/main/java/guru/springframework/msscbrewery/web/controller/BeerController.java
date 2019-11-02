package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.service.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(beerService.getBeer(beerId), OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody BeerDto beer) {
        BeerDto savedDto = beerService.save(beer);

        HttpHeaders headers = new HttpHeaders();
        //TODO add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        return new ResponseEntity(headers, CREATED);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(NO_CONTENT)
    public void handleUpdate(@PathVariable("beerId") UUID beerId, @Valid  @RequestBody BeerDto beer) {
        beerService.update(beerId, beer);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("beerId") UUID beerId) {
        beerService.delete(beerId);
    }

}
