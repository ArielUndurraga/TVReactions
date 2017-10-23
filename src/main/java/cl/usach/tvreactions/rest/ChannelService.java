package cl.usach.tvreactions.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.usach.tvreactions.entities.*;
import cl.usach.tvreactions.repository.*;

@RestController
@RequestMapping("/channels")
public class ChannelService {
	@Autowired
	ChannelRepository canalRepository;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Channel> getAllChannels(){
		return canalRepository.findAll();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Channel create(@RequestBody Channel resource){
		return canalRepository.save(resource);
	}
}

