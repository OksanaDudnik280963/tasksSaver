package com.example.tasks.saver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Operation")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "operation")
@Table(name = "OPERATIONS")
public class Operation extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPERATION_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "OPERATION_PRICE", nullable = false)
    @JsonProperty
    private BigDecimal operationPrice;

    @Column(name = "OPERATION_NAME", nullable = false, unique = true)
    @JsonProperty
    private String operationName;


    @Column(name = "OPERATION_STATUS")
    @JsonProperty
    private String operationStatus;



    @Column(name = "TASK_NAME", nullable = false)
    @JsonProperty
    private String taskName;


    @Column(name = "OPERATION_DESCRIPTION")
    @JsonProperty
    private String operationDescription;

}
