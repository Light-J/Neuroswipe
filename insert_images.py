import mysql.connector
import urllib.request
import random
import os

#Credentials for database
mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  passwd="comsc"
)

mycursor = mydb.cursor()

#Warning, images need to be in top, middle and side folders and must have the same names and same amount
#I used the CUBRIC .zip images which work well for this
def insert_images():
   sql_scans = "INSERT INTO brainschema.scans (path1, path2, path3) VALUES (%s, %s, %s)"
   path = '.' 
   files_front = os.listdir(path+"/front")
   files_side = os.listdir(path+"/side")
   files_top = os.listdir(path+"/top")
   for i in range(0, len(files_front)):
      val_scans = ("front/" + files_front[i], "side/"+files_side[i], "top/"+files_top[i])
      mycursor.execute(sql_scans, val_scans)
      mydb.commit()
   print("Image entry succesful")
      
insert_images()
    

