package com.jcg.springmvc.mongo.controller;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcg.springmvc.mongo.Todo;
import com.jcg.springmvc.mongo.TodoService;
import com.jcg.springmvc.mongo.User;

@Controller
@RequestMapping(value="/todo")
public class TodoController {

	private static Logger log = Logger.getLogger(TodoController.class);

	@Resource(name="todoService")
	private TodoService todoService;

	// Displaying the initial todos list.
	@RequestMapping(value = "/todoList", method = RequestMethod.GET)
	public String getTodos(Model model) {
		System.out.println("Entered in list");
		log.debug("Request to fetch all users from the mongo database");
		List<Todo> todo_list = todoService.getAll();		
		model.addAttribute("todos", todo_list);		
		return "welcome";
	}

	// Opening the add new user form page.
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUser(Model model) {
		log.debug("Request to open the new user form page");
		model.addAttribute("todoAttr", new Todo());
		return "form";
	}

	// Opening the edit user form page.
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam(value="id", required=true) String id, Model model) {
		log.debug("Request to open the edit user form page with id  =  "+id);
		
		model.addAttribute("todoAttr", todoService.findTodoId(id));		
		return "form";
	}

	// Deleting the specified user.
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value="id", required=true) String id, Model model) {
		todoService.delete(id);
		return "redirect:todoList";
	}

	// Adding a new user or updating an existing user.
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("todoAttr") Todo todo) {
		log.debug("todo id==== "+ todo.getId());
		if(todo.getId() != null && todo.getId().trim() != "") {
			todoService.edit(todo);
		} else {
			todoService.add(todo);
		}
		return "redirect:todoList";
	}
}