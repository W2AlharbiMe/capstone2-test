package com.example.capstone2updated;


import com.example.capstone2updated.Api.Response.ApiResponseWithData;
import com.example.capstone2updated.Controller.PartsController;
import com.example.capstone2updated.DTO.PartDTO;
import com.example.capstone2updated.Model.Part;
import com.example.capstone2updated.Repository.PartRepository;
import com.example.capstone2updated.Service.PartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PartsController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class PartsControllerTest {

    @MockBean
    PartService partService;


    @Autowired
    MockMvc mockMvc;

    Part part1, part2, part3;

    List<Part> parts;

    PartDTO partDTO;


    @BeforeEach
    void setUp() {
        part1 = new Part(1, "Part1", "N/a", 4500.00, false, Set.of());
        part2 = new Part(2, "Part2", "N/a", 4500.00, false, Set.of());
        part3 = new Part(3, "Part3", "N/a", 4500.00, false, Set.of());

        parts = Arrays.asList(part1, part2, part3);

        partDTO = PartDTO.builder()
                .name("PartDTO")
                .description("N/A")
                .purchasePrice(2400.00)
                .isUsed(false)
                .build();
    }

    @Test
    void findAllTest() throws Exception {

        Mockito.when(partService.findAll()).thenReturn(parts);
        mockMvc
                .perform(
                        get("/api/v1/parts/get")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Part1"));
    }

    @Test
    void findByIdTest() throws Exception {
        Mockito.when(partService.findById(part1.getId())).thenReturn(part1);

        mockMvc
                .perform(
                        get("/api/v1/parts/search/id/{id}", part1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Part1"));
    }

    @Test
    void addPartTest() throws Exception {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been added.");
        response1.put("part", partDTO);

        Mockito.when(partService.addPart(partDTO)).thenReturn(response1);
        mockMvc
                .perform(
                        post("/api/v1/parts/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content( new ObjectMapper().writeValueAsString(partDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.part.name").value("PartDTO"));
        ;
    }

    @Test
    void updatePartTest() throws Exception {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been updated.");
        response1.put("part", partDTO);


        Mockito.when(partService.updatePart(part1.getId(), partDTO)).thenReturn(response1);
        mockMvc
                .perform(
                        put("/api/v1/parts/update/{id}", part1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(partDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.part.name").value("PartDTO"));
        ;
    }

    @Test
    void deletePartTest() throws Exception {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been deleted.");
        response1.put("part", partDTO);

        Mockito.when(partService.deletePart(part1.getId())).thenReturn(response1);

        mockMvc
                .perform(
                        delete("/api/v1/parts/delete/{id}", part1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.part.name").value("PartDTO"));
        ;
    }
}
