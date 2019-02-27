import mysql.connector
import random
import os

host = input("Please enter your database url (ie localhost): ")
user = input("Please enter your database username: ")
password = input("Please enter your database password: ")

#Credentials for database
mydb = mysql.connector.connect(
  host=host,
  user=user,
  passwd=password
)

mycursor = mydb.cursor()

#Warning, images need to be in top, middle and side folders and must have the same names and same amount
#I used the CUBRIC .zip images which work well for this
def insert_images():
   sql_scans = "INSERT INTO brainschema.scan (top_image, side_image, front_image) VALUES (%s, %s, %s)"
   path = '.' 
   files_front = os.listdir(path+"/front")
   files_side = os.listdir(path+"/side")
   files_top = os.listdir(path+"/top")
   for i in range(0, len(files_front)):
      val_scans = ("front/" + files_front[i], "side/"+files_side[i], "top/"+files_top[i])
      mycursor.execute(sql_scans, val_scans)
      mydb.commit()
   input("Image entry succesful")
      
insert_images()
    

