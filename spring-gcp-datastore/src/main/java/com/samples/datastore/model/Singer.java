package com.samples.datastore.model;

import lombok.*;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Descendants;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.cloud.gcp.data.datastore.core.mapping.LazyReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Reference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "singers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Singer {

    @Id
    @Field(name = "singer_id")
    private String singerId;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @LazyReference // entities are lazy-loaded when the property is accessed
    private Band firstBand; // general relationships without hierarchy

    @Reference
    private List<Band> bands;


    // descendants are fully-formed entities residing in their own kinds
    // The parent entity does not have an extra field to hold the descendant entities.
    // Instead, the relationship is captured in the descendants' keys, which refer to their parent entities:
    // relationship link is part of the descendant entity’s key value
    @Descendants
    private Set<Instrument> personalInstruments; // one-to-many relationships


    @LastModifiedDate
    private LocalDateTime lastModifiedTime;

    // Embedded entities
    private Set<Album> albums;     // Embedded entities stored directly in the field of the containing entity
    // Embedded entities don’t need to have @Id field, it is only required for top level entities

}