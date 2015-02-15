package ch.busyboxes.redwatcher.domain;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooEquals
@RooToString
@RooJpaEntity
public class Server {

	@NotNull
	private String name;
}
