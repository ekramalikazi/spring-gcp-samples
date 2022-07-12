package com.samples.datastore.model;

import lombok.*;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;

import java.time.LocalDate;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Album implements Comparable<Album> {
    String albumName;

    LocalDate date;

    @Override
    public int compareTo(Album album) {
        return this.albumName.compareTo(album.albumName);
    }
}