package com.borntocode.spring_api_gateway.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("api_route")
@Entity
@Builder
public class ApiRoute {
    @Id
    private Long id;
    private String uri;
    private String path;
    private String method;
    private String description;

    @Column(name = "group_code")
    private String groupCode;

    private String status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
