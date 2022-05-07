We have 3 points (requests).
1. http://localhost:8080/ad
2. http://localhost:8080/impression
3. http://localhost:8080/stats/user/John
File with them added to repo. It is a collection of Postman tool.

1st and 2nd requests - have a POST method, because we add information in DB.
3rd - this is GET, because we only get information.

App designed by RESTfull architecture.
It has good readability and changeability by layers. 

DB - it's a REDIS (key/value db). It's a good solution for this task, and if we want to make a higher scale. https://redis.io/docs/about/

Now this app executing on localhost.
For that, you need a Docker on your computer, and execute the command "docker-compose up --build -d", being inside the project directory where the file "docker-compose" is located.
This file creates images (REDIS and app), containers out of these images, and starts these containers.
