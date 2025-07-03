package com.example.tasks.saver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "Task")
@Table(name = "TASKS")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "task")
public class Task extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID", nullable = false, unique = true)
    private Long id;

    //@GeneratedValue(strategy = GenerationType.AUTO)
/*
    @Column(name = "TASK_NUMBER", nullable = false, unique = true, insertable = true, updatable = true)
    @JsonProperty
    private Long taskNumber;
*/

    @Column(name = "TASK_NAME", nullable = false, unique = true, insertable = true, updatable = true)
    @JsonProperty
    private String taskName;

    @Column(name = "TASK_DESCRIPTION", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private String taskDescription;

    @Column(name = "TASK_COST", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private BigDecimal taskCost;

    @Column(name = "OPERATION_COUNT", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private Long operationsCount;

    @Column(name = "TASK_STATUS", nullable = true, unique = false, insertable = true, updatable = true)
    @JsonProperty
    private String taskStatus;

}
