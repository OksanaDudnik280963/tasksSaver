package com.example.tasks.saver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Operation")
@Table(name = "OPERATIONS")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "operation")
public class Operation extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPERATION_ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    //@GeneratedValue(strategy = GenerationType.AUTO)
/*
    @Column(name = "OPERATION_NUMBER", nullable = false, unique = true, insertable = true, updatable = true)
    @JsonProperty
    private Long operationNumber;
*/

    @Column(name = "OPERATION_PRICE", nullable = false, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private BigDecimal operationPrice;

    @Column(name = "OPERATION_NAME", nullable = false, unique = true, insertable = true, updatable = true)
    @JsonProperty
    private String operationName;


    @Column(name = "OPERATION_STATUS", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private String operationStatus;


    @Column(name = "TASK_NAME", nullable = false, unique = true, insertable = true, updatable = true)
    @JsonProperty
    private String taskName;//during creation

    @Column(name = "OPERATION_DESCRIPTION", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private String operationDescription;

}
