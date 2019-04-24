# SwipeRightBrains

  

CUBRIC Project for Brain Scientists to create public app which to identify good and bad scans of a brain

To run the project

  

#### Step 1: Create the database

- First create your MySQL database, you will need the host, database name, username, and password to access this.

- On this database you will need to run schema.sql which is provided within the project. 
- *Note Within this schema there is a default admin, the credentials for this will need to be changed to secure the application. Or this user can be removed (Can only be done from database not application) and you will have to create an admin user manually. (Create a simple user then edit their role with an SQL update query)*
``` sql 
SELECT * FROM account;

UPDATE account SET role='admin' WHERE account_id = <account id>;
```
#### Step 2: Create and configure the application
 The application uses a series of properties. You can configure these in 2 ways:
1. edit the application-prod.properties file located within the project source
2. Provide an external application.properties file

The properties that need to be confgured include
3. Database credentials (url, host, pass, database)
4. Email credentials for support (This is used for password resets)
5. Port the application will run on
6. Password strength desired for user passwords (Ranges from 0 = no checks to 4 = very secure password)

To use an external properties file you will need to create an application.properties file on your machine with the following content
````
#Fill in this section with a production email
spring.mail.host=
spring.mail.port=
spring.mail.username=
spring.mail.password=

#Fill in this section with a production database settings
spring.datasource.url=jdbc:mysql://
spring.datasource.username=
spring.datasource.password=

#Set this to the desired password strength for production
app.password.strength = 

#Set this to the desired port for the application
server.port=

debug=false
logging.level.org.springframework.jdbc=warn
````


Build the applications JAR, to build this you can use the gradle wrapper provided to run the build task. See link below for help (https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:using_wrapper)

Create the project structure, an example is shown below of how to layout the directories
````
your_jar_name.jar
application.properties (Optional)
brain_images/
			top/
			front/
			side/
````

#### Step 3: Run the application

- In order to run the application you will need to run the jar. If you chose to make the application.properties external then you will need to add the following argument ``-Dspring.config.location=file:./``

#### Step 4: Insert some scans
To insert scans you can either upload them manually through the application however this is limited to 1 at a time or you can use the insert_images.py* script to insert a bulk ammount
````
* The insert_images.py script must be run inside the brain_images directory. 
The script will look for unique valid scans within the 3 directories (Top, Side, Front)
For a scan to be valid it must:
 - Be present in each of the 3 directories with the same name (ie 66A_30DTI_MD_C_trafo_tracts_dRL_FOD_interp_FORNIX.jpg is present with each of its side, top and front images in the correct folders)
 - Not already be included in the data set

brain_images/
			top/
				image1.jpg
				image2.jpg
			front/
				image1.jpg
			side/
				image1.jpg
				image2.jpg
insert_images.py

This will insert image1.jpg into the application as it is valid but image2.jpg is not.
````

  
The application will now be up and running and can be accessed via the url and port used.

 