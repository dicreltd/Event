package com.example.ekanri.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ekanri.model.Event;
import com.example.ekanri.model.EventRepository;
import com.example.ekanri.model.Sankasha;
import com.example.ekanri.model.SankashaRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository repository;

	@Autowired
	private SankashaRepository srepository;

	// Top
	@GetMapping("/elist")
	public ModelAndView slist(ModelAndView mav) {
		List<Event> list = repository.findAll();

		mav.addObject("list",list);

		 mav.setViewName("elist");
		 return mav;
	}

	@GetMapping("/eadd")
	public ModelAndView evenAddConfirm(
			@ModelAttribute Event event,
			ModelAndView mav) {

		mav.addObject("event",event);

		mav.setViewName("eadd");
		return mav;
	}

	@PostMapping("/eadd")
	public ModelAndView evenAdd(
			@ModelAttribute Event event,
			ModelAndView mav) {

		repository.save(event);

		mav.setViewName("redirect:/elist");
		return mav;
	}

	@GetMapping("/event/{eid}")
	public ModelAndView evenAddConfirm(
			@PathVariable int eid,
			ModelAndView mav) {

		Optional<Event> ev = repository.findById(eid);
		List<Sankasha> list = srepository.findByEid(eid);

		mav.addObject("list", list);
		mav.addObject("event", ev.get());
		mav.setViewName("event");
		return mav;
	}

	@PostMapping("/event")
	public ModelAndView eventPost(
			@ModelAttribute Sankasha sankasha,
			ModelAndView mav) {

		srepository.save(sankasha);

		mav.setViewName("redirect:event/" + sankasha.getEid());
		return mav;
	}

	@GetMapping("/eventedit")
	public ModelAndView evenEdit(
			@RequestParam int eid,
			ModelAndView mav) {

		Optional<Event> ev = repository.findById(eid);

		mav.addObject("event",ev.get());

		mav.setViewName("eventedit");
		return mav;
	}

	@PostMapping("/eventedit")
	public ModelAndView eventEditPost(
			@ModelAttribute Event event,
			ModelAndView mav) {

		repository.save(event);

		mav.setViewName("redirect:/event/" + event.getEid());
		return mav;
	}

	@GetMapping("/eventdel")
	public ModelAndView evenDel(
			@RequestParam int eid,
			ModelAndView mav) {

		Optional<Event> ev = repository.findById(eid);

		mav.addObject("event",ev.get());

		mav.setViewName("eventdel");
		return mav;
	}

	@PostMapping("/eventdel")
	public ModelAndView eventDelPost(
			@RequestParam int eid,
			ModelAndView mav) {

		List<Sankasha> list = srepository.findByEid(eid);
		srepository.deleteInBatch(list);
		repository.deleteById(eid);

		mav.setViewName("redirect:/elist");
		return mav;
	}
}
