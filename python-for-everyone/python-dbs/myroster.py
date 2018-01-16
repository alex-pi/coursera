import json
import sqlite3

conn = sqlite3.connect("roster.sqlite")
cur = conn.cursor()

cur.executescript('''
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Member;

CREATE TABLE `Course` ( 
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 
  `title` TEXT UNIQUE
);

CREATE TABLE `User` ( 
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 
  `name` TEXT UNIQUE 
);

CREATE TABLE Member ( 
  user_id integer, 
  course_id integer, 
  role integer, 
  primary key (user_id, course_id) 
);

''')

file = input("Enter file name: ")
if len(file) <= 0: file = "roster_data.json"

commit_size = 0
for entry in json.loads(open(file).read()):
    print("Entry: ", entry)
    user = entry[0]
    course = entry[1]
    role = entry[2]

    if user is None or course is None or role is None:
        continue

    cur.execute('''INSERT OR IGNORE INTO User (name)
        VALUES (?)''', (user,))
    cur.execute('SELECT id FROM User WHERE name = ?', (user,))
    user_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Course (title)
        VALUES (?)''', (course,))
    cur.execute('SELECT id FROM Course WHERE title = ?', (course,))
    course_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Member (user_id, course_id, role)
        VALUES (?, ?, ?)''', (user_id, course_id, role))

    if commit_size > 30:
        commit_size = 0
        conn.commit()

conn.commit()

cur.execute('''SELECT hex(User.name || Course.title || Member.role ) AS X FROM 
    User JOIN Member JOIN Course 
    ON User.id = Member.user_id AND Member.course_id = Course.id
    ORDER BY X''')

for r in cur.fetchall():
    print(r[0])

cur.execute('''SELECT User.name, Course.title, Member.role FROM User JOIN Member JOIN Course
    ON User.id = Member.user_id AND Member.course_id = Course.id
    ORDER BY User.name, Course.title''')

for (name, title, role) in cur.fetchall():
    r = "student"
    if role == 1: r = "teacher"
    print(name, "is a", r, "in", title)

