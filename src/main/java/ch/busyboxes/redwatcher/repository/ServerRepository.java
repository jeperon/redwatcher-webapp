package ch.busyboxes.redwatcher.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import ch.busyboxes.redwatcher.domain.Server;

@RooJpaRepository(domainType = Server.class)
public interface ServerRepository {
}
