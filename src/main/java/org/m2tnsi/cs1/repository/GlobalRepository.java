package org.m2tnsi.cs1.repository;

import org.m2tnsi.cs1.classes.Global;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalRepository extends CrudRepository<Global, Long> {

}
