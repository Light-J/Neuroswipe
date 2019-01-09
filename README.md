# SwipeRightBrains

University Project for Brain Scientists to create public app which to identify good and bad scans of a brain
To run the project

#### Step 1: Create the database
 - First create your MySQL database, you will need the host, database name, username, and password to access this.
 - In the application.properties you will need to add this information (URL contains the host and database name)
 - On this database you will need to run schema.sql which is provided
 
#### Step 2: Create the directory to run the application
 - You will need to assemble your directory as seen below. This includes your jar file for the application and a brain_images folder with 3 sub folders (top, front, side)
 ````
*your_jar_name*.jar
brain_images/
    top/
    front/
    side/
 ````
 
#### Step 3: Run the application
 - In order to run the application you will need to run the jar. The jar takes no arguments
 - *Optionally you may wish to run the insert_images.py script in order to insert a bulk amount of images. (You will need to know your database credentials and host) See below for instructions*
 
 Example
 ````
 * The script must be run inside the brain_images directory. In each of the 3 folders you need to have the same number of images and with the same names (See below).
  
 brain_images/
            insert_images.py
            top/
                image1.jpg
                image2.jpg
            front/
                image1.jpg
                image2.jpg
            side/
                image1.jpg
                image2.jpg
            
               
               
 This will insert image1.jpg and image2.jpg into your scans
```` 

The application will now be up and running and can be accessed. To get to the home route use "/" (For example localhost:8080/)

### Manual test pack
##### Home page
- This is the landing page when first accessing the website
- It will containt information about Cardiff unis CUBRICs centre

##### About brain imaging
- Click "About brain imaging" on the navigation bar
- You will be presented with information about MRI scans

##### Training
- Click "Training" on the navigation bar
- You will be presented with cards containing information about how the site works
- Scroll to the bottom of this page
- A reward will appear when you reach the bottom for completing training
- Close the reward
- Click "Practice sorting images" that is on the bottom of this page
- You will be redirected to the home page as you have not logged in yet and a log in box will appear

##### Registering
Before you can log in you first need an account so close the log in box by clicking the "x" in the corner of it or by pressing "esc"
- Click "Register" on the navigation bar, you will be presented with the registration page containing input boxes
- Enter "carl" into the email then press submit
- Warning dialogs should appear saying password must not be empty and that email is invalid
- Enter "carl@valid.me" into the email box, enter "pass" into password, and "not" into confirm then submit
- You will be presented with another dialog saying these passwords do not match
- Enter "carl@valid.me" into the email box and then "password1" into both password boxes then click submit
- You will be automatically logged in and then shown the home page with "carl@valid.me" replacing the log in and register buttons on the nav bar. There will also now be "Practice", "Sort images" and "Feedback" shown on the navigation bar

##### Updating profile
- Click on "carl@valid.me" on the navigation bar, a drop down should appear and then click "My profile"
- Change age to "21" and then press submit
- The page should reload with your age saved as "21"
- Change your postcode to "CF" and the press submit
- A dialog should appear about entering the postcode in the correct format
- Enter "CF23" into the postcode box and then press submit
- All of these details are saved

##### Logging out
- Click on the "carl@valid.me" on the nav bar and then click log out
- You will be redirected to the home page
- The "carl@valid.me" will be replaced with log in and register buttons as well as the navigation bars extra options being removed

##### Log in as default admin
- Click the log in button on the navigation bar
- Enter "default@admin" into the username and "admin" into the password box then click sign in
- You should now be  logged in as "default@admin" and there will now be "Manage images", "Manage users", "Export images" and "Feedback" added to the navigation bar

##### Managing images
This tab lets you manage the images currently on the system, if you inserted a bulk amount of images you will see them all here
######Upload an image
- Upload an image by selecting 3 images of your choice into the top, middle and side upload sections then click upload
- The image will appear in the table, if you uploaded a bulk number of images this will be hard to find (A search would be useful in the future)
- The table is paginated and pages can be changed by clicking "next" or "previous" at the bottom of the page
- To view an image click on the hyperlink in the file name columns
######Change an images known good / bad
- Click on the "Unknown" on an image in the "Known good/bad" column
- A drop down will appear with "Good", "Bad" and "Unknown"
- Click "Good", this image will now show as a good image
- A "Option saved" notification will appear
- Click "Bad", for a different image, this image will now be a known bad image
- A "Option saved" notification will appear

#### Collect some ratings and feedback
- Log out the default admin and log in as "carl@valid.me" with the password "password1"

##### Practice images
These images are known good / bad images that the user can practice on, the results are not saved in this section. The images are loaded randomly.
- Click on either the "yes" or "no" buttons or use the left and right arrow keys to answer the question
- If the user is correct the Correct answers will increase and if they are wrong incorrect answers will increase
- After the correct answers reaches 10 then a reward will appear

##### Sorting images
These images are from all the scans, they are picked at random
- Click on either the "yes" or "no" buttons or use the left and right arrow keys to answer the question
- After 25 questions have been answered a reward will appear
- After 50 questions have been answered a reward will appear

##### Leaving feedback
This tab allows a logged in user to leave feedback
- Enter "This is my feedback" into the field and click submit
- You will be redirected and a reward will appear
- Close the reward and you will see a thank you for your feedback page


####Manage the responses to questions and feedback
- Log out of "carl@valid.me" and log in as the "default@admin" with "admin"

##### Viewing feedback
- Click on the feedback tab
- A page will load with the feedback from "carl@valid.me" with the feedback "This is my feedback"


##### Exporting images
This page allows you to download a zip archive of all the images that match your criteria
- Click on the update button without entering anything into the search boxes 
- A result of scans will be shown, these will be all the scans that have responses from users
- Clicking download will download all of the images shown in this list as a .zip archive
- Entering values into these fields will allows you to filter your results


##### Managing users
This page allows you to search through users, change a users role, view a specific users responses, remove a specific users responses or delete a user
###### Searching users
- Enter "carl" into the search box and click search, the user "carl@valid.me" will appear
- Leave the search box blank and click search, all users will appear

###### Changing a users roles
- Click on "user" for the user "carl@valid.me", then click "Admin", a notification will appear saying the users role has been changed
- Click on "admin" for the user "default@admin" and then click "user", a notification will appear saying it had failed to change as you cant change yourself or the default admin

###### Viewing users response
- Click on "View responses", another page will appear showing all the responses for that user.
- "carl@valid.me" should appear with all of your responses from earlier

###### Removing a users responses
- Click on "Remove user responses", a confirm box will appear saying are you sure you want to remove the responses for said user.
- Click "cancel" and the box will disappear and nothing will happen
- Doing this again but clicking confirm will cause a notification saying a number of responses was removed

###### Deleting a user
- Click on "delete user", a confirm box will appear saying are you sure you want to delete that users. 
- Click "cancel" and the box will disappear and nothing will happen
- Doing this again but clicking confirm will cause a notification saying the user was removed
- Note users responses will remain on the system, also that users feedback will be removed
- Note trying this on the default admin will fail as you cannot remove the default admin

 


