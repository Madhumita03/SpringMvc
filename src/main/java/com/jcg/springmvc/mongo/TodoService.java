package com.jcg.springmvc.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcg.springmvc.mongo.User;
import com.jcg.springmvc.mongo.factory.MongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Service("todoService")
@Transactional
public class TodoService {

	static String db_name = "mydb", db_collection = "myTodo";
	private static Logger log = Logger.getLogger(TodoService.class);

	// Fetch all users from the mongo database.
	public List<Todo> getAll() {
		List<Todo> todo_list = new ArrayList<Todo>();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Fetching cursor object for iterating on the database records.
		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();

			Todo todo = new Todo();
			todo.setId(dbObject.get("id").toString());
			todo.setLabel(dbObject.get("label").toString());
			todo.setDescription(dbObject.get("description").toString());
			todo.setStatus(dbObject.get("status").toString());

			// Adding the user details to the list.
			todo_list.add(todo);
		}
		log.debug("Total records fetched from the mongo database are= " + todo_list.get(0).getId() +" "+todo_list.get(0).getLabel()+ " " +
				todo_list.get(0).getDescription() + " " + todo_list.get(0).getStatus());
		return todo_list;
	}

	// Add a new user to the mongo database.
	public Boolean add(Todo todo) {
		boolean output = false;
		log.debug("Adding a new user to the mongo database; Entered user_name is= " + todo.getLabel());
		try {
			DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

			// Create a new object and add the new user details to this object.
			BasicDBObject doc = new BasicDBObject();
			doc.put("id", UUID.randomUUID()+"");
			doc.put("label", todo.getLabel()); 
			doc.put("description", todo.getDescription());
			doc.put("status", todo.getStatus());

			// Save a new user to the mongo collection.
			coll.insert(doc);
			output = true;
		} catch (Exception e) {
			output = false;
			log.error("An error occurred while saving a new user to the mongo database", e);
		}
		return output;
	}

	// Update the selected user in the mongo database.
	public Boolean edit(Todo todo) {
		boolean output = false;
		log.debug("Updating the existing user in the mongo database; Entered user_id is= " + todo.getId());
		try {
			// Fetching the user details.
			BasicDBObject existing = (BasicDBObject) getDBObject(todo.getId());

			DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

			// Create a new object and assign the updated details.
			BasicDBObject edited = new BasicDBObject();
			edited.put("id", todo.getId());
			edited.put("label", todo.getLabel());
			edited.put("description", todo.getDescription());
			edited.put("status", todo.getStatus());

			// Update the existing user to the mongo database.
			coll.update(existing, edited);
			output = true;
		} catch (Exception e) {
			output = false;
			log.error("An error has occurred while updating an existing user to the mongo database", e);
		}
		return output;
	}

	// Delete a user from the mongo database.
	public Boolean delete(String id) {
		boolean output = false;
		log.debug("Deleting an existing user from the mongo database; Entered user_id is= " + id);
		try {
			// Fetching the required user from the mongo database.
			BasicDBObject item = (BasicDBObject) getDBObject(id);

			DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

			// Deleting the selected user from the mongo database.
			coll.remove(item);
			output = true;
		} catch (Exception e) {
			output = false;
			log.error("An error occurred while deleting an existing user from the mongo database", e);
		}
		return output;
	}

	// Fetching a particular record from the mongo database.
	private DBObject getDBObject(String id) {
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Fetching the record object from the mongo database.
		DBObject where_query = new BasicDBObject();

		// Put the selected user_id to search.
		where_query.put("id", id);
		return coll.findOne(where_query);
	}

	// Fetching a single user details from the mongo database.
	public Todo findTodoId(String id) {
		Todo todo = new Todo();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Fetching the record object from the mongo database.
		DBObject where_query = new BasicDBObject();
		where_query.put("id", id);

		DBObject dbo = coll.findOne(where_query);
		todo.setId(dbo.get("id").toString());
		todo.setLabel(dbo.get("label").toString());
		todo.setDescription(dbo.get("description").toString());
		todo.setStatus(dbo.get("status").toString());

		// Return user object.
		return todo;
	}
}