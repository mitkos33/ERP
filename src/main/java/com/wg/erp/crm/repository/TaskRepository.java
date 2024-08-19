package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatusInOrderByDueDateDesc(Collection<StatusType> status);
    int countAllByStatusIn(Set<StatusType> status);
}
