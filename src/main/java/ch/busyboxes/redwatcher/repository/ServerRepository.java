package ch.busyboxes.redwatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ch.busyboxes.redwatcher.domain.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {

}
