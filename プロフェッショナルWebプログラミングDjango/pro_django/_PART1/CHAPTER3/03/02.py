# キーエラーで例外処理を使う例
x = {0:1,1:2,3:4}
try:
    x[2]
except Exception as e:
    print("error", e.args)
print("done")
