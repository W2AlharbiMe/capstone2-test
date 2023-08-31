package com.example.capstone2updated;

import com.example.capstone2updated.DTO.PartDTO;
import com.example.capstone2updated.Model.Part;
import com.example.capstone2updated.Repository.PartRepository;
import com.example.capstone2updated.Service.PartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PartServiceTest {

    @InjectMocks
    PartService partService;

    @Mock
    PartRepository partRepository;




    Part part1;
    Part part2;
    Part part3;

    PartDTO partDTO1;
    PartDTO partDTO2;
    PartDTO partDTO3;



    List<Part> parts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        part1 = new Part(null, "Part1", "N/A", 2400.00, false, Set.of());
        part2 = new Part(null, "Part2", "N/A", 2400.00, false, Set.of());
        part3 = new Part(null, "Part3", "N/A", 2400.00, false, Set.of());

        parts.add(part1);
        parts.add(part2);
        parts.add(part3);

        partDTO1 = PartDTO.builder()
                .name("Part1")
                .description("N/A")
                .purchasePrice(2400.00)
                .isUsed(false)
                .build();

        partDTO2 = PartDTO.builder()
                .name("New Part")
                .description("N/A")
                .purchasePrice(2400.00)
                .isUsed(false)
                .build();
    }

    @Test
    void findAllTest() {
        when(partRepository.findAll()).thenReturn(parts);


        List<Part> partList = partService.findAll();
        Assertions.assertEquals(partList, parts);


        verify(partRepository, times(1)).findAll();
    }

    @Test
    void findByIdTest() {
        when(partRepository.findPartById(part1.getId())).thenReturn(part1);

        Part part = partService.findById(part1.getId());
        Assertions.assertEquals(part, part1);


        verify(partRepository, times(1)).findPartById(part1.getId());
    }

    @Test
    void addPartTest() {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been added.");
        response1.put("part", null);

//        when(partRepository.save(part1)).thenReturn(part1);

        HashMap<String, Object> response = partService.addPart(partDTO1);
        Assertions.assertEquals(response.get("message"), response1.get("message"));


//        verify(partRepository, times(2)).save(part1);
    }


    @Test
    void updatePartTest() {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been updated.");
        response1.put("part", null);

        when(partRepository.findPartById(part3.getId())).thenReturn(part3);

        HashMap<String, Object> response = partService.updatePart(part3.getId(), partDTO2);
        Assertions.assertEquals(response.get("message"), response1.get("message"));

        verify(partRepository, times(1)).save(part3);
        verify(partRepository, times(1)).findPartById(part3.getId());
    }


    @Test
    void deletePartTest() {
        HashMap<String, Object> response1 = new HashMap<>();
        response1.put("message", "the part have been deleted.");
        response1.put("part", null);

        when(partRepository.findPartById(part2.getId())).thenReturn(part2);


        HashMap<String, Object> response = partService.deletePart(part2.getId());
        Assertions.assertEquals(response.get("message"), response1.get("message"));


        verify(partRepository, times(1)).deleteById(part2.getId());
    }
}
