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

import cl.usach.tvreactions.entities.Channel;
import cl.usach.tvreactions.entities.Tvshow;
import cl.usach.tvreactions.repository.TvshowRepository;

@RestController
@RequestMapping("/tvshows")
public class TvshowService {
	
	@Autowired
	TvshowRepository tvshowRepository;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Tvshow> getAllTvshows(){
		return tvshowRepository.findAll();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Tvshow create(@RequestBody Tvshow resource){
		resource.setTvshowFrequency(0);
		return tvshowRepository.save(resource);
	}

}
