class Robot:
    pass

x = Robot()
x.name = "Filiberto"

print("Attribute name is only at instance level.")
print(Robot.__dict__)
print(x.name)

y = Robot()
try:
    print("object y does not have an attribute name, we get an exception.")
    print(y.name)
except Exception as err:
    print(err)

Robot.brand = "LG" # This becomes a Class variable/attribute in python.
print("Python looks first on object dict for attributes, if not there, it checks the Class dictionary.")
print(y.brand)
print(x.brand)
print(Robot.__dict__)