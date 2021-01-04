package com.zwb.demo.repository;

import com.zwb.demo.bean.SchedulerTasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerTasksRepository extends CrudRepository<SchedulerTasks, Integer> {
    SchedulerTasks findByName(String name);
}
