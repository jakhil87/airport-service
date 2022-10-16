package com.demo.airport.rest;

import com.demo.airport.service.AirportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AirportController.class)
@AutoConfigureMockMvc(addFilters = false)
class AirportControllerTest {

    @MockBean
    AirportService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetCountriesWithMaxAirports_Success() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders
                    .get("http://localhost:8080/api/countries-with-max-airports?maxResults=5")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void testGetCountriesWithMaxAirports_OptionalQueryParam() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders
                    .get("http://localhost:8080/api/countries-with-max-airports")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void testGetRunwaysByCountryCodeOrName_Success() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders
                    .get("http://localhost:8080/api/runways-by-country?search=india")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void testGetCountriesByName_Success() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders
                    .get("http://localhost:8080/api/countries-by-name?search=United")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void testGetCountriesByName_ValidationFailed() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/countries-by-name?search=Un")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
