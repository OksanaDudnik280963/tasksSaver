package com.example.tasks.saver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Task")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "task")
@Table(name = "TASKS", schema="tasks")
public class Task extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "TASK_NAME", nullable = false, unique = true)
    @JsonProperty
    private String taskName;

    @Column(name = "TASK_DESCRIPTION")
    @JsonProperty
    private String taskDescription;

    @Column(name = "TASK_COST")
    @JsonProperty
    private BigDecimal taskCost;

    @Column(name = "OPERATION_COUNT")
    @JsonProperty
    private Long operationsCount;

    @Column(name = "TASK_STATUS")
    @JsonProperty
    private String taskStatus;

/*
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Task.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations = new ArrayList();
*/
}
