package com.dietapp.notificationservice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String message;

    @NotNull
    private String code;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "notification",
            orphanRemoval = true)
    private Set<CustomProperty> properties;

    @Version
    private int version;

    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastUpdatedDate;

    public void addProperty(CustomProperty customProperty) {
        if (properties == null) {
            properties = new HashSet<>();
        }
        properties.add(customProperty);
    }
}
