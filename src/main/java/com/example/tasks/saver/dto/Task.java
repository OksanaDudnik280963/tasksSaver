package com.example.tasks.saver.dto;

import com.example.tasks.saver.dto.enums.TasksStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.tasks.saver.global.InstallConstants.START_TASK_NAME;

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
    private String taskDescription = START_TASK_NAME;

    @Column(name = "TASK_COST")
    @JsonProperty
    private BigDecimal taskCost = BigDecimal.ZERO;

    @Column(name = "OPERATION_COUNT")
    @JsonProperty
    private Long operationsCount = 0L;

    @Column(name = "TASK_STATUS")
    @JsonProperty
    private String taskStatus = TasksStatus.PROJECT.name();

/*
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Task.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations = new ArrayList();
*/
}
