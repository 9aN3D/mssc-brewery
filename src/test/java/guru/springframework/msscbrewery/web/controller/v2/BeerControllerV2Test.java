package guru.springframework.msscbrewery.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.service.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static guru.springframework.msscbrewery.web.model.v2.BeerStyle.PILSNER;
import static guru.springframework.msscbrewery.web.model.v2.BeerStyle.PORTER;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerControllerV2.class)
class BeerControllerV2Test {

    @MockBean
    BeerServiceV2 beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private BeerDtoV2 validBeer;

    private final static String BEER_API_URL = "/api/v2/beer/";

    @BeforeEach
    void setUp() {
        validBeer = BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .name("Beer1")
                .style(PILSNER)
                .upc(123456789012L)
                .build();
    }

    @Test
    void testGetBeer() throws Exception {
        given(beerService.getBeer(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(
                get( BEER_API_URL + validBeer.getId().toString())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")));
    }

    @Test
    void testHandlePost() throws Exception {
        BeerDtoV2 beerDto = validBeer;
        beerDto.setId(null);
        BeerDtoV2 savedDto = BeerDtoV2.builder().id(UUID.randomUUID()).name("New Beer").style(PORTER).build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.save(any())).willReturn(savedDto);

        mockMvc.perform(
                post(BEER_API_URL)
                        .contentType(APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testHandleUpdate() throws Exception {
        BeerDtoV2 beerDto = validBeer;
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(
                put(BEER_API_URL + UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().update(any(), any());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(
                delete(BEER_API_URL + UUID.randomUUID())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(beerService).should().delete(any());
    }

}
