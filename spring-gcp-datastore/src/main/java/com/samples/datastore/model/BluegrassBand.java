package com.samples.datastore.model;

import lombok.Data;
import org.springframework.cloud.gcp.data.datastore.core.mapping.DiscriminatorValue;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;

@Entity
@DiscriminatorValue("bluegrass")
@Data
public class BluegrassBand extends Band {

}