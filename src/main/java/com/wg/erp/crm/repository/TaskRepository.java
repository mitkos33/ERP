package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatusInOrderByDueDateDesc(Collection<StatusType> status);
    List<Task> findAllByAssignedToGroupsAndStatusInOrderByDueDateDesc(Collection<UserGroup> userGroups, Collection<StatusType> status);
    int countAllByStatusIn(Set<StatusType> status);
    Optional<Task> findByIdAndCreatedBy(Long id, User createdBy);
    @Query("SELECT t FROM Task t WHERE t.id = :id AND (t.createdBy = :createdBy OR EXISTS (SELECT g FROM UserGroup g WHERE g MEMBER OF t.assignedToGroups AND g IN :assignedToGroupIds))")
    Optional<Task> findByIdAndCreatedByOrAssignedToGroups(
            @Param("id") Long id,
            @Param("createdBy") User createdBy,
            @Param("assignedToGroupIds") Set<UserGroup> assignedToGroupIds
    );
    int countAllByAssignedToGroupsAndStatusIn(Set<UserGroup> assignedToGroups, Collection<StatusType> status);
}
