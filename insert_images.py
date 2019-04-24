import mysql.connector
import random
import os
import getpass

host = raw_input("Please enter your database url: ")
user = raw_input("Please enter your database user username: ")
password = getpass.getpass(prompt="Enter your datasource password: ")#str(input("Please enter your database password: "))
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

def insert_scans(scans):
    sql_scans = "INSERT INTO brainschema.scan (top_image, side_image, front_image) VALUES (%s, %s, %s)"
    for scan in scans:
        val_scan = ("top/" + scan, "side/"+scan, "front/"+scan)
        mycursor.execute(sql_scans, val_scan)
        mydb.commit()
    print(str(len(scans)) + " scans have been entered succesfully");

def check_for_new_scans(valid):
    existing_scans = []
    scans_to_be_added = []
    sql_scans = "SELECT top_image FROM scan;"
    mycursor.execute(sql_scans)
    
    for row in mycursor.fetchall():
        existing_scans.insert(0, row[0].strip("top/"))

    for scan_name in valid:
        if scan_name not in existing_scans:
            scans_to_be_added.insert(0, scan_name)
    
    for x in scans_to_be_added:
        print(x)
    insert_scans(scans_to_be_added)
    
    
check_for_new_scans(get_all_valid_scans())

