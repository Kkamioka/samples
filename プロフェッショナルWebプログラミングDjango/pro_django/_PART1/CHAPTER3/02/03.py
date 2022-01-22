# breakとcontinueの例
while True:
    s = str(input("y/n"))
    if s == "y":
        print("Yes")
        breakå
    elif s == "n":
        print("No")
        break
    else:
        print("正しく入力してください")
        continue
