class Robot:
    def __init__(self, name, build_year):
        self.name = name
        self.build_year = build_year

    # If str is not defined for this class, python calls __repr__
    def __str__(self):
        return "Name: {}, Year: {}".format(self.name, self.build_year)

    def __repr__(self):
        return 'Robot("{}", {})'.format(self.name, self.build_year)

x = Robot("Alex", 1982)
print(x)
print(repr(x))
print("__repr__ must return an expression python can evaluate, we create a new instance in the following line: ")
print("y = eval(repr(x))")
y = eval(repr(x))
print(y)

class Human:
    pass

print("Python uses a default method when str and repr are not defined.")
print(Human())