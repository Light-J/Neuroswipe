import mysql.connector
import random
import os

host = "localhost"#input("Please enter your database url (ie localhost): ")
user = "root"#input("Please enter your database username: ")
password = "comsc"#input("Please enter your database password: ")
database = "brainschema"

#Credentials for database
mydb = mysql.connector.connect(
  host=host,
  user=user,
  passwd=password,
  database=database
)

mycursor = mydb.cursor()

def get_all_valid_scans():
    valid = []
    path = '.' 
    files_front = os.listdir(path+"/front")
    files_side = os.listdir(path+"/side")
    files_top = os.listdir(path+"/top")
    for scan_name in files_top:
       if scan_name in files_side:
           if scan_name in files_front:
               valid.insert(0, scan_name)
               
    return valid

def check_for_new_scans(valid):
    existing_scans = []
    scans_to_be_added = []
    sql_scans = "SELECT top_image FROM scan;"
    mycursor.execute(sql_scans)
    
    for row in mycursor.fetchall():
        existing_scans.insert(0, row[0].strip("front/"))

    for scan_name in valid:
        if scan_name not in existing_scans:
            #print(scan_name)
            scans_to_be_added.insert(0, scan_name)
    
    for x in scans_to_be_added:
        print(x)
    
    
check_for_new_scans(get_all_valid_scans())



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
      
#insert_images()
    

