package com.samples.datastore;

import com.samples.datastore.model.*;
import com.samples.datastore.repo.SingerRepository;
import com.samples.datastore.repo.TransactionalRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

@SpringBootApplication
@Slf4j
public class DatastoreApplication {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private TransactionalRepositoryService transactionalRepositoryService;

    public static void main(String[] args) {
        SpringApplication.run(DatastoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> {
            System.out.println("Remove all records from 'singers' kind");
            this.singerRepository.deleteAll();

            this.singerRepository
                    .save(Singer.builder()
                            .singerId("singer1")
                            .firstName("Jane")
                            .lastName("Doe")
                            .albums(new HashSet<>())
                            .build());

            Singer janeDoe = Singer.builder()
                                .singerId("singer2")
                                .firstName("Jane")
                                .lastName("Doe")
                                .albums(new TreeSet<>(Arrays.asList(
                                                new Album("a", LocalDate.of(2012, Month.JANUARY, 20)),
                                                new Album("b", LocalDate.of(2018, Month.FEBRUARY, 12))
                                            )
                                        )
                                )
                                .build();

            Singer richardRoe = Singer.builder()
                                    .singerId("singer3")
                                    .firstName("Richard")
                                    .lastName("Roe")
                                    .albums(new TreeSet<>(Arrays.asList(
                                            new Album("c", LocalDate.of(2000, Month.AUGUST, 31)))))
                                    .build();

            this.singerRepository.saveAll(Arrays.asList(janeDoe, richardRoe));

            createRelationshipsInTransaction(janeDoe, richardRoe);

            // The following line uses count(), which is a global-query in Datastore. This
            // has only eventual consistency.
            Thread.sleep(3000);

            retrieveAndPrintSingers();

            System.out.println("This concludes the sample.");

        };
    }

    private void retrieveAndPrintSingers() {
        System.out.println("The kind for singers has been cleared and "
                + this.singerRepository.count() + " new singers have been inserted:");

        Iterable<Singer> allSingers = this.singerRepository.findAll();
        allSingers.forEach(System.out::println);

        System.out.println("You can also retrieve by keys for strong consistency: ");

        // Retrieving by keys or querying with a restriction to a single entity group
        // / family is strongly consistent.
        this.singerRepository
                .findAllById(Arrays.asList("singer1", "singer2", "singer3"))
                .forEach((x) -> System.out.println("retrieved singer: " + x));

        //Query by example: find all singers with the last name "Doe"
        Iterable<Singer> singers = this.singerRepository.findAll(
                Example.of(Singer.builder()
                        .lastName("Doe")
                        .build(), ExampleMatcher.matching().withIgnorePaths("singerId")));
        System.out.println("Query by example");
        singers.forEach(System.out::println);

        //Pageable parameter
        System.out.println("Using Pageable parameter");
        Pageable pageable = PageRequest.of(0, 1);
        Slice<Singer> slice;
        boolean hasNext = true;
        while (hasNext) {
            slice = this.singerRepository.findSingersByLastName("Doe", pageable);
            System.out.println(slice.getContent().get(0));
            hasNext = slice.hasNext();
            pageable = slice.getPageable().next();
        }
    }

    private void createRelationshipsInTransaction(Singer singerA, Singer singerB) {
        Band band1 = new Band("General Band");
        Band band2 = new Band("Big Bland Band");
        Band band3 = BluegrassBand.builder()
                .name("Crooked Still")
                .build();

        // Creates the related Band and Instrument entities and links them to a Singer
        // in a single atomic transaction
        this.transactionalRepositoryService.createAndSaveSingerRelationshipsInTransaction(
                singerA, band1, Arrays.asList(band1, band2), new HashSet<>(Arrays
                        .asList(new Instrument("recorder"), new Instrument("cow bell"))));

        // You can also execute code within a transaction directly using the
        // SingerRepository.
        // The following call also performs the creation and saving of relationships
        // in a single transaction.
        this.singerRepository.performTransaction((transactionRepository) -> {
            singerB.setFirstBand(band3);
            singerB.setBands(Arrays.asList(band3, band2));
            singerB.setPersonalInstruments(new HashSet<>(Arrays
                    .asList(new Instrument("triangle"), new Instrument("marimba"))));
            this.singerRepository.save(singerB);
            return null;
        });

        System.out.println("Find by reference with query");
        List<String> singers = this.singerRepository.findSingersByFirstBand(band3).stream().map(Singer::getFirstName)
                .collect(Collectors.toList());
        System.out.println(singers);

        System.out.println("Find by reference");
        List<String> singers2 = this.singerRepository.findByFirstBand(band3).stream().map(Singer::getFirstName)
                .collect(Collectors.toList());
        System.out.println(singers2);
    }

}
