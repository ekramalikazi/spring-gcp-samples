package com.samples.datastore.model;

import com.google.cloud.datastore.Key;
import lombok.*;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Instrument {

    @Id
    Key instrumentId;

    @NonNull
    String type;

}