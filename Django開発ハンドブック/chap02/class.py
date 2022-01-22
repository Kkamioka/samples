# インスタンスメソッド
class Test:
    def show(self, val):
        print(self, val)    # selfとvalを出力
        
test = Test()           # Testクラスをインスタンス化してオブジェクトの参照を代入
test.show('こんにちは')    # Testオブジェクトからshow()メソッドを実行

# __init__()メソッドでインスタンス変数への代入を行う
class Test2:
    def __init__(self, val):
        self.val = val
        
    def show(self):
        print(self.val)    # self.valを出力

test2 = Test2(100)
test2.show()

# クラス変数
class MyClass:
    count = 0  # クラス変数countの定義

print(MyClass.count)

# クラスメソッド
class ClassTest:
    @classmethod
    def class_method(cls):
        print("これはクラスメソッドです。")

ClassTest.class_method()