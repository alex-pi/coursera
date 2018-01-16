class Animal:

    def eat(self):
        print(self.name, "is eating.")

    def __init__(self, name):
        # this create 2 object/instance variables
        self.name = name
        self.tricks = []


class Dog(Animal):

    # class variable shared by all objects
    toys_pool = ["ball", "chicken"]

    def bark(self, times=2):
        print(self.name, "barked", times, "times")

    def add_trick(self, t):
        self.tricks.append(t)

    def do_tricks(self):
        print(self.tricks)

    # class methods are bound to a Class not to an object!
    @classmethod
    def add_toy(cls, toy):
        cls.toys_pool.append(toy)

d1 = Dog("Drinky")
d1.eat()
d1.bark(3)
# d1.bark() is the same as Dog.bark(d1) is like explicitly passing th 'self'
Dog.bark(d1, 3)
d1.add_trick("Roll")

d2 = Dog("Mugres")
d2.add_trick("Play death")
d2.eat()
d1.eat()
d2.do_tricks()
# Both instances share the toys
Dog.add_toy("bone")
print(d1.toys_pool)
print(d2.toys_pool)
