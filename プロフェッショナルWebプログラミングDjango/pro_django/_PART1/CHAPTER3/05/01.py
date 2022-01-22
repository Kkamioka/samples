# クラスの例
class Car:
    def __init__(self, name, passengers):
        self.name = name
        self.passengers = passengers
        print("車を作りました")

        name = ""
        passengers = 0;

    def run(self):
        print("走行しています。")

car1 = Car("honda", 5)
print(car1.name)
car1.run()