package com.example.capstone2updated;


import com.example.capstone2updated.Model.Manufacturer;
import com.example.capstone2updated.Repository.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManufacturerRepositoryTest {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    Manufacturer manufacturer1;

    Manufacturer manufacturer2;
    Manufacturer manufacturer3;


    @BeforeEach
    void setUp() {
        // clean the table
        manufacturerRepository.deleteAll();

        manufacturer1 = new Manufacturer(null, "Manufacturer1", 2002, Set.of());
        manufacturer2 = new Manufacturer(null, "Manufacturer2", 2003, Set.of());
        manufacturer3 = new Manufacturer(null, "Manufacturer3", 2003, Set.of());

    }

    @Test
    void findManufacturerByIdTest() {
        manufacturerRepository.save(manufacturer1);
        manufacturerRepository.save(manufacturer2);
        manufacturerRepository.save(manufacturer3);


        Manufacturer manufacturer = manufacturerRepository.findManufacturerById(manufacturer1.getId());

        Assertions.assertThat(manufacturer.getId()).isEqualTo(manufacturer1.getId());
    }

    @Test
    void findManufacturerByNameTest() {
        manufacturerRepository.save(manufacturer2);

        Manufacturer manufacturer = manufacturerRepository.findManufacturerByName("Manufacturer2");

        Assertions.assertThat(manufacturer.getName()).isEqualTo(manufacturer2.getName());
    }


    @Test
    void lookByNameTest() {
        manufacturerRepository.save(manufacturer1);
        manufacturerRepository.save(manufacturer2);
        manufacturerRepository.save(manufacturer3);


        List<Manufacturer> local = new ArrayList<>();
        local.add(manufacturer1);
        local.add(manufacturer2);
        local.add(manufacturer3);


        List<Manufacturer> manufacturers = manufacturerRepository.lookByName("Manufacturer");
        Assertions.assertThat(manufacturers).isEqualTo(local);
    }
}
