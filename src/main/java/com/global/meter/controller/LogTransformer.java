package com.global.meter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/logs")
public class LogTransformer {
/*
	private static final Logger LOG = LoggerFactory.getLogger(LogTransformer.class);
	
	@Autowired
	LogsRepository logsRepository;

	@Autowired
	MetersCommandsConfiguration configuration;
	
	@Autowired
	Error error;
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/add" , method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> insertLogs(@Valid @RequestBody Logs logs)
	{
		LOG.info("Adding logs in logs table: " + logs.getDevice_serial_number());
		if (logs.getDevice_serial_number() != null) {
			logsRepository.save(logs);
			return new ResponseEntity<>(logs, HttpStatus.OK);
		}
		else {
			LOG.error("Device Info is not present. Please contact to administrator.");
			error.setMessage("Device Info is not present. Please contact to administrator.");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	}
	*/
}
