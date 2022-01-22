# ブレイクポイントの例
x = {0:1,1:2,3:4}
for n in range(4):
    try:
        print(x[n])
    except Exception as e:
        print('error',e.args)
        import pdb; pdb.set_trace()

print("done")
