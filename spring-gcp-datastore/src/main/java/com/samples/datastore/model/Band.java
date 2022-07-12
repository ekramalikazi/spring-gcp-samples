package com.samples.datastore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.DiscriminatorField;
import org.springframework.cloud.gcp.data.datastore.core.mapping.DiscriminatorValue;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity
@DiscriminatorField(field = "band_type")
@DiscriminatorValue("generic_band")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Band {

    @Id
    protected String name;
}