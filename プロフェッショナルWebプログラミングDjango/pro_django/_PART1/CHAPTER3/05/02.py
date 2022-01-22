# クラスを継承する例
class Car:
    def __init__(self, name, passengers):
        self.name = name
        self.passengers = passengers
        print("車を作りました")

    name = ""
    passengers = 0;

    def run(self):
        print("走行しています。")


class GasCar(Car):
    def __init__(self, name, passengers):
        super().__init__(name, passengers)
        self.gas = 0

    def refuel(self, n):
        self.gas += n
        print("合計"+str(self.gas) + "リットル")

car2 = GasCar("honda_wagon", 8)
print(car2.name)
car2.refuel(1)
car2.refuel(3)
