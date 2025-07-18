CRUD app taskapi on Spring.

1) Developed a REST API for a simple task management service. The following endpoints implemented:
- Create a task
- Get a list of all tasks
- Update a task
- Delete a task
- Get a task by ID

2) Connected a relational H2 database to project.

3) Implemented logging of requests and responses passing through  service.

4)External HTTP GET request to: https://api.restful-api.dev/objects
 - Logged the received response.

5) Implemented sending created tasks via email.

6) Added Basic Authentication to protect the API.

7) Implemented caching for "Get all tasks" using Redis.

NOTES: for testing purposes i used my own @gmail account to get notification when new task added. You can replace to your own in following files:
application.properties
- 15 line: spring.mail.username=youremail@gmail.com
- 16 line: spring.mail.password=yourpassword


TaskService.java:
  - 29 line: emailService.sendTaskCreated(created, "youremail@gmail.com"); // to which email sent

  - authorization to make any request:
    username: user
    password: user123

Not implemented:
- Unit tests
- Docker 
