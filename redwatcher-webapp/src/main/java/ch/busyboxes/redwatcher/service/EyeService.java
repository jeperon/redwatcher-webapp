package ch.busyboxes.redwatcher.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class EyeService {

	private static final Logger logger = LoggerFactory.getLogger(EyeService.class);

	public boolean testSnmp(String host) throws IOException {
		Address targetAddress = GenericAddress.parse("udp:" + host + "/161");
		TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		transport.listen();

		// create the target
		CommunityTarget target = new CommunityTarget();
		target.setAddress(targetAddress);
		target.setRetries(1);
		target.setTimeout(5000);
		target.setVersion(SnmpConstants.version2c);

		// create the PDU
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID("1.3.6")));
		pdu.setType(PDU.GETNEXT);

		// send the PDU
		ResponseEvent response = snmp.send(pdu, target);
		// extract the response PDU (could be null if timed out)
		PDU responsePDU = response.getResponse();
		// extract the address used by the agent to send the response:
		Address peerAddress = response.getPeerAddress();

		logger.debug(responsePDU.toString());

		return true;
	}
}
